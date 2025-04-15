package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.AccountInformation;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO;
import org.estatement.estatementsystemback.dto.NotifcationsDTO.NotificationDTO;
import org.estatement.estatementsystemback.dto.SettingsDTO.ConnectionDTO;
import org.estatement.estatementsystemback.service.Card.CardServiceImpl;
import org.estatement.estatementsystemback.service.account.AccountServiceImpl;
import org.estatement.estatementsystemback.service.connection.ConnectionServiceImpl;
import org.estatement.estatementsystemback.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/Settings")
public class SettingsController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private CardServiceImpl cardService;
    @Autowired
    private ConnectionServiceImpl connectionservice;

    @PutMapping("/SetNotifications")
    public void SetNotifications(@RequestBody NotificationDTO dto){
        userService.setNotifications(dto);
    }
    @GetMapping("/GetNotifications")
    public NotificationDTO getNotifications(){
        return userService.getNotificationPreferences();
    }
    @PutMapping("/Account-balance-limit")
    public ResponseEntity<String> updateBalanceLimit(
            @RequestParam Long accountId,
            @RequestParam double limitBalance) throws AccountNotFoundException {
        accountService.updateMinimumBalanceAlert(accountId, limitBalance);
        return ResponseEntity.ok("Minimum balance alert updated successfully");
    }

    @PutMapping("/Card-balance-limit")
    public ResponseEntity<String> updateCardBalanceLimit(
            @RequestParam Long cardId,
            @RequestParam double limitBalance) {
        cardService.updateMinimumBalanceAlert(cardId, limitBalance);
        return ResponseEntity.ok("Card minimum balance alert updated successfully");
    }
    @GetMapping("/list-of-accounts")
    public ResponseEntity<List<AccountInformation>> getRIB_IBAN_Type() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
    @GetMapping("/list-of-cards")
    public ResponseEntity<List<CardOverviewDTO>> findCardOverviewsByUserEmail() {
        return ResponseEntity.ok(cardService.findCardOverviewsByUserEmail());
    }
    @GetMapping("/ConnectionHistory")
    public List<ConnectionDTO> getConnections(){
        return connectionservice.getConnectionsByUserEmail();
    }



}
