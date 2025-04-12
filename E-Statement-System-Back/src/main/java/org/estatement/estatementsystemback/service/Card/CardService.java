package org.estatement.estatementsystemback.service.Card;

import org.estatement.estatementsystemback.dto.AccountOverviewDTO.TransactionDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CPWL;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardInformations;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.CardOverviewDTO;
import org.estatement.estatementsystemback.dto.CardOverviewDTO.IBAN_Cnum_Ctype;

import java.util.List;
import java.util.Optional;

public interface CardService {

    List<CardOverviewDTO> findCardOverviewsByUserEmail();
    Optional<IBAN_Cnum_Ctype> findCardDetailsWithIbanForConnectedUser( Long card_id);
    Optional<CPWL>findCPWLForConnectedUser(Long card_id);
    List<TransactionDTO>getCardTransactions(Long cardId, String period, String operationType);
    CardInformations findCardInformationsByCardId(Long card_id);

}
