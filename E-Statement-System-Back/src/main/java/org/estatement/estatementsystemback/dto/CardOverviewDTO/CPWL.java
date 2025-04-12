package org.estatement.estatementsystemback.dto.CardOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CPWL {
    private double current_balance;
    private double payement_limit;
    private double withdrawal_limit;
    private double last_transaction;

}
