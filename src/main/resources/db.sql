CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS pay_payment_requests (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    first_due_date TIMESTAMP NOT NULL,
    first_total NUMERIC(15,2) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
