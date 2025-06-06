package org.estatement.estatementsystemback.dao;


import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.DashboardDTO.ExpensesAnalysis;
import org.estatement.estatementsystemback.dto.DashboardDTO.Last4Transactions;
import org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO;
import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;
import org.estatement.estatementsystemback.entity.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
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
    AND MONTH(t.date_time) = MONTH(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH))
    AND YEAR(t.date_time) = YEAR(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH))
    """, nativeQuery = true)
    List<Object[]> findLastMonthFinancialSummaryByUserEmail(String userEmail);
    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.ExpensesAnalysis(" +
            "t.category, " +
            "CAST(SUM(t.amount) AS double)) " +
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN a.accountFounder u " +
            "WHERE u.email = :email " +
            "AND t.dateTime >= :startDate " +
            "AND t.dateTime <= :endDate " +
            "AND t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE') " +
            "GROUP BY t.category " +
            "ORDER BY SUM(t.amount) DESC")
    List<ExpensesAnalysis> findExpensesAnalysisByUserEmailAndDateRange(
            @Param("email") String email,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.Last4Transactions(" +
            "t.id, t.status, t.amount, t.title) " +
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN a.accountFounder u " +
            "WHERE u.email = :email " +
            "ORDER BY t.dateTime DESC, t.id DESC")

    List<Last4Transactions> findLast4TransactionsByUserEmail(@Param("email") String email, PageRequest pageable);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO(" +
            "t.id, t.operation, t.category, t.dateTime, t.status, t.amount) " +
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN a.accountFounder u " +
            "WHERE u.email = :email " +
            "AND (:accountId IS NULL OR a.id = :accountId) " +
            "AND (" +
            "   (:period = 'this_week' AND t.dateTime >= :weekStartDate AND t.dateTime <= CURRENT_TIMESTAMP) " +
            "   OR (:period = 'this_month' AND MONTH(t.dateTime) = MONTH(CURRENT_DATE) AND YEAR(t.dateTime) = YEAR(CURRENT_DATE)) " +
            "   OR (:period = 'last_month' AND MONTH(t.dateTime) = MONTH(CURRENT_DATE - 1 MONTH) AND YEAR(t.dateTime) = YEAR(CURRENT_DATE - 1 MONTH)) " +
            "   OR (:period = 'last_3_months' AND t.dateTime >= :threeMonthsAgo AND t.dateTime <= CURRENT_TIMESTAMP) " +
            ") " +
            "AND (" +
            "   (:operationType = 'all') " +
            "   OR (:operationType = 'expenses' AND t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE')) " +
            "   OR (:operationType = 'incomes' AND t.operation = 'DEPOSIT') " +
            ") " +
            "ORDER BY t.dateTime DESC")
    List<TransactionDTO> findFilteredTransactions(
            @Param("email") String email,
            @Param("accountId") Long accountId,
            @Param("period") String period,
            @Param("operationType") String operationType,
            @Param("weekStartDate") LocalDateTime weekStartDate,
            @Param("threeMonthsAgo") LocalDateTime threeMonthsAgo);


    @Query("SELECT NEW org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO(" +
            "t.id, t.operation, t.category, t.dateTime, t.status, t.amount) " +
            "FROM Transaction t " +
            "JOIN t.card c " +
            "JOIN c.linkedAccount.accountFounder u " +
            "WHERE u.email = :email " +
            "AND (:cardId IS NULL OR c.id_card = :cardId) " +
            "AND (" +
            "   (:period = 'this_week' AND t.dateTime >= :weekStartDate AND t.dateTime <= CURRENT_TIMESTAMP) " +
            "   OR (:period = 'this_month' AND MONTH(t.dateTime) = MONTH(CURRENT_DATE) AND YEAR(t.dateTime) = YEAR(CURRENT_DATE)) " +
            "   OR (:period = 'last_month' AND MONTH(t.dateTime) = MONTH(CURRENT_DATE - 1 MONTH) AND YEAR(t.dateTime) = YEAR(CURRENT_DATE - 1 MONTH)) " +
            "   OR (:period = 'last_3_months' AND t.dateTime >= :threeMonthsAgo AND t.dateTime <= CURRENT_TIMESTAMP) " +
            ") " +
            "AND (" +
            "   (:operationType = 'all') " +
            "   OR (:operationType = 'expenses' AND t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE')) " +
            "   OR (:operationType = 'incomes' AND t.operation = 'DEPOSIT') " +
            ") " +
            "ORDER BY t.dateTime DESC")
    List<TransactionDTO>getCardTransactions(
            @Param("email") String email,
            @Param("cardId") Long cardId,
            @Param("period") String period,
            @Param("operationType") String operationType,
            @Param("weekStartDate") LocalDateTime weekStartDate,
            @Param("threeMonthsAgo") LocalDateTime threeMonthsAgo);



    // In TransactionRepository.java
    @Query("""
    SELECT new org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO(
        t.id,
        t.operation,
        t.category,
        t.paymentMethod,
        a.rib,
        c.cardNumber,
        t.dateTime,
        t.status,
        t.amount
    )
    FROM Transaction t
    LEFT JOIN t.account a
    LEFT JOIN t.card c
    WHERE (:email IS NULL OR a.accountFounder.email = :email)
    AND (
        :source = 'all' OR 
        (:source = 'account' AND a.id = :sourceId) OR 
        (:source = 'card' AND c.id_card = :sourceId)
    )
    AND t.dateTime BETWEEN :startDate AND :endDate
    AND (
        :operationType = 'all' OR
        (:operationType = 'expenses' AND t.operation IN ('WITHDRAWAL', 'PAYMENT', 'TRANSFER', 'FEE')) OR
        (:operationType = 'incomes' AND t.operation = 'DEPOSIT')
    )
""")
    List<TransactionsPageDTO> findTransactionsWithFilters(
            @Param("email") String email,
            @Param("source") String source, // "account", "card", or "all"
            @Param("sourceId") Long sourceId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("operationType") String operationType
    );



    @Query("SELECT new org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO(" +
            "t.dateTime, t.id, t.operation, t.category, t.amount) " +
            "FROM Transaction t " +
            "WHERE (:accountId IS NULL OR t.account.id = :accountId) " +
            "AND (:cardId IS NULL OR t.card.id_card = :cardId) " +
            "AND t.dateTime BETWEEN :startDate AND :endDate " +
            "AND t.operation IN :operationTypes " +
            "AND t.status = true " +
            "AND (t.account.accountFounder.email = :email OR t.card.linkedAccount.accountFounder.email = :email)")
    List<StatementTransactionDTO> findStatementTransactionsByUserEmail(
            @Param("email") String email,
            @Param("accountId") Long accountId,
            @Param("cardId") Long cardId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("operationTypes") List<String> operationTypes
    );










}