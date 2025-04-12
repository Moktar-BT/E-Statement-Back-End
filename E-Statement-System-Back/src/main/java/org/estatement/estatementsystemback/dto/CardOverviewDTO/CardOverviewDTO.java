package org.estatement.estatementsystemback.dto.CardOverviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardOverviewDTO {
    private Long id;
    private String cardNumber;
    private String cardType;
    private Double balance;
    private boolean status;

}
