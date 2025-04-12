package org.estatement.estatementsystemback.service.transaction;

import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;

import java.util.List;

public interface TransactionService {
    public List<TransactionsPageDTO> getFilteredTransactions(
            String period,
            String operationType,
            String filterType,
            Long cardId,
            Long accountId
    );
}
