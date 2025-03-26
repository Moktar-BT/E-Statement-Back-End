package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {


    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.accountFounder.email = :accountFounderEmail")
    Optional<Double> findTotalBalanceByAccountFounderEmail(@Param("accountFounderEmail") String accountFounderEmail);

    List<Account> findByAccountFounder_Email(String accountFounderEmail);
}