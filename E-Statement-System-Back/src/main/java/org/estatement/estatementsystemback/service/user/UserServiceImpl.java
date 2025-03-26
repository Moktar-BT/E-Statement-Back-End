package org.estatement.estatementsystemback.service.user;

import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.AccountDAO;
import org.estatement.estatementsystemback.dao.CardDAO;
import org.estatement.estatementsystemback.dao.TransactionDAO;
import org.estatement.estatementsystemback.dao.UserDAO;
import org.estatement.estatementsystemback.dto.DashboardDTO.*;
import org.estatement.estatementsystemback.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;


    @Override
    public AccountsOverviewDTO getAccountsOverview(String email) {
        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccountsOverviewDTO dto = new AccountsOverviewDTO();
        dto.setToTalBalance(accountDAO.findTotalBalanceByAccountFounderEmail(email).orElse(0.0));
        dto.setLastTransaction(transactionDAO.findLatestTransactionAmountByUserEmail(email).orElse(0.0));
        dto.setCreditCards(cardDAO.sumCurrentBalanceByUserEmail(email).orElse(0.0));
        dto.setInterestRate(user.getInterestRate());

        return dto;
    }

    @Override
    public List<IncomesVsExpenses> getIncomesVsExpenses(String email) {
        List<Object[]> results = transactionDAO.findMonthlyIncomeExpenseByUserEmail(email);

        List<IncomesVsExpenses> response = new ArrayList<>();

        // Default values if no transactions exist
        IncomesVsExpenses currentMonth = new IncomesVsExpenses("This month", 0.0, 0.0);
        IncomesVsExpenses previousMonth = new IncomesVsExpenses("Last month", 0.0, 0.0);

        // Update with actual data from query
        for (Object[] row : results) {
            String period = (String) row[0];
            Double income = ((Number) row[1]).doubleValue();
            Double expense = ((Number) row[2]).doubleValue();

            if ("This month".equals(period)) {
                currentMonth = new IncomesVsExpenses(period, income, expense);
            } else {
                previousMonth = new IncomesVsExpenses(period, income, expense);
            }
        }

        response.add(currentMonth);
        response.add(previousMonth);

        return response;
    }

    @Override
    public BudgetSummary getBudgetSummary(String email) {
        log.info("Fetching budget summary for email: {}");

        List<Object[]> results = transactionDAO.findMonthlyFinancialSummaryByUserEmail(email);

        if (results == null || results.isEmpty()) {
            log.warn("No budget summary data found for email: {}");
            return new BudgetSummary(0.0, 0.0, 0.0, 0.0, 0.0);
        }

        Object[] row = results.get(0);
        log.info("Raw query results: {}");

        // Handle potential null values
        Double total = row[0] != null ? ((Number) row[0]).doubleValue() : 0.0;
        Double expenses = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        Double remaining = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
        Double expensesPercentage = row[3] != null ? ((Number) row[3]).doubleValue() : 0.0;
        Double remainingPercentage = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;

        BudgetSummary summary = new BudgetSummary(total, expenses, remaining, expensesPercentage, remainingPercentage);
        log.info("Mapped budget summary: {}");

        return summary;
    }

    @Override
    public List<CreditCardSummary> getCreditCardSummaries(String email) {
        return new ArrayList<>(cardDAO.findCreditCardSummariesByUserEmail(email));

    }

    @Override
    public List<ExpensesAnalysis> getExpensesAnalysis(String email) {
        return transactionDAO.findExpensesAnalysisByUserEmail(email);
    }

    @Override
    public List<Last4Transactions> getLast4Transactions(String email) {
        return transactionDAO.findLast4TransactionsByUserEmail(
                email,
                PageRequest.of(0, 4)
        );
    }

}
