package org.estatement.estatementsystemback.dao;


import org.estatement.estatementsystemback.dto.DashboardDTO.ExpensesAnalysis;
import org.estatement.estatementsystemback.dto.DashboardDTO.Last4Transactions;
import org.estatement.estatementsystemback.entity.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT amount FROM transaction t " +
            "JOIN account a ON t.id_account = a.id " +
            "JOIN user u ON a.account_founder_id_user = u.id_user " +
            "WHERE u.email = ?1 " +
            "ORDER BY t.date_time DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Double> findLatestTransactionAmountByUserEmail(String userEmail);


    @Query(value = """
    SELECT 
        CASE 
            WHEN MONTH(t.date_time) = MONTH(CURRENT_DATE()) 
                 AND YEAR(t.date_time) = YEAR(CURRENT_DATE()) 
            THEN 'This month'
            ELSE 'Last month'
        END AS period,
        COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0) AS income,
        COALESCE(SUM(CASE WHEN t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') THEN t.amount ELSE 0 END), 0) AS expense
    FROM transaction t
    JOIN account a ON t.id_account = a.id
    JOIN user u ON a.account_founder_id_user = u.id_user
    WHERE u.email = ?1
    AND (
        (MONTH(t.date_time) = MONTH(CURRENT_DATE()) 
        AND YEAR(t.date_time) = YEAR(CURRENT_DATE()))
        OR
        (MONTH(t.date_time) = MONTH(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH)) 
        AND YEAR(t.date_time) = YEAR(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH)))
    )
    GROUP BY period
    ORDER BY 
        CASE 
            WHEN period = 'This month' THEN 1
            WHEN period = 'Last month' THEN 2
            ELSE 3
        END
    """, nativeQuery = true)
    List<Object[]> findMonthlyIncomeExpenseByUserEmail(String userEmail);

    @Query(value = """
    SELECT 
        COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0) AS total,
        COALESCE(SUM(CASE WHEN t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') THEN t.amount ELSE 0 END), 0) AS expenses,
        COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0) - 
        COALESCE(SUM(CASE WHEN t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') THEN t.amount ELSE 0 END), 0) AS remaining,
        CASE 
            WHEN COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0) = 0 THEN 0
            ELSE (COALESCE(SUM(CASE WHEN t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') THEN t.amount ELSE 0 END), 0) / 
                 COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0)) * 100
        END AS expensesPercentage,
        CASE 
            WHEN COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0) = 0 THEN 0
            ELSE 100 - ((COALESCE(SUM(CASE WHEN t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') THEN t.amount ELSE 0 END), 0) / 
                 COALESCE(SUM(CASE WHEN t.operation IN ('DEPOSIT') THEN t.amount ELSE 0 END), 0)) * 100)
        END AS remainingPercentage
    FROM transaction t
    JOIN account a ON t.id_account = a.id
    JOIN user u ON a.account_founder_id_user = u.id_user
    WHERE u.email = ?1
    AND MONTH(t.date_time) = MONTH(CURRENT_DATE())
    AND YEAR(t.date_time) = YEAR(CURRENT_DATE())
    """, nativeQuery = true)
    List<Object[]> findMonthlyFinancialSummaryByUserEmail(String userEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.ExpensesAnalysis(" +
            "t.category, " +
            "CAST(SUM(t.amount) AS double)) " +  // Explicit cast to double
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN a.accountFounder u " +
            "WHERE u.email = :email " +
            "GROUP BY t.category " +
            "ORDER BY SUM(t.amount) DESC")
    List<ExpensesAnalysis> findExpensesAnalysisByUserEmail(@Param("email") String email);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.Last4Transactions(" +
            "t.idTransaction, t.status, t.amount, t.title) " +
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN a.accountFounder u " +
            "WHERE u.email = :email " +
            "ORDER BY t.dateTime DESC, t.idTransaction DESC")
    List<Last4Transactions> findLast4TransactionsByUserEmail(@Param("email") String email, PageRequest pageable);

}