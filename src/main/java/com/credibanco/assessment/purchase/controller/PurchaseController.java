package com.credibanco.assessment.purchase.controller;
import com.credibanco.assessment.card.exceptions.ValidatorParam;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
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
    private static final String ZERO_THREE = "03";
    private final PurchaseService purchaseService;
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createTransaction(@Valid @RequestBody DtoCreatePurchase dtoCreatePurchase,
                                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidatorParam.getErrors(bindingResult, ZERO_THREE);
        }
        Map<String, String> responseToCreateTransaction = purchaseService.createTransaction(dtoCreatePurchase);
        Map.Entry<String, String> firstEntry = responseToCreateTransaction.entrySet().iterator().next();
        String firstKey = firstEntry.getKey();

        return firstKey.equals(DOUBLE_ZERO) ? new ResponseEntity<>(responseToCreateTransaction, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(responseToCreateTransaction, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancelTransaction(@Valid @RequestBody DtoCancelTransactionPurchase dtoCancelTransactionPurchase,
                                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return com.credibanco.assessment.card.exceptions.ValidatorParam.getErrors(bindingResult, ZERO_THREE);
        }
        Map<String, String> responseToCancelTransaction = purchaseService.cancelTransaction(dtoCancelTransactionPurchase);
        Map.Entry<String, String> firstEntry = responseToCancelTransaction.entrySet().iterator().next();
        String firstKey = firstEntry.getKey();

        return firstKey.equals(DOUBLE_ZERO) ? new ResponseEntity<>(responseToCancelTransaction, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(responseToCancelTransaction, HttpStatus.BAD_REQUEST);
    }

}











