package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary;
import org.estatement.estatementsystemback.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardDAO extends JpaRepository<Card, Long> {
    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary(" +
            "c.cardType, " +
            "c.cardNumber, " +
            "c.currentBalance, " +
            "c.availableLimit, " +
            "c.expirationDate, " +
            "c.ccv_cvc, " +
            "CONCAT(c.linkedAccount.accountFounder.firstName, ' ', c.linkedAccount.accountFounder.lastName)) " +
            "FROM Card c " +
            "WHERE c.linkedAccount.accountFounder.email = :userEmail")
    List<CreditCardSummary> findCreditCardSummariesByUserEmail(@Param("userEmail") String userEmail);

    @Query("SELECT SUM(c.currentBalance) FROM Card c " +
            "WHERE c.linkedAccount.accountFounder.email = :userEmail")
    Optional<Double> sumCurrentBalanceByUserEmail(@Param("userEmail") String userEmail);

}