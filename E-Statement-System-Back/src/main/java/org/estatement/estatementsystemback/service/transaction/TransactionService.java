package org.estatement.estatementsystemback.service.transaction;

import org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO;
import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<TransactionsPageDTO> findTransactionsWithFilters(
            String period,
            String operationType,
            String source, // "account", "card", or "all"
            Long sourceId
    );

    List<StatementTransactionDTO>findStatementTransactions(  Long accountId,
                                                             Long cardId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate,
                                                             List<String> operationTypes);
}
