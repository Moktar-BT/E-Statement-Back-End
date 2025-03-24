package org.estatement.estatementsystemback.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;



@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id_Transaction;
    private Long number ;
    private String operation ;
    private String category;
    private LocalDateTime date_time ;
    private boolean status ;
    private double amount;
    private String payementMethod ;
    @ManyToOne
    @JoinColumn(name = "idAccount")
    private Account account;

}
