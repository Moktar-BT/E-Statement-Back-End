package org.estatement.estatementsystemback.dto.AccountOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long id;
    private String opereation ;
    private String category;
    private LocalDateTime dateTime;
    private boolean status ;
    private Double amount ;

}
