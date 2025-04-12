package org.estatement.estatementsystemback.service.transaction;

import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.TransactionDAO;
import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionDAO transactionDAO;

    @Override
    public List<TransactionsPageDTO> getFilteredTransactions(String period, String operationType, String filterType, Long cardId, Long accountId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfThisMonth = now.withDayOfMonth(1);
        LocalDateTime firstDayOfLastMonth = firstDayOfThisMonth.minusMonths(1);
        LocalDateTime threeMonthsAgo = now.minusMonths(3);
        LocalDateTime oneYearAgo = now.minusYears(1);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();


        return transactionDAO.getFilteredTransactions(
                email,
                period != null ? period : "last_month",
                operationType != null ? operationType : "all",
                filterType != null ? filterType : "all",
                cardId,
                accountId,
                firstDayOfThisMonth,
                firstDayOfLastMonth,
                threeMonthsAgo,
                oneYearAgo,
                now
        );

    }


}
