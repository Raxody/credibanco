package com.credibanco.assessment.card.model.factories;

import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.helper.CardNumberEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class FactoryResponseEntityMap {

    private static final String DOUBLE_ZERO = "00";
    private static final String SUCCESS = "Exito";
    private static final String ENROLLMENT_NUMBER = "enrollmentNumber";
    private static final String PAN = "pan";
    private static final String OWNER = "owner";
    private static final String ID = "id";
    private static final String NUMBER_PHONE = "numberPhone";
    private static final String STATUS = "status";
    private FactoryResponseEntityMap() {}
    public static ResponseEntity<Map<String, String>> toMap(Card card, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put(DOUBLE_ZERO, SUCCESS);
        responseCard.put(ENROLLMENT_NUMBER,String.valueOf(card.getEnrollmentNumber()));
        responseCard.put(PAN, CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(card.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }

    public static ResponseEntity<Map<String, String>> toMapFindByPan(Card card, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put(DOUBLE_ZERO, SUCCESS);
        responseCard.put(OWNER,String.valueOf(card.getOwner()));
        responseCard.put(ID,String.valueOf(card.getId()));
        responseCard.put(NUMBER_PHONE,String.valueOf(card.getNumberPhone()));
        responseCard.put(STATUS,String.valueOf(card.getStatus()));
        responseCard.put(PAN, CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(card.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }

}
