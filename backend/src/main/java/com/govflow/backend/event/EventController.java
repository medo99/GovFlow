package com.govflow.backend.event;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventIngestionService ingestionService;

    public EventController(EventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GovFlowEvent ingest(
            @Valid @RequestBody EventIngestionRequest request) {

        return ingestionService.ingest(request);
    }
    @GetMapping("/case/{caseId}")
    public List<GovFlowEvent> getCaseHistory(
            @PathVariable String caseId) {

        return ingestionService.getCaseHistory(caseId);
    }
}