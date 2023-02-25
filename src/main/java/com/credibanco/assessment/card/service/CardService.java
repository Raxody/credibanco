package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.factories.FactoryCard;
import com.credibanco.assessment.card.model.helper.CardNumberEncryptor;
import com.credibanco.assessment.card.model.helper.UtilityCard;
import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.purchase.repository.PurchaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class CardService implements CardRepository {
    private static final String CREATED = "creada";
    private final CardCrudRepository cardCrudRepository;
    private final PurchaseRepository purchaseRepository;

    public CardService(CardCrudRepository cardCrudRepository, PurchaseRepository purchaseRepository) {
        this.cardCrudRepository = cardCrudRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional
    public Card createCard(DtoCreateCard dtoCreateCard) {
        Card card = FactoryCard.toCreateSpecificCard(dtoCreateCard);
        card.setPan(findLastPanAndAssignNewPan());
        card.setEnrollmentNumber(UtilityCard.randomNumberFromOneToOneHundredToEnrollCard());
        card.setStatus(CREATED);
        return cardCrudRepository.save(card);
    }

    @Override
    @Transactional(readOnly = true)
    public String findLastPanAndAssignNewPan() {
        Card card = cardCrudRepository.findTopByOrderByPanDesc();
        return UtilityCard.assignCardNumberForPan(card.getPan());
    }

    @Override
    @Transactional
    public Map<String, String> enrollCard(DtoEnrollCard dtoEnrollCard) {
        Optional<Card> card = findCardByPan(dtoEnrollCard.getPan());
        Map<String, String> responseToEnrollCard = new HashMap<>();
        String encryptedPan = CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(dtoEnrollCard.getPan());
        if(!card.isPresent()){
            return Collections.singletonMap("01","Tarjeta no existe");
        } else if (card.get().getEnrollmentNumber().equals(Integer.parseInt(dtoEnrollCard.getNumberValidation()))) {
            card.get().setStatus("enrolada");
            cardCrudRepository.save(card.get());
            responseToEnrollCard.put("00","Exito");
            responseToEnrollCard.put("pan",encryptedPan);
            return responseToEnrollCard;
        }
        responseToEnrollCard.put("02","Numero de validación invalido");
        responseToEnrollCard.put("pan",encryptedPan);
        return responseToEnrollCard;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> findCardByPan(String pan) {
        return cardCrudRepository.findById(pan);
    }

    @Override
    @Transactional
    public boolean deleteCard(String pan) {
        return findCardByPan(pan)
                .map(card -> {
                    purchaseRepository.deletePurchasesByPan(pan);
                    cardCrudRepository.deleteById(pan);
                    return Boolean.TRUE;})
                .orElse(Boolean.FALSE);
    }


}
