package org.estatement.estatementsystemback.dto.DashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSummary {

    private Double total;
    private Double expenses ;
    private Double remaining;
    private Double expensesPercentage;
    private Double remainingPercentage;

}
