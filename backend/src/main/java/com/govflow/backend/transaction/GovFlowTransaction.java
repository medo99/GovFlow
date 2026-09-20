package com.govflow.backend.transaction;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "govflow_transaction")
public class GovFlowTransaction {

    @Id
    @Column(name = "case_id", length = 64)
    private String caseId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 120)
    private String department;

    @Column(nullable = false, length = 120)
    private String stage;

    @Column(name = "transaction_type", nullable = false, length = 120)
    private String transactionType;

    @Column(nullable = false, length = 32)
    private String priority;

    @Column(name = "sla_hours", nullable = false)
    private Integer slaHours;

    @Column(name = "age_hours", nullable = false)
    private Integer ageHours;

    @Column(name = "queue_size", nullable = false)
    private Integer queueSize;

    @Column(name = "delay_risk_pct", nullable = false)
    private Integer delayRiskPct;

    @Column(nullable = false, length = 32)
    private String status;

    public GovFlowTransaction() {
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getSlaHours() {
        return slaHours;
    }

    public void setSlaHours(Integer slaHours) {
        this.slaHours = slaHours;
    }

    public Integer getAgeHours() {
        return ageHours;
    }

    public void setAgeHours(Integer ageHours) {
        this.ageHours = ageHours;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Integer getDelayRiskPct() {
        return delayRiskPct;
    }

    public void setDelayRiskPct(Integer delayRiskPct) {
        this.delayRiskPct = delayRiskPct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}