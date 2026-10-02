package com.govflow.backend.event;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "govflow_event")
public class GovFlowEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "case_id", nullable = false, length = 64)
    private String caseId;

    @Column(name = "event_type", nullable = false, length = 64)
    private String eventType;

    @Column(name = "department", length = 120)
    private String department;

    @Column(name = "stage", length = 120)
    private String stage;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    public GovFlowEvent() {
    }

    @PrePersist
    public void prePersist() {
        if (receivedAt == null) {
            receivedAt = LocalDateTime.now();
        }
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }
}