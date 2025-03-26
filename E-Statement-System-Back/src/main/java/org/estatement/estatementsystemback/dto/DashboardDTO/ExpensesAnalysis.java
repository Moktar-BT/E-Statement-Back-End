package org.estatement.estatementsystemback.dto.DashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesAnalysis {
    private String category;
    private Double amount;
}
