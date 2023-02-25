package com.credibanco.assessment.card.repository;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;

import java.util.Map;
import java.util.Optional;

public interface CardRepository {

    Card createCard(DtoCreateCard dtoCreateCard);

    String findLastPanAndAssignNewPan();

    Map<String, String> enrollCard(DtoEnrollCard dtoEnrollCard);
    Optional<Card> findCardByPan(String pan);

    boolean deleteCard(String pan);


}
