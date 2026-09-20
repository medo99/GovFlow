package com.govflow.backend.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GovFlowTransactionRepository
        extends JpaRepository<GovFlowTransaction, String> {

    long countByStatusIgnoreCase(String status);
    long countByStatusNotIgnoreCase(String status);

    long countByDelayRiskPctGreaterThanEqual(Integer risk);
    @Query("select avg(t.delayRiskPct) from GovFlowTransaction t")
    Double findAverageRisk();

    @Query("""
        select t.department,
               count(t),
               sum(case when lower(t.status) = 'delayed' then 1 else 0 end),
               avg(t.delayRiskPct)
        from GovFlowTransaction t
        group by t.department
        order by avg(t.delayRiskPct) desc
    """)
    List<Object[]> findDepartmentSummary();
    @Query("""
    select t.stage,
           count(t),
           sum(case when lower(t.status) = 'delayed' then 1 else 0 end),
           avg(t.delayRiskPct),
           avg(t.queueSize)
    from GovFlowTransaction t
    group by t.stage
    order by avg(t.delayRiskPct) desc
""")
    List<Object[]> findBottleneckSummary();
    List<GovFlowTransaction>
    findTop10ByStatusIgnoreCaseOrderByDelayRiskPctDescAgeHoursDesc(
            String status
    );
}