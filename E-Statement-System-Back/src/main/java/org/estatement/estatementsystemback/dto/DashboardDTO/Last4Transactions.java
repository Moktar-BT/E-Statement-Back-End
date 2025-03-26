package org.estatement.estatementsystemback.dto.DashboardDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class Last4Transactions {
    private Long idTransaction;
    private boolean status;
    private double amount;
    private String title;
}
