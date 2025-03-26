package org.estatement.estatementsystemback.dto.DashboardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardSummary {
    private String cardType;
    private String cardNumber;
    private double currentBalance;
    private double availableLimit;
    private Date expirationDate;
    private int ccv_cvc ;
    private String cardFounderName;
}
