package com.govflow.backend.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class GovFlowTransactionController {

    private final GovFlowTransactionRepository repository;

    public GovFlowTransactionController(GovFlowTransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<GovFlowTransaction> getTransactions(
            @PageableDefault(size = 20, sort = "caseId") Pageable pageable) {

        return repository.findAll(pageable);
    }
}