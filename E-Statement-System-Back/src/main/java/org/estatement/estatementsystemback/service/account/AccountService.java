package org.estatement.estatementsystemback.service.account;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.CPIL;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.RIB_IBAN_Type;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<RIB_IBAN_Type> getRib_iban_type( Long account_id);
    List<AccountInformation> getAllAccounts();
    Optional<CPIL> getCPIL( Long account_id);
    List<TransactionDTO>getTransactions( Long accountId, String period, String operationType);
    List<CreditCardSummary> findCreditCardsByAccountAndUser( Long account_id);
    void updateMinimumBalanceAlert(Long accountId, double newAlert) throws AccountNotFoundException;
}
