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
    private static final String THE_CARD_DOES_NOT_EXIT= "Tarjeta no existe";
    private static final String PAN = "Pan";
    private static final String SUCCESS = "Exito";
    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO_ONE = "01";
    private static final String ZERO_TWO = "02";
    private static final String ENROLLED = "enrolada";
    private static final String  INVALID_VALIDATION_NUMBER = "Numero de validación invalido";
    private final CardCrudRepository cardCrudRepository;
    private final PurchaseRepository purchaseRepository;

    public CardService(CardCrudRepository cardCrudRepository, PurchaseRepository purchaseRepository) {
        this.cardCrudRepository = cardCrudRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> getAllCards() {
        List<Card> cards = (List<Card>) cardCrudRepository.findAll();
        cards.forEach(card -> card.setPan(CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(card.getPan())));
        return cards;
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
            return Collections.singletonMap(ZERO_ONE,THE_CARD_DOES_NOT_EXIT);
        } else if (card.get().getEnrollmentNumber().equals(Integer.parseInt(dtoEnrollCard.getNumberValidation()))) {
            card.get().setStatus(ENROLLED);
            cardCrudRepository.save(card.get());
            responseToEnrollCard.put(DOUBLE_ZERO,SUCCESS);
            responseToEnrollCard.put(PAN,encryptedPan);
            return responseToEnrollCard;
        }
        responseToEnrollCard.put(ZERO_TWO,INVALID_VALIDATION_NUMBER);
        responseToEnrollCard.put(PAN,encryptedPan);
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
