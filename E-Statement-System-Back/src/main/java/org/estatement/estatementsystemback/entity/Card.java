package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue
    private Long id_card;
    private String cardNumber; //(16 chiffres masqués partiellement)
    private String cardType; // (Visa, MasterCard, etc.)
    private Date expirationDate;
    private boolean status;
    private double paymentLimit; // Plafond de crédit accordé par la banque (si applicable)
    private double withdrawalLimit;
    private double availableLimit;
    private double currentBalance; // Droit d'utilisation
    private int ccv_cvc ;
    private double minimumBalanceAlertForCard ;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> cardTransactions;

    @ManyToOne
    private Account linkedAccount;
}
