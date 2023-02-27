package com.credibanco.assessment.card.model.service;


import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.testdatabuilder.CardTestDataBuilder;
import com.credibanco.assessment.card.persistence.crud.CardCrudRepository;
import com.credibanco.assessment.card.service.CardService;
import com.credibanco.assessment.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest {

    private final CardCrudRepository cardRepository = Mockito.mock(CardCrudRepository.class);
    private final PurchaseRepository purchaseRepository = Mockito.mock(PurchaseRepository.class);
    private final CardService cardService = new CardService(cardRepository,purchaseRepository);

    @Test
    @DisplayName("This test create a card and assign a pan")
    void shouldCreateACardSuccess(){
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withTypeCard("debito")
                .buildDtoCreateCard();
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .build();
        Mockito.when(cardRepository.save(Mockito.any())).thenReturn(new Card());
        Mockito.when(cardRepository.findTopByOrderByPanDesc()).thenReturn(card);

        // Act
        Card createdCard = cardService.createCard(dtoCreateCard);

        // Assert
        assertNotNull(createdCard);
    }

    @Test
    @DisplayName("This test obtains all the cards ")
    void shouldGetAllCardsSuccess(){
        // Arrange
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .build();
        List<Card> cards = Arrays.asList(card);
        Mockito.when(cardRepository.findAll()).thenReturn(cards);

        // Act
        List<Card> responseListCards = cardService.getAllCards();

        // Assert
        assertEquals(1,responseListCards.size());
    }

    @Test
    @DisplayName("This test find and obtain a card by pan ")
    void shouldFindACardByPanSuccess(){
        // Arrange
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withStatus("creada")
                .withEnrollmentNumber("46")
                .build();
        Mockito.when(cardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(card));

        // Act
        Optional<Card> responseCard = cardService.findCardByPan("1000000000000000");

        // Assert
        assertNotNull(responseCard);
    }

    @Test
    @DisplayName("This test find a card by pan and later delete the card")
    void shouldDeleteACardByPanSuccess(){
        // Arrange
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .build();
        Mockito.when(cardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(card));
        Optional<Card> responseCard = cardService.findCardByPan("1000000000000000");
        // Act
        boolean responseDelete = cardService.deleteCard(responseCard.get().getPan());

        // Assert
        assertTrue(responseDelete);
    }


    @Test
    @DisplayName("should not be able to enroll the card because you cannot find the card")
    void shouldNotEnrollTheCardBecauseCannotFindTheCard(){
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000f")
                .withEnrollmentNumber("55")
                .buildDtoEnrollCard();
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .build();
        Mockito.when(cardRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Act
        Map<String,String> responseCard = cardService.enrollCard(dtoEnrollCard);

        // Assert
        assertEquals(responseCard, Collections.singletonMap("01", "Tarjeta no existe"));
    }

    @Test
    @DisplayName("should not be able to enroll the card because you cannot find the card")
    void shouldEnrollTheCardSuccess(){
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("55")
                .buildDtoEnrollCard();

        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("55")
                .build();
        Map<String,String> esperado = new HashMap<>();
        esperado.put("00", "Exito");
        esperado.put("Pan", "100000******0000");

        Mockito.when(cardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(card));

        // Act
        Map<String,String> responseToCreatePurchase = cardService.enrollCard(dtoEnrollCard);

        // Assert
        assertEquals(responseToCreatePurchase, esperado);

    }

    @Test
    @DisplayName("should not enroll the card because the validation number is invalid")
    void shouldNotEnrollTheCardBecauseTheValidationNumberIsInvalid(){
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("55")
                .buildDtoEnrollCard();

        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("44")
                .build();
        Map<String,String> esperado = new HashMap<>();
        esperado.put("02", "Numero de validación invalido");
        esperado.put("Pan", "100000******0000");
        Mockito.when(cardRepository.findById(Mockito.anyString())).thenReturn(Optional.of(card));

        // Act
        Map<String,String> responseToEnrollCard = cardService.enrollCard(dtoEnrollCard);

        // Assert
        assertEquals(responseToEnrollCard, esperado);
    }

}
