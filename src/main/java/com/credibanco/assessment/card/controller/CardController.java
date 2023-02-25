package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.exceptions.ValidatorParam;
import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.factories.FactoryResponseEntityMap;
import com.credibanco.assessment.card.service.CreateCardService;
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

    private final CreateCardService createCardService;

    public CardController(CreateCardService createCardService) {
        this.createCardService = createCardService;
    }

    @PostMapping("/createCard")
    public ResponseEntity<Map<String,String>> createCard(@Valid @RequestBody DtoCreateCard dtoCreateCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,"00");
        }
        return FactoryResponseEntityMap
                .toMap(createCardService
                                .createCard(dtoCreateCard),
                        HttpStatus.CREATED);
    }

    @PutMapping("/enrollCard")
    public ResponseEntity<Map<String,String>> update(@Valid @RequestBody DtoEnrollCard dtoEnrollCard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidatorParam.getErrors(bindingResult,"04");
        }
        Map<String,String> responseToEnrollCard = createCardService.enrollCard(dtoEnrollCard);
        Map.Entry<String, String> firstEntry = responseToEnrollCard.entrySet().iterator().next();
        String firstKey = firstEntry.getKey();

        return firstKey.equals("00") ?  new ResponseEntity<>(responseToEnrollCard, HttpStatus.ACCEPTED) :
                new ResponseEntity<>(responseToEnrollCard, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{pan}")
    public ResponseEntity<Map<String,String>> findCardByPan(@PathVariable("pan") String pan){
        return createCardService
                .findCardByPan(pan)
                .map(card -> FactoryResponseEntityMap.toMapFindByPan(card,HttpStatus.OK))
                .orElse( new ResponseEntity<>(Collections.singletonMap("01","No encontrado"),HttpStatus.NOT_FOUND));
    }


}











