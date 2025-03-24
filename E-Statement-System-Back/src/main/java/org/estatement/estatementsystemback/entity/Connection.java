package org.estatement.estatementsystemback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Connection {
    @Id
    @GeneratedValue
    private Long id_connection_history;
    private LocalDateTime date_time;
    private String location ;
    private String deviceType;
    private String deviceName;
    private boolean status ;
    @ManyToOne
    private User user;


}
