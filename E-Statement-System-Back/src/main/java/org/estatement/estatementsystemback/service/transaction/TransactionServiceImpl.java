package org.estatement.estatementsystemback.service.transaction;

import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.TransactionDAO;
import org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO;
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
    public List<TransactionsPageDTO> findTransactionsWithFilters(
            String period,
            String operationType,
            String source, // "account", "card", or "all"
            Long sourceId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = switch (period.toLowerCase()) {
            case "last_week" -> now.minusWeeks(1);
            case "last_month" -> now.minusMonths(1);
            case "last_quarter" -> now.minusMonths(3);
            case "last_year" -> now.minusYears(1);
            default -> now.minusYears(10); // default to 10 years ago
        };

        return transactionDAO.findTransactionsWithFilters(
                email,
                source != null ? source : "all",
                sourceId,
                startDate,
                now,
                operationType != null ? operationType : "all"
        );
    }


    @Override
    public List<StatementTransactionDTO> findStatementTransactions(Long accountId, Long cardId, LocalDateTime startDate, LocalDateTime endDate, List<String> operationTypes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return transactionDAO.findStatementTransactionsByUserEmail(email,accountId,cardId,startDate,endDate,operationTypes);
    }


}
