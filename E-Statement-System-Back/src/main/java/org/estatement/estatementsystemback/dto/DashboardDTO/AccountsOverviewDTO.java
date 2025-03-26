package org.estatement.estatementsystemback.dto.DashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsOverviewDTO {
    private Double ToTalBalance;
    private Double interestRate;
    private Double LastTransaction;
    private Double  CreditCards;


}
