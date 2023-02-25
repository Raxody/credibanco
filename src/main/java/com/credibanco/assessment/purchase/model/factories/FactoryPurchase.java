package com.credibanco.assessment.purchase.model.factories;

import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;

import java.time.LocalDateTime;

public class FactoryPurchase {
    private static final String  APPROVED = "Aprobada";
    private FactoryPurchase() {}
    public static Purchase toPurchase(DtoCreatePurchase dtoCreatePurchase){
        return new Purchase(dtoCreatePurchase.getPan(),Long.parseLong(dtoCreatePurchase.getPurchaseValue()),
                dtoCreatePurchase.getPurchaseAddress(),APPROVED , LocalDateTime.now());
    }
}
