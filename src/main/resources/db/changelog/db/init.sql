CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

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
