package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.CPIL;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.RIB_IBAN_Type;
import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.DashboardDTO.CreditCardSummary;
import org.estatement.estatementsystemback.entity.Transaction;
import org.estatement.estatementsystemback.service.account.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Account")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountService;
    @GetMapping("/{id}/rib_iban_type")
    public ResponseEntity<Optional<RIB_IBAN_Type>> getRIB_IBAN_Type(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.getRib_iban_type(id));
    }
    @GetMapping("/list_of_accounts")
    public ResponseEntity<List<AccountInformation>> getRIB_IBAN_Type() {

        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @GetMapping("/{id}/CPIL")
    public ResponseEntity<Optional<CPIL>> getCPIL(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.getCPIL(id));

    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO> > getAccountTransactions(
            @PathVariable Long accountId,
            @RequestParam(required = false, defaultValue = "this_month") String period,
            @RequestParam(required = false, defaultValue = "all") String operationType) {


        List<TransactionDTO>  transactions = accountService.getTransactions( accountId, period, operationType);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("{id}/cards/")
    public ResponseEntity<List<CreditCardSummary>> findCreditCardsByAccountAndUser(@PathVariable Long id) {

        return ResponseEntity.ok(accountService.findCreditCardsByAccountAndUser(id));
    }


}
