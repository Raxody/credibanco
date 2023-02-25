package com.credibanco.assessment.card.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public final class ValidatorParam {

    private ValidatorParam() {}
    public static ResponseEntity<Map<String, String>> getErrors(BindingResult bindingResult,String numberError) {
        Map<String,String>  errors = new HashMap<>();
        errors.put(numberError," Fallido");
        if(bindingResult.hasErrors()){
            bindingResult
                    .getFieldErrors()
                    .forEach(fieldError ->
                            errors.put(fieldError.getField(),
                                    fieldError.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
