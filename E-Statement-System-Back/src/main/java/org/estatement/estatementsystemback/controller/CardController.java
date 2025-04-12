package org.estatement.estatementsystemback.controller;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CPWL;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardInformations;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.IBAN_Cnum_Ctype;
import org.estatement.estatementsystemback.service.Card.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Card")
public class CardController {
    @Autowired
    private CardServiceImpl cardService;
    @GetMapping("/list_of_cards")
    public ResponseEntity<List<CardOverviewDTO>> findCardOverviewsByUserEmail() {

        return ResponseEntity.ok(cardService.findCardOverviewsByUserEmail());
    }
    @GetMapping("/{id}/IBAN_Cnum_Ctype")
    public ResponseEntity<Optional<IBAN_Cnum_Ctype>> findCardOverviewByIBAN(@PathVariable long id) {
        return ResponseEntity.ok(cardService.findCardDetailsWithIbanForConnectedUser( id));
    }
    @GetMapping("/{id}/CPWL")
    public ResponseEntity<Optional<CPWL>>findCPWLForConnectedUser(@PathVariable long id){
        try {

            return ResponseEntity.ok(cardService.findCPWLForConnectedUser(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{id}/Transactions")
    public ResponseEntity<List<TransactionDTO>> findTransactionsForCard(
            @PathVariable long id ,
            @RequestParam(required = false, defaultValue = "this_month") String period,
            @RequestParam(required = false, defaultValue = "all") String operationType
    ) {
        return ResponseEntity.ok(cardService.getCardTransactions(id, period, operationType));
    }
    @GetMapping("{id}/informations")
    public ResponseEntity<CardInformations> getCardInformations(@PathVariable long id) {
        return ResponseEntity.ok(cardService.findCardInformationsByCardId(id));
    }
}
