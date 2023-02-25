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

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/createCard")
    public ResponseEntity<Map<String,String>> createCard(@Valid @RequestBody DtoCreateCard dtoCreateCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,"00");
        }
        return FactoryResponseEntityMap
                .toMap(cardService
                                .createCard(dtoCreateCard),
                        HttpStatus.CREATED);
    }

    @PutMapping("/enrollCard")
    public ResponseEntity<Map<String,String>> update(@Valid @RequestBody DtoEnrollCard dtoEnrollCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,"04");
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
                .orElse( new ResponseEntity<>(Collections.singletonMap("01","No encontrado"),HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{pan}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("pan") String pan){
        return cardService.deleteCard(pan) ?
                new ResponseEntity<>(Collections.singletonMap("00","Se ha eliminado la tarjeta"),HttpStatus.OK):
                new ResponseEntity<>(Collections.singletonMap("01","No se ha eliminado la tarjeta"),HttpStatus.NOT_FOUND);
    }


}











