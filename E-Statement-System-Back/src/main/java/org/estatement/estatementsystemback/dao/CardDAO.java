package org.estatement.estatementsystemback.dao;

import jakarta.transaction.Transactional;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CPWL;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardInformations;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.IBAN_Cnum_Ctype;
import org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary;
import org.estatement.estatementsystemback.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT NEW org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary(" +
            "c.cardType, " +
            "c.cardNumber, " +
            "c.currentBalance, " +
            "c.availableLimit, " +
            "c.expirationDate, " +
            "c.ccv_cvc, " +
            "CONCAT(c.linkedAccount.accountFounder.firstName, ' ', c.linkedAccount.accountFounder.lastName)) " +
            "FROM Card c " +
            "WHERE c.linkedAccount.id = :accountId " +
            "AND c.linkedAccount.accountFounder.email = :userEmail")
    List<CreditCardSummary> findCreditCardsByAccountAndUser(
            @Param("accountId") Long accountId,
            @Param("userEmail") String userEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO(" +
            "c.id_card, " +
            "c.cardNumber, " +
            "c.cardType, " +
            "c.currentBalance ,"+
            "c.status) " +
            "FROM Card c " +
            "WHERE c.linkedAccount.accountFounder.email = :userEmail")
    List<CardOverviewDTO> findCardOverviewsByUserEmail(@Param("userEmail") String userEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.CardOverviewDTO.IBAN_Cnum_Ctype(" +
            "c.linkedAccount.iban, " +
            "c.cardNumber, " +
            "c.cardType) " +
            "FROM Card c " +
            "WHERE c.id_card = :cardId " +
            "AND c.linkedAccount.accountFounder.email = :userEmail")
    Optional<IBAN_Cnum_Ctype> findCardDetailsWithIbanForConnectedUser(
            @Param("cardId") Long cardId,
            @Param("userEmail") String userEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.CardOverviewDTO.CPWL(" +
            "c.currentBalance, " +
            "c.paymentLimit, " +
            "c.withdrawalLimit, " +
            "(SELECT t.amount FROM Transaction t WHERE t.card.id_card = c.id_card ORDER BY t.dateTime DESC LIMIT 1)" +
            ") " +
            "FROM Card c " +
            "WHERE c.id_card = :cardId " +
            "AND c.linkedAccount.accountFounder.email = :userEmail")
    Optional<CPWL> findCPWLForConnectedUser(
            @Param("cardId") Long cardId,
            @Param("userEmail") String userEmail);

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.CardOverviewDTO.CardInformations(" +
            "c.linkedAccount.iban, " +
            "c.cardType, " +
            "c.linkedAccount.accountFounder.firstName, " +
            "c.linkedAccount.accountFounder.lastName, " +
            "c.cardNumber, " +
            "c.currentBalance, " +
            "c.availableLimit, " +
            "c.expirationDate, " +
            "c.ccv_cvc) " +  // Removed extra comma here
            "FROM Card c " +
            "WHERE c.id_card = :cardId " +
            "AND c.linkedAccount.accountFounder.email = :userEmail")
    CardInformations findCardInformationsByCardId(
            @Param("cardId") Long cardId,
            @Param("userEmail") String userEmail);

    @Modifying
    @Transactional
    @Query("UPDATE Card c SET c.minimumBalanceAlertForCard = :newAlert " +
            "WHERE c.id_card = :cardId AND c.linkedAccount.accountFounder.email = :userEmail")
    int updateMinimumBalanceAlertForCard(
            @Param("cardId") Long cardId,
            @Param("newAlert") double newAlert,
            @Param("userEmail") String userEmail
    );

}