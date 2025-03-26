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
    @Column(name = "id")
    private Long id;

    private String rib;
    private String iban;
    private String accountNumber;
    private String accountType;
    private double balance;
    private double pendingBalance;
    private double minimumBalanceAlertForAccount;

    @ManyToOne
    @JoinColumn(name = "account_founder_id_user")
    private User accountFounder;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}