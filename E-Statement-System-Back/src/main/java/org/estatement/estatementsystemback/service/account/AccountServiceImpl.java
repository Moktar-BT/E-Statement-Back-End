package org.estatement.estatementsystemback.service.account;

import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.AccountDAO;
import org.estatement.estatementsystemback.dao.CardDAO;
import org.estatement.estatementsystemback.dao.TransactionDAO;
import org.estatement.estatementsystemback.dao.UserDAO;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.CPIL;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.RIB_IBAN_Type;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary;
import org.estatement.estatementsystemback.entity.Transaction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;


    @Override
    public Optional<RIB_IBAN_Type> getRib_iban_type( Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return accountDAO.findRIBAndIBANAndTypeByAccountIdAndUserEmail(id,email);
    }

    @Override
    public List<AccountInformation> getAllAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return accountDAO.findAccountInformationByUserEmail(email);
    }

    @Override
    public Optional<CPIL> getCPIL( Long account_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return accountDAO.findCPILByAccountIdAndUserEmail(account_id,email);
    }

    @Override
    public List<TransactionDTO> getTransactions( Long accountId, String period, String operationType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        // Validate input parameters
        if (!List.of("this_month", "this_week", "last_month", "last_3_months").contains(period)) {
            throw new IllegalArgumentException("Invalid period value. Allowed values: this_month, this_week, last_month, last_3_months");
        }

        if (!List.of("all", "expenses", "incomes").contains(operationType)) {
            throw new IllegalArgumentException("Invalid operation type. Allowed values: all, expenses, incomes");
        }

        // Calculate date ranges
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStartDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .truncatedTo(ChronoUnit.DAYS);
        LocalDateTime threeMonthsAgo = now.minusMonths(3);

        return transactionDAO.findFilteredTransactions(
                email,
                accountId,
                period,
                operationType,
                weekStartDate,
                threeMonthsAgo
        );
    }

    @Override
    public List<CreditCardSummary> findCreditCardsByAccountAndUser(Long account_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return  cardDAO.findCreditCardsByAccountAndUser(account_id,email);
    }
}
