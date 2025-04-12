package org.estatement.estatementsystemback.dto.AccountOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInformation {
    private Long id;
    private String accountType;
    private String accountName;
    private boolean status;
    private String rib;
    private Double balance;
    private String iban;
}
