package com.credibanco.assessment.purchase.model.helper;

public final class CardNumberEncryptor {

    private static final Integer ZERO = 0;
    private static final Integer FIVE = 5;
    private static final Integer FOUR = 4;
    private static final String ASTERISK  = "*";

    private CardNumberEncryptor() {}

    public static String panEncryptFirstSixAndLastFourCardNumbers(String numberCard){
        StringBuilder numberCardEncrypted = new StringBuilder();
        for(int positionNumber = ZERO; positionNumber < numberCard.length(); positionNumber++){
            if(positionNumber <= FIVE || positionNumber >= (numberCard.length() - FOUR)){
                numberCardEncrypted.append(numberCard.charAt(positionNumber));
            }else{
                numberCardEncrypted.append(ASTERISK);
            }
        }
        return numberCardEncrypted.toString();
    }

}
