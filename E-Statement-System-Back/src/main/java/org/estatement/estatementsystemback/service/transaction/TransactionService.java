package org.estatement.estatementsystemback.service.transaction;

import org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO;
import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<TransactionsPageDTO> getFilteredTransactions(
            String period,
            String operationType,
            String filterType,
            Long cardId,
            Long accountId
    );
    List<StatementTransactionDTO>findStatementTransactions(  Long accountId,
                                                             Long cardId,
                                                             LocalDateTime startDate,
                                                             LocalDateTime endDate,
                                                             List<String> operationTypes);
}
