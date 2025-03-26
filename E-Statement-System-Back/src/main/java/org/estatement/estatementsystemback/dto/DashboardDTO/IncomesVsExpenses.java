package org.estatement.estatementsystemback.dto.DashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class IncomesVsExpenses {
    private String period;
    private Double income;
    private Double expense;
}
