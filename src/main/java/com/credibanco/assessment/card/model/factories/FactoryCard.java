package com.credibanco.assessment.card.model.factories;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.entity.Credit;
import com.credibanco.assessment.card.model.entity.Debit;

public class FactoryCard {
    private static final String DEBITO = "debito";

    private FactoryCard() {}
    public static Card toCreateSpecificCard(DtoCreateCard dtoCreateCard){
        return (dtoCreateCard.getTypeCard().equals(DEBITO)) ?
                new Debit(dtoCreateCard.getOwner(),
                dtoCreateCard.getId(),
                dtoCreateCard.getNumberPhone()) :
                new Credit(dtoCreateCard.getOwner(),
                        dtoCreateCard.getId(),
                        dtoCreateCard.getNumberPhone());
    }
}
