package org.estatement.estatementsystemback.dto.StatementDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementTransactionDTO {

    private LocalDateTime dateTime;
    private Long id;
    private String operation;
    private String category;
    private double amount;
}
