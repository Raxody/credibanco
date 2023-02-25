package com.credibanco.assessment.purchase.service;

import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.helper.UtilityCard;
import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;
import com.credibanco.assessment.purchase.model.factories.FactoryPurchase;
import com.credibanco.assessment.purchase.model.helper.UtilityPurchase;
import com.credibanco.assessment.purchase.persistence.crud.PurchaseCrudRepository;
import com.credibanco.assessment.purchase.repository.PurchaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PurchaseService implements PurchaseRepository {

    private static final String THE_CARD_DOES_NOT_EXIT= "Tarjeta no existe";
    private static final String CARD_NOT_ENROLLED = "Tarjeta no enrrolada";
    private static final String SUCCESS_PURCHASE= "Compra exitosa";
    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO_ONE = "01";
    private static final String ZERO_TWO = "01";
    private static final String ENROLLED = "enrolada";
    private static final String STATUS_TRANSACTION = "Estado transaccion";
    private static final String  REJECTED = "Rechazada";
    private static final String  APPROVED = "Aprobada";
    private static final String  REFERENCE_NUMBER = "Numero de referencia";





    private final PurchaseCrudRepository purchaseCrudRepository;
    private final CardRepository cardRepository;

    public PurchaseService(PurchaseCrudRepository purchaseCrudRepository, CardRepository cardRepository) {
        this.purchaseCrudRepository = purchaseCrudRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public Map<String, String> createTransaction(DtoCreatePurchase dtoCreatePurchase) {
        Optional<Card> card = cardRepository.findCardByPan(dtoCreatePurchase.getPan());
        Map<String, String> responseToCreateTransaction = new HashMap<>();
        if(!card.isPresent()){
            responseToCreateTransaction.put(ZERO_ONE,THE_CARD_DOES_NOT_EXIT);
            responseToCreateTransaction.put(STATUS_TRANSACTION,REJECTED);
        } else if (!card.get().getStatus().equals(ENROLLED)) {
            responseToCreateTransaction.put(ZERO_TWO,CARD_NOT_ENROLLED);
            responseToCreateTransaction.put(STATUS_TRANSACTION,REJECTED);
        }else{
            Purchase purchase = FactoryPurchase.toPurchase(dtoCreatePurchase);
            purchase.setReferenceNumber(this.findLastReferenceNumberAndAssignNewLastReference());
            purchaseCrudRepository.save(purchase);
            responseToCreateTransaction.put(DOUBLE_ZERO,SUCCESS_PURCHASE);
            responseToCreateTransaction.put(STATUS_TRANSACTION,APPROVED);
            responseToCreateTransaction.put(REFERENCE_NUMBER, purchase.getReferenceNumber());
        }
        return responseToCreateTransaction;
    }

    @Override
    @Transactional(readOnly = true)
    public String findLastReferenceNumberAndAssignNewLastReference() {
        Purchase purchase = purchaseCrudRepository.findTopByOrderByReferenceNumberDesc();
        return UtilityPurchase.assignReferenceNumberForPurchase(purchase.getReferenceNumber());
    }

    @Override
    public int deletePurchasesByPan(String pan) {
        return purchaseCrudRepository.deletePurchasesByPan(pan);
    }

    @Override
    public Map<String, String> cancelTransaction(DtoCreatePurchase dtoCreatePurchase) {
        return null;
    }

}


