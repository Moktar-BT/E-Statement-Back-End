package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction") // Explicitement nommé
    private Long id;

    private String title;
    private String operation;
    private String category;
    private LocalDateTime dateTime;
    private boolean status;
    private double amount;
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "id_account" )
    private Account account;


    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Account getRelatedAccount() {
        if (this.card != null) {
            return this.card.getLinkedAccount();
        }
        return this.account;
    }
}