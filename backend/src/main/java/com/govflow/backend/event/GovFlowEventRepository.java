package com.govflow.backend.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GovFlowEventRepository
        extends JpaRepository<GovFlowEvent, Long> {

    List<GovFlowEvent> findByCaseIdOrderByOccurredAtDesc(String caseId);
}