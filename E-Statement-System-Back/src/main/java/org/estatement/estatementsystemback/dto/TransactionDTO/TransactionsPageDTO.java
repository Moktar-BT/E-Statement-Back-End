package org.estatement.estatementsystemback.dto.TransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsPageDTO {
    private Long id;
    private String opereation ;
    private String category;
    private String paymentMethod;
    private String accountRib;
    private String carteNumber;
    private LocalDateTime dateTime;
    private boolean status ;
    private Double amount ;
}
