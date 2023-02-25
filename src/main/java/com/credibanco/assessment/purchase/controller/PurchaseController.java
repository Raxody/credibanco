package com.credibanco.assessment.purchase.controller;


import com.credibanco.assessment.purchase.exceptions.ValidatorParam;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    private static final String DOUBLE_ZERO = "00";
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/createPurchase")
    public ResponseEntity<Map<String,String>> createTransaction(@Valid @RequestBody DtoCreatePurchase dtoCreatePurchase, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,"03");
        }
        Map<String,String> responseToCreateTransaction = purchaseService.createTransaction(dtoCreatePurchase);
        Map.Entry<String, String> firstEntry = responseToCreateTransaction.entrySet().iterator().next();
        String firstKey = firstEntry.getKey();

        return firstKey.equals(DOUBLE_ZERO) ?  new ResponseEntity<>(responseToCreateTransaction, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(responseToCreateTransaction, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/prueba")
    public ResponseEntity<?> a(){
        String a =this.purchaseService.findLastReferenceNumberAndAssignNewLastReference();
        return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
    }


}











