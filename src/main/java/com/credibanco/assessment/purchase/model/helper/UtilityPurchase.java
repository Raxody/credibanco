package com.credibanco.assessment.purchase.model.helper;

import java.util.Random;

public final class UtilityPurchase {
    private static final int ONE = 1;
    private static final int ONE_HUNDRED = 1;

    private UtilityPurchase() {}

    public static String assignReferenceNumberForPurchase(String referenceNumber){
        return String.valueOf(Long.parseLong(referenceNumber) + ONE);
    }

}
