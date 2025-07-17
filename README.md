# Pagos360 Payment Requests

This project provides a Kotlin Spring Boot service for creating and tracking payment requests through the [Pagos360](https://pagos360.com/) API. It exposes a small REST API and stores data in PostgreSQL using R2DBC.

## Requirements

- Java 21
- Maven 3.9 (wrapper included)
- Docker and Docker Compose for running the full stack

## Building

Run the Maven wrapper to build the executable JAR:

```bash
./mvnw clean package
```

## Running with Docker Compose

Please read the RUNNING.md file. Default values are stored in the `.env` file:

## API Endpoints

The main controller exposes endpoints for listing, retrieving and creating payment requests:

```kotlin
@RestController
@RequestMapping("/api/payment-requests")
@Validated
class PayRequestController(private val payRequestUseCases: PayRequestUseCases) {

    @Operation(summary = "List Payment Requests")
    @GetMapping
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Flux<PayResponseDto> =
        payRequestUseCases.listAll(page, size).map { it.toDto() }

    @Operation(summary = "Get Payment Request by ID")
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<PayResponseDto> =
        payRequestUseCases.findById(UUID.fromString(id)).map { it.toDto() }
            .switchIfEmpty(
                Mono.error(
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Payment request not found")
                )
            )

    @Operation(summary = "Create Payment Requests")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody cmds: List<@Valid PayCreateRequestCmd>
    ): Flux<PayResponseDto> =
        payRequestUseCases.create(cmds.map { cmd ->
            cmd.toDomain()
        })
            .map { it.toDto() }
}
```

Webhook events from Pagos360 are handled in a separate controller:

```kotlin
class PayRequestWebhookController(
    private val useCases: PayRequestUseCases
) {

    @PatchMapping("/{id}/status")
    @Operation(
        summary = "Receive webhook event from payment provider",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun webhook(
        @PathVariable id: UUID,
        @RequestBody cmd: PayPatchRequestStatusCmd
    ): Mono<PayResponseDto> =
        useCases.updateStatus(id, cmd.toStatus()).map { it.toDto() }
}
```

## Database

Liquibase migrations are executed on startup. The initial migration creates the `pay_payment_requests` table:

```sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE pay_payment_requests
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description    VARCHAR(255)   NOT NULL,
    first_due_date TIMESTAMP      NOT NULL,
    first_total    NUMERIC(15, 2) NOT NULL,
    payer_name     VARCHAR(100)   NOT NULL,
    status         VARCHAR(20)    NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_pay_request_status CHECK (
        status IN ('PENDING', 'PAID', 'REVERSED', 'ERROR')
        )
);
```

## Docker Image

The service is packaged in a simple image defined by `Dockerfile`:

```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/payment-requests-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## License

This repository does not include a specific license.

