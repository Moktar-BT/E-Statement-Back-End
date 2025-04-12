package org.estatement.estatementsystemback.dto.AccountOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RIB_IBAN_Type {
    private String iban;
    private String rib;
    private String accountType;
}
