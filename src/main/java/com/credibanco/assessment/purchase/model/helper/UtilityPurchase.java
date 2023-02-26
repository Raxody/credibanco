package com.credibanco.assessment.purchase.model.helper;
import java.time.LocalDateTime;

public final class UtilityPurchase {
    private static final int ONE = 1;
    private static final int FIVE = 5;
    private static final int ZERO = 0;

    private UtilityPurchase() {}

    public static String assignReferenceNumberForPurchase(String referenceNumber){
        return String.valueOf(Long.parseLong(referenceNumber) + ONE);
    }

    public static boolean transactionCancellationTimeCalculator(LocalDateTime registeredTime){
        LocalDateTime maximumReversionTime = registeredTime.plusMinutes(FIVE);
        return maximumReversionTime.compareTo(LocalDateTime.now()) > ZERO;
    }

}
