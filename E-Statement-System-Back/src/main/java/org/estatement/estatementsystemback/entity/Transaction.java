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
    @Column(name = "id_transaction")
    private Long idTransaction;

    private String title;
    private String operation;
    private String category;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "status")
    private boolean status;

    private double amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
}