-- idempotency-records
CREATE TABLE idempotency_records (
    id UUID PRIMARY KEY,

    idempotency_key VARCHAR(100) NOT NULL,

    user_keycloak_id UUID NOT NULL,

    request_hash TEXT,
    response_body TEXT,
    http_status INTEGER,

    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    expires_at TIMESTAMPTZ,

    CONSTRAINT uk_idem_user_key
        UNIQUE (idempotency_key, user_keycloak_id)
);


--indexing 
CREATE INDEX idx_idem_user_key
    ON idempotency_records(idempotency_key, user_keycloak_id);

CREATE INDEX idx_idem_expires_at
    ON idempotency_records(expires_at);

CREATE INDEX idx_idem_status
    ON idempotency_records(status);

    
--payments
CREATE TABLE payments (

    id UUID PRIMARY KEY,

    order_id UUID NOT NULL,

    user_keycloak_id UUID NOT NULL,

    gateway_transaction_id VARCHAR(100) NOT NULL,

    amount NUMERIC(15,2) NOT NULL CHECK (amount > 0),

    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL
);

--index
-- Frequently queried by order
CREATE INDEX idx_payments_order_id
    ON payments(order_id);

-- Frequently queried by user
CREATE INDEX idx_payments_user_id
    ON payments(user_keycloak_id);

-- Lookup by gateway transaction (used in webhook)
CREATE UNIQUE INDEX uk_payments_gateway_txn
    ON payments(gateway_transaction_id);

-- filter by status
CREATE INDEX idx_payments_status
    ON payments(status);