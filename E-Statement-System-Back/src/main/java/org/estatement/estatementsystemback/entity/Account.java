package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private boolean status;
    private String rib;
    private String iban;
    private String accountName;
    private String accountType;
    private double balance;
    private double pendingBalance;
    private double minimumBalanceAlertForAccount;
    private double interestRate;

    @ManyToOne
    @JoinColumn(name = "account_founder_id_user")
    private User accountFounder;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> accountTransactions;

    @OneToMany(mappedBy = "linkedAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;
}