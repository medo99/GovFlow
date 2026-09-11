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
