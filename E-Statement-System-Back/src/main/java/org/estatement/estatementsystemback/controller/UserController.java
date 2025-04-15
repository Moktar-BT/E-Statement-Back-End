package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.DashboardDTO.*;
import org.estatement.estatementsystemback.dto.NotifcationsDTO.NotificationDTO;
import org.estatement.estatementsystemback.entity.Transaction;
import org.estatement.estatementsystemback.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Dashboard")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/accountsOverview")
    public ResponseEntity<AccountsOverviewDTO> accountsOverview() {
        AccountsOverviewDTO overview = userService.getAccountsOverview();
        return ResponseEntity.ok(overview);
    }
    @GetMapping("/getIncomesVsExpenses")
    public ResponseEntity <List<IncomesVsExpenses>> getIncomesVsExpenses() {

        ArrayList<IncomesVsExpenses> list = new ArrayList<>(userService.getIncomesVsExpenses());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getBudgetSummary")
    public ResponseEntity<BudgetSummary> getBudgetSummary() {

        return ResponseEntity.ok(userService.getBudgetSummary());
    }
    @GetMapping("/getCreditCardSummary")
    public ResponseEntity<List<CreditCardSummary>>getCreditCardSummary() {

        return ResponseEntity.ok(userService.getCreditCardSummaries());
    }
    @GetMapping("/getExpensesAnalysis")
    public ResponseEntity<List<ExpensesAnalysis>> getExpensesAnalysis(){
        return ResponseEntity.ok(userService.getExpensesAnalysis());
    }
    @GetMapping("/getLast4transactions")
    public ResponseEntity<List<Last4Transactions>>getLast4transactions(){
        return ResponseEntity.ok(userService.getLast4Transactions());
    }

}
