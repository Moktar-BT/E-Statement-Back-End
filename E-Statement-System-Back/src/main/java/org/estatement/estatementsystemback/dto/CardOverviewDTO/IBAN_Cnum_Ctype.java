package org.estatement.estatementsystemback.dto.CardOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IBAN_Cnum_Ctype {
    private String iban ;
    private String cardNumber;
    private String cardType;
}
