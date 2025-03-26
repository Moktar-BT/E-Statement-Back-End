package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.DashboardDTO.*;
import org.estatement.estatementsystemback.entity.Transaction;
import org.estatement.estatementsystemback.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/Dashboard/accountsOverview")
    public ResponseEntity<AccountsOverviewDTO> accountsOverview() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        AccountsOverviewDTO overview = userService.getAccountsOverview(email);
        return ResponseEntity.ok(overview);
    }
    @GetMapping("/Dashboard/getIncomesVsExpenses")
    public ResponseEntity <List<IncomesVsExpenses>> getIncomesVsExpenses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ArrayList<IncomesVsExpenses> list = new ArrayList<>(userService.getIncomesVsExpenses(email));
        return ResponseEntity.ok(list);
    }
    @GetMapping("/Dashboard/getBudgetSummary")
    public ResponseEntity<BudgetSummary> getBudgetSummary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getBudgetSummary(email));
    }
    @GetMapping("/Dashboard/getCreditCardSummary")
    public ResponseEntity<List<CreditCardSummary>>getCreditCardSummary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getCreditCardSummaries(email));
    }
    @GetMapping("/Dashboard/getExpensesAnalysis")
    public ResponseEntity<List<ExpensesAnalysis>> getExpensesAnalysis(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getExpensesAnalysis(email));
    }
    @GetMapping("/Dashboard/getLast4transactions")
    public ResponseEntity<List<Last4Transactions>>getLast4transactions(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getLast4Transactions(email));
    }
}
