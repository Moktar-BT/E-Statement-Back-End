package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id ;
    private String rib;
    private String iban ;
    private String accountNumber;
    private String accountType;
    private double balance;
    private double pendingBalance;
    private double minimumBalanceAlertForAccount ;
    @ManyToOne
    private User accountFounder ;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    @OneToMany
    private List<Statement> statements ;



}
