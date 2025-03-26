package org.estatement.estatementsystemback.service.user;

import org.estatement.estatementsystemback.dto.DashboardDTO.*;

import java.util.List;

public interface UserService {

    AccountsOverviewDTO getAccountsOverview(String email);
    List<IncomesVsExpenses>getIncomesVsExpenses(String email);
    BudgetSummary getBudgetSummary(String email);
    List<CreditCardSummary> getCreditCardSummaries(String email);
    List<ExpensesAnalysis> getExpensesAnalysis(String email);
    List<Last4Transactions> getLast4Transactions(String email);



}
