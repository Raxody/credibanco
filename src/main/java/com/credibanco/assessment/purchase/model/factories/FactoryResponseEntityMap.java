package com.credibanco.assessment.purchase.model.factories;

import com.credibanco.assessment.purchase.model.entity.Purchase;
import com.credibanco.assessment.purchase.model.helper.CardNumberEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public final class FactoryResponseEntityMap {

    private FactoryResponseEntityMap() {}
   /* public static ResponseEntity<Map<String, String>> toMap(Purchase purchase, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put("00", "Exito");
        responseCard.put("enrollmentNumber",String.valueOf(purchase.getEnrollmentNumber()));
        responseCard.put("pan", CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(purchase.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }

    public static ResponseEntity<Map<String, String>> toMapFindByPan(Purchase purchase, HttpStatus httpStatus) {
        Map<String, String> responseCard = new HashMap<>();
        responseCard.put("00", "Exito");
        responseCard.put("titular",String.valueOf(purchase.getOwner()));
        responseCard.put("cedula",String.valueOf(purchase.getId()));
        responseCard.put("telefono",String.valueOf(purchase.getNumberPhone()));
        responseCard.put("estado",String.valueOf(purchase.getStatus()));
        responseCard.put("pan", CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(purchase.getPan()));
        return new ResponseEntity<>(responseCard, httpStatus);
    }*/

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
