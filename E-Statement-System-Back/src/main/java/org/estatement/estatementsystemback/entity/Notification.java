package org.estatement.estatementsystemback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long id_notification;
    private String title;
    private String message;
    private LocalDateTime date_notification;
    @ManyToOne
    private User user;

}
