package org.estatement.estatementsystemback.dto.AccountOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CPIL {
    private double currente_balance;
    private double panding_balance;
    private double interest_balance;
    private double last_transaction;

}
