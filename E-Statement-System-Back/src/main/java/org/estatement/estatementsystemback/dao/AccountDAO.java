package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.CPIL;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.RIB_IBAN_Type;
import org.estatement.estatementsystemback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.AccountOverviewDTO.RIB_IBAN_Type(" +
            "a.rib, a.iban, a.accountType) " +
            "FROM Account a " +
            "WHERE a.id = :accountId AND a.accountFounder.email = :userEmail")
    Optional<RIB_IBAN_Type> findRIBAndIBANAndTypeByAccountIdAndUserEmail(
            @Param("accountId") Long accountId,
            @Param("userEmail") String userEmail);


    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.accountFounder.email = :accountFounderEmail")
    Optional<Double> findTotalBalanceByAccountFounderEmail(@Param("accountFounderEmail") String accountFounderEmail);


    List<Account> findByAccountFounder_Email(String accountFounderEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation(" +
            "a.id, a.accountType,a.accountName,a.status,a.rib,a.balance,a.iban) " +
            "FROM Account a " +
            "WHERE a.accountFounder.email = :email")
    List<AccountInformation> findAccountInformationByUserEmail(@Param("email") String email);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.AccountOverviewDTO.CPIL(" +
            "a.balance, " +
            "a.pendingBalance, " +
            "a.interestRate, " +
            "COALESCE((SELECT t.amount FROM Transaction t WHERE t.account = a ORDER BY t.dateTime DESC LIMIT 1), 0)) " +
            "FROM Account a " +
            "WHERE a.id = :accountId AND a.accountFounder.email = :userEmail")
    Optional<CPIL> findCPILByAccountIdAndUserEmail(
            @Param("accountId") Long accountId,
            @Param("userEmail") String userEmail);
}