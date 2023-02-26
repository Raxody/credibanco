package com.credibanco.assessment.purchase.service;

import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.helper.CardNumberEncryptor;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;
import com.credibanco.assessment.purchase.model.factories.FactoryPurchase;
import com.credibanco.assessment.purchase.model.helper.UtilityPurchase;
import com.credibanco.assessment.purchase.persistence.crud.PurchaseCrudRepository;
import com.credibanco.assessment.purchase.repository.PurchaseRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PurchaseService implements PurchaseRepository {

    private static final String THE_CARD_DOES_NOT_EXIT = "Tarjeta no existe";
    private static final String CARD_NOT_ENROLLED = "Tarjeta no enrrolada";
    private static final String SUCCESS_PURCHASE = "Compra exitosa";
    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO_ONE = "01";
    private static final String ZERO_TWO = "02";
    private static final String ZERO_FOUR = "04";
    private static final String ENROLLED = "enrolada";
    private static final String STATUS_TRANSACTION = "statusTransaction";
    private static final String REJECTED = "Rechazada";
    private static final String APPROVED = "Aprobada";
    private static final String REFERENCE_NUMBER = "referenceNumber";
    private static final String INVALID_REFERENCE_NUMBER = "Numero de referencia invalido";
    private static final String PURCHASE_CANCELED = "Compra  anulada";
    private static final String CAN_NOT_CANCEL_TRANSACTION = "No se puede anular transaccion";
    private static final String THREE_DATA_DO_NOT_MATCH_WITH_THE_REGISTER ="No coinciden los 3 datos con el registro";
    private static final String CANCELED = "anulada";
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
        if (!card.isPresent()) {
            responseToCreateTransaction.put(ZERO_ONE, THE_CARD_DOES_NOT_EXIT);
            responseToCreateTransaction.put(STATUS_TRANSACTION, REJECTED);
        } else if (!card.get().getStatus().equals(ENROLLED)) {
            responseToCreateTransaction.put(ZERO_TWO, CARD_NOT_ENROLLED);
            responseToCreateTransaction.put(STATUS_TRANSACTION, REJECTED);
        } else {
            Purchase purchase = FactoryPurchase.toPurchase(dtoCreatePurchase);
            purchase.setReferenceNumber(this.findLastReferenceNumberAndAssignNewLastReference());
            purchaseCrudRepository.save(purchase);
            responseToCreateTransaction.put(DOUBLE_ZERO, SUCCESS_PURCHASE);
            responseToCreateTransaction.put(STATUS_TRANSACTION, APPROVED);
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
    @Transactional
    public int deletePurchasesByPan(String pan) {
        return purchaseCrudRepository.deletePurchasesByPan(pan);
    }

    @Override
    @Transactional
    public Map<String, String> cancelTransaction(DtoCancelTransactionPurchase dtoCancelTransactionPurchase) {
        Optional<Purchase> purchase = findPurchaseByReferenceNumber(dtoCancelTransactionPurchase.getReferenceNumber());
        Map<String, String> responseToCancelTransaction = new HashMap<>();
        if (!purchase.isPresent()) {
            responseToCancelTransaction.put(ZERO_ONE, INVALID_REFERENCE_NUMBER);
        } else if (!purchase.get().getPan().equals(dtoCancelTransactionPurchase.getPan()) ||
               !String.valueOf(purchase.get().getPurchaseValue()).equals(dtoCancelTransactionPurchase.getPurchaseValue())) {
            responseToCancelTransaction.put(ZERO_FOUR,THREE_DATA_DO_NOT_MATCH_WITH_THE_REGISTER );

        } else if (UtilityPurchase.transactionCancellationTimeCalculator(purchase.get().getTimePurchase())) {
            responseToCancelTransaction.put(DOUBLE_ZERO, PURCHASE_CANCELED);
            purchase.get().setStatus(CANCELED);
            purchaseCrudRepository.save(purchase.get());
        } else {
            responseToCancelTransaction.put(ZERO_TWO, CAN_NOT_CANCEL_TRANSACTION);
        }
        responseToCancelTransaction.put(REFERENCE_NUMBER, dtoCancelTransactionPurchase.getReferenceNumber());
        return responseToCancelTransaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Purchase> findPurchaseByReferenceNumber(String referenceNumber) {
        return purchaseCrudRepository.findById(referenceNumber);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = (List<Purchase>) purchaseCrudRepository.findAll();
        purchases.stream().forEach(purchase -> purchase.setPan(CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(purchase.getPan())));
        return purchases;
    }


}


