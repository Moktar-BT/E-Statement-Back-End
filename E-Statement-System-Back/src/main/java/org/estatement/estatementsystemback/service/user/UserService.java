package org.estatement.estatementsystemback.service.user;

import org.estatement.estatementsystemback.dto.DashboardDTO.*;

import java.util.List;

public interface UserService {

    AccountsOverviewDTO getAccountsOverview();
    List<IncomesVsExpenses>getIncomesVsExpenses();
    BudgetSummary getBudgetSummary();
    List<CreditCardSummary> getCreditCardSummaries();
    List<ExpensesAnalysis> getExpensesAnalysis();
    List<Last4Transactions> getLast4Transactions();



}
