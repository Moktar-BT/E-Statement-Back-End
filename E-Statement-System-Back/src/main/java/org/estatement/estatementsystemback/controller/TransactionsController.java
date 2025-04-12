package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;
import org.estatement.estatementsystemback.service.Card.CardServiceImpl;
import org.estatement.estatementsystemback.service.transaction.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Transactions")
public class TransactionsController {
    @Autowired
    private TransactionServiceImpl transactionService;
    @GetMapping("/filtredTransactions")
    public List<TransactionsPageDTO> getFilteredTransactions(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) Long cardId,
            @RequestParam(required = false) Long accountId
    ) {
        return transactionService.getFilteredTransactions(period, operationType, filterType, cardId, accountId);
    }

}
