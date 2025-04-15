package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.StatementDTO.StatementTransactionDTO;
import org.estatement.estatementsystemback.dto.TransactionDTO.TransactionsPageDTO;
import org.estatement.estatementsystemback.service.Card.CardServiceImpl;
import org.estatement.estatementsystemback.service.transaction.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
            @RequestParam(required = false) String source,
            @RequestParam(required = false) Long sourceId

    ) {
        return transactionService.findTransactionsWithFilters(period,
               operationType,
                 source,
                sourceId);
    }
    @GetMapping("/StatementTransactions")
    public List<StatementTransactionDTO> getStatementTransactions(
            @RequestParam(value = "accountId", required = false) Long accountId,
            @RequestParam(value = "cardId", required = false) Long cardId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam("operationTypes") List<String> operationTypes){
        return transactionService.findStatementTransactions(
                accountId,
                cardId,
                startDate,
                endDate,
                operationTypes);
    }

}
