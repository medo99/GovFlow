package com.govflow.backend.event;

import com.govflow.backend.transaction.GovFlowTransaction;
import com.govflow.backend.transaction.GovFlowTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventIngestionService {

    private final GovFlowEventRepository eventRepository;
    private final GovFlowTransactionRepository transactionRepository;

    public EventIngestionService(
            GovFlowEventRepository eventRepository,
            GovFlowTransactionRepository transactionRepository) {

        this.eventRepository = eventRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public GovFlowEvent ingest(EventIngestionRequest request) {

        GovFlowTransaction transaction = transactionRepository
                .findById(request.getCaseId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Transaction not found: " + request.getCaseId()
                ));

        GovFlowEvent event = new GovFlowEvent();
        event.setCaseId(request.getCaseId());
        event.setEventType(request.getEventType());
        event.setDepartment(request.getDepartment());
        event.setStage(request.getStage());
        event.setOccurredAt(request.getOccurredAt());

        GovFlowEvent savedEvent = eventRepository.save(event);

        applyEvent(transaction, request);

        return savedEvent;
    }

    private void applyEvent(
            GovFlowTransaction transaction,
            EventIngestionRequest request) {

        switch (request.getEventType().toUpperCase()) {

            case "STAGE_CHANGED" -> {
                if (request.getStage() == null ||
                        request.getStage().isBlank()) {

                    throw new IllegalArgumentException(
                            "stage is required for STAGE_CHANGED"
                    );
                }

                transaction.setStage(request.getStage());

                if (request.getDepartment() != null &&
                        !request.getDepartment().isBlank()) {

                    transaction.setDepartment(request.getDepartment());
                }
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported event type: " + request.getEventType()
            );
        }
    }
    @Transactional(readOnly = true)
    public List<GovFlowEvent> getCaseHistory(String caseId) {

        if (!transactionRepository.existsById(caseId)) {
            throw new IllegalArgumentException(
                    "Transaction not found: " + caseId
            );
        }

        return eventRepository
                .findByCaseIdOrderByOccurredAtDesc(caseId);
    }
}