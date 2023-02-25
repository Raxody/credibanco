package com.credibanco.assessment.purchase.repository;

import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;

import java.util.Map;

public interface PurchaseRepository {

    Map<String, String> createTransaction(DtoCreatePurchase dtoCreatePurchase);

    String findLastReferenceNumberAndAssignNewLastReference();
    int deletePurchasesByPan(String pan);
    Map<String, String> cancelTransaction(DtoCreatePurchase dtoCreatePurchase);

}
