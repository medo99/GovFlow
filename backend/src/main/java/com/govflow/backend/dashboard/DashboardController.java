package com.govflow.backend.dashboard;

import com.govflow.backend.transaction.GovFlowTransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import com.govflow.backend.transaction.GovFlowTransaction;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final GovFlowTransactionRepository repository;

    public DashboardController(GovFlowTransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {

        long total = repository.count();

        // v0.2 definition:
        // Active workload = every transaction not completed
        long active = repository.countByStatusNotIgnoreCase("Completed");

        long delayed = repository.countByStatusIgnoreCase("Delayed");

        long completed = repository.countByStatusIgnoreCase("Completed");

        // v0.2 definition:
        // High Risk = delay risk >= 75%
        long highRisk = repository.countByDelayRiskPctGreaterThanEqual(75);

        Double averageRiskValue = repository.findAverageRisk();

        double averageRisk =
                averageRiskValue != null
                        ? averageRiskValue
                        : 0.0;

        // v0.2 SLA Compliance:
        // non-delayed active workload / active workload
        double slaCompliance =
                active == 0
                        ? 0.0
                        : ((double) (active - delayed) / active) * 100.0;

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("totalTransactions", total);
        result.put("activeTransactions", active);
        result.put("delayedTransactions", delayed);
        result.put("completedTransactions", completed);
        result.put("highRiskTransactions", highRisk);

        result.put(
                "slaCompliancePct",
                Math.round(slaCompliance * 10.0) / 10.0
        );

        result.put(
                "averageRiskPct",
                Math.round(averageRisk * 100.0) / 100.0
        );

        return result;
    }
    @GetMapping("/departments")
    public List<Map<String, Object>> getDepartmentSummary() {

        return repository.findDepartmentSummary()
                .stream()
                .map(row -> {

                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("department", row[0]);
                    item.put("totalTransactions", row[1]);
                    item.put("delayedTransactions", row[2]);

                    double avgRisk = row[3] != null
                            ? ((Number) row[3]).doubleValue()
                            : 0.0;

                    item.put(
                            "averageRiskPct",
                            Math.round(avgRisk * 100.0) / 100.0
                    );

                    return item;
                })
                .toList();
    }
    @GetMapping("/bottlenecks")
    public List<Map<String, Object>> getBottlenecks() {

        return repository.findBottleneckSummary()
                .stream()
                .map(row -> {

                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("stage", row[0]);
                    item.put("totalTransactions", row[1]);
                    item.put("delayedTransactions", row[2]);

                    double avgRisk = row[3] != null
                            ? ((Number) row[3]).doubleValue()
                            : 0.0;

                    double avgQueue = row[4] != null
                            ? ((Number) row[4]).doubleValue()
                            : 0.0;

                    item.put("averageRiskPct",
                            Math.round(avgRisk * 100.0) / 100.0);

                    item.put("averageQueueSize",
                            Math.round(avgQueue * 100.0) / 100.0);

                    return item;
                })
                .toList();
    }
    @GetMapping("/high-risk")
    public List<GovFlowTransaction> getHighRiskTransactions() {
        return repository
                .findTop10ByStatusIgnoreCaseOrderByDelayRiskPctDescAgeHoursDesc("Delayed");
    }

}