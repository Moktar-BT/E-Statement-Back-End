package org.estatement.estatementsystemback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue
    private Long id_card;
    private String cardNumber; //(16 chiffres masqués partiellement)
    private String cardHolder;
    private String cardType; // (Visa, MasterCard, etc.)
    private Date expirationDate;
    private double paymentLimit; // Plafond de crédit accordé par la banque (si applicable)
    private double withdrawalLimit;
    private double availableLimit;
    private double currentBalance; // Droit d'utilisation
    private int ccv_cvc ;
    private double minimumBalanceAlertForCard ;
    @ManyToOne
    private Account linkedAccount;
}
