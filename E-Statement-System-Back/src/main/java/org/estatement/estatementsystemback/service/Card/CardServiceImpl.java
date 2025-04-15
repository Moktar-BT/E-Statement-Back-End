package org.estatement.estatementsystemback.service.Card;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.CardDAO;
import org.estatement.estatementsystemback.dao.TransactionDAO;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CPWL;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardInformations;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.IBAN_Cnum_Ctype;
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
public class CardServiceImpl implements CardService {
    private final CardDAO cardDAO;
    private final TransactionDAO transactionDAO;

    @Override
    public List<CardOverviewDTO>findCardOverviewsByUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return cardDAO.findCardOverviewsByUserEmail(email);
    }

    @Override
    public Optional<IBAN_Cnum_Ctype> findCardDetailsWithIbanForConnectedUser(Long card_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return cardDAO.findCardDetailsWithIbanForConnectedUser(card_id, email);
    }

    @Override
    public Optional<CPWL> findCPWLForConnectedUser(Long card_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return cardDAO.findCPWLForConnectedUser(card_id, email);
    }

    @Override
    public List<TransactionDTO> getCardTransactions(Long cardId, String period, String operationType) {
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
        return transactionDAO.getCardTransactions(email, cardId, period, operationType, weekStartDate, threeMonthsAgo);
    }

    @Override
    public CardInformations findCardInformationsByCardId(Long card_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return cardDAO.findCardInformationsByCardId(card_id,email);
    }

    @Override
    @Transactional
    public void updateMinimumBalanceAlert(Long cardId, double newAlert) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        int updatedRows = cardDAO.updateMinimumBalanceAlertForCard(
                cardId,
                newAlert,
                userEmail
        );

    }

}
