package com.credibanco.assessment.card.model.factories;

import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.helper.CardNumberEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class FactoryResponseEntityMap {

    private FactoryResponseEntityMap() {}
    public static ResponseEntity<Map<String, String>> toMap(Card card, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put("00", "Exito");
        responseCard.put("enrollmentNumber",String.valueOf(card.getEnrollmentNumber()));
        responseCard.put("pan", CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(card.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }

    public static ResponseEntity<Map<String, String>> toMapFindByPan(Card card, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put("00", "Exito");
        responseCard.put("titular",String.valueOf(card.getOwner()));
        responseCard.put("cedula",String.valueOf(card.getId()));
        responseCard.put("telefono",String.valueOf(card.getNumberPhone()));
        responseCard.put("estado",String.valueOf(card.getStatus()));
        responseCard.put("pan", CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(card.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }

    /*public static ResponseEntity<Map<String, String>> userToMap(dto user, HttpStatus httpStatus){
        Map<String,String>  responseUser = new HashMap<>();
        responseUser.put("00","Exito");
        responseUser.put("id",String.valueOf(user.getId()));
        responseUser.put("name",user.getName());
        responseUser.put("email",user.getEmail());
        responseUser.put("password", user.getPassword());
        return new ResponseEntity<>(responseUser,httpStatus);
    }*/

}
