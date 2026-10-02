CREATE TABLE govflow_transaction (
    case_id VARCHAR(64) PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    department VARCHAR(120) NOT NULL,
    stage VARCHAR(120) NOT NULL,
    transaction_type VARCHAR(120) NOT NULL,
    priority VARCHAR(32) NOT NULL,
    sla_hours INTEGER NOT NULL,
    age_hours INTEGER NOT NULL,
    queue_size INTEGER NOT NULL,
    delay_risk_pct INTEGER NOT NULL CHECK (delay_risk_pct BETWEEN 0 AND 100),
    status VARCHAR(32) NOT NULL
);

CREATE INDEX idx_gf_tx_department ON govflow_transaction(department);
CREATE INDEX idx_gf_tx_stage ON govflow_transaction(stage);
CREATE INDEX idx_gf_tx_risk ON govflow_transaction(delay_risk_pct);
CREATE INDEX idx_gf_tx_status ON govflow_transaction(status);
CREATE TABLE govflow_event (
    event_id BIGSERIAL PRIMARY KEY,
    case_id VARCHAR(64) NOT NULL,
    event_type VARCHAR(64) NOT NULL,
    department VARCHAR(120),
    stage VARCHAR(120),
    occurred_at TIMESTAMP NOT NULL,
    received_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_govflow_event_transaction
        FOREIGN KEY (case_id)
        REFERENCES govflow_transaction(case_id)
);

CREATE INDEX idx_gf_event_case
    ON govflow_event(case_id);

CREATE INDEX idx_gf_event_type
    ON govflow_event(event_type);

CREATE INDEX idx_gf_event_occurred
    ON govflow_event(occurred_at);
