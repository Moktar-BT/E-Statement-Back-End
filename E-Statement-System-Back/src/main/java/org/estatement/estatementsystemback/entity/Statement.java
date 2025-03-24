package org.estatement.estatementsystemback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Statement {
    @Id
    @GeneratedValue
    private Long id_statement;
    private String title ;
    private String type ;
    private double size ;
    private boolean status ;
    private String description ;
    private Long downloadFrequency;
    @ManyToOne
    private Account account ;


}
