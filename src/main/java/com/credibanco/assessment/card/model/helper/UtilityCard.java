package com.credibanco.assessment.card.model.helper;

import java.util.Random;

public final class UtilityCard {
    private static final int ONE = 1;
    private static final int ONE_HUNDRED = 1;

    private UtilityCard() {}

    public static String assignCardNumberForPan(String pan){
        return String.valueOf(Long.parseLong(pan) + ONE);
    }
    public static int randomNumberFromOneToOneHundredToEnrollCard(){
        Random random = new Random();
         return (random.nextInt(ONE_HUNDRED) + ONE);
    }

}
