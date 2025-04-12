package org.estatement.estatementsystemback.dto.CardOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInformations {
    private String iban ;
    private String type;
    private String ownerFirstName;
    private String ownerLastName;
    private String cardNumber;
    private double currentbalance ;
    private double availabLimit;
    private Date expirationDate;
    private int cvv_cvc;

}
