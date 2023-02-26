package com.credibanco.assessment.purchase.repository;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PurchaseRepository {

    Map<String, String> createTransaction(DtoCreatePurchase dtoCreatePurchase);

    String findLastReferenceNumberAndAssignNewLastReference();
    int deletePurchasesByPan(String pan);
    Map<String, String> cancelTransaction(DtoCancelTransactionPurchase dtoCancelTransactionPurchase);
    Optional<Purchase> findPurchaseByReferenceNumber(String referenceNumber);

    List<Purchase> getAllPurchases();

}
