package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.exceptions.ValidatorParam;
import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.factories.FactoryResponseEntityMap;
import com.credibanco.assessment.card.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("card")
public class CardController {

    private static final String DOUBLE_ZERO = "00";
    private static final String ZERO_ONE = "01";
    private static final String ZERO_TWO = "02";
    private static final String ZERO_FOUR = "04";
    private static final String NOT_FOUND = "No encontrado";
    private static final String THE_CARD_HAS_BEEN_REMOVED = "Se ha eliminado la tarjeta";
    private static final String THE_CARD_HAS_NOT_BEEN_REMOVED = "No se ha eliminado la tarjeta";
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createCard(@Valid @RequestBody DtoCreateCard dtoCreateCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,DOUBLE_ZERO);
        }
        return FactoryResponseEntityMap
                .toMap(cardService
                                .createCard(dtoCreateCard),
                        HttpStatus.CREATED);
    }

    @PutMapping("/enroll")
    public ResponseEntity<Map<String,String>> update(@Valid @RequestBody DtoEnrollCard dtoEnrollCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,ZERO_FOUR);
        }
        Map<String,String> responseToEnrollCard = cardService.enrollCard(dtoEnrollCard);
        Map.Entry<String, String> firstEntry = responseToEnrollCard.entrySet().iterator().next();
        String firstKey = firstEntry.getKey();

        return firstKey.equals("00") ?  new ResponseEntity<>(responseToEnrollCard, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(responseToEnrollCard, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{pan}")
    public ResponseEntity<Map<String,String>> findCardByPan(@PathVariable("pan") String pan){
        return cardService
                .findCardByPan(pan)
                .map(card -> FactoryResponseEntityMap.toMapFindByPan(card,HttpStatus.OK))
                .orElse( new ResponseEntity<>(Collections.singletonMap(ZERO_ONE,NOT_FOUND),HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{pan}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("pan") String pan){
        return cardService.deleteCard(pan) ?
                new ResponseEntity<>(Collections.singletonMap(DOUBLE_ZERO,THE_CARD_HAS_BEEN_REMOVED),HttpStatus.OK):
                new ResponseEntity<>(Collections.singletonMap(ZERO_ONE,THE_CARD_HAS_NOT_BEEN_REMOVED),HttpStatus.NOT_FOUND);
    }


}











