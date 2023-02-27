package com.credibanco.assessment.purchase.model.service;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.testdatabuilder.CardTestDataBuilder;
import com.credibanco.assessment.card.repository.CardRepository;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;
import com.credibanco.assessment.purchase.model.testdatabuilder.PurchaseTestDataBuilder;
import com.credibanco.assessment.purchase.persistence.crud.PurchaseCrudRepository;
import com.credibanco.assessment.purchase.service.PurchaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseServiceTest {

    private final PurchaseCrudRepository purchaseCrudRepository = Mockito.mock(PurchaseCrudRepository.class);
    private final CardRepository cardRepository = Mockito.mock(CardRepository.class);
    private final PurchaseService purchaseService = new PurchaseService(purchaseCrudRepository, cardRepository);

    @Test
    @DisplayName("This test find and obtain a card by reference number ")
    void shouldFindAPurchaseByPanSuccess() {
        // Arrange
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.of(purchase));

        // Act
        Optional<Purchase> responsePurchase = purchaseService.findPurchaseByReferenceNumber("123456");

        // Assert
        assertNotNull(responsePurchase);
    }

    @Test
    @DisplayName("This test obtains all the purchases ")
    void shouldGetAllPurchasesSuccess() {
        // Arrange
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();

        List<Purchase> purchases = Arrays.asList(purchase);
        Mockito.when(purchaseCrudRepository.findAll()).thenReturn(purchases);

        // Act
        List<Purchase> responseListPurchases = purchaseService.getAllPurchases();

        // Assert
        assertEquals(1, responseListPurchases.size());
    }

    @Test
    @DisplayName("This test delete all purchases that have the same pan")
    void shouldDeleteAPurchaseByPanSuccess() {
        // Arrange - Act
        Mockito.when(purchaseCrudRepository.deletePurchasesByPan(Mockito.anyString())).thenReturn(1);
        int response = purchaseService.deletePurchasesByPan("1000000000000000");

        // Assert
        assertEquals(1, response);
    }

    @Test
    @DisplayName("This test should not create a purchase because it doesn't find a card with this pan")
    void shouldNotCreateAPurchaseBecauseDoesNotFind() {
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("Usuario prueba")
                .withPurchaseAddress("Calle falsa # 15")
                .withPurchaseValue("150000")
                .buildDtoCreatePurchase();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("01", "Tarjeta no existe");
        expectedResponse.put("statusTransaction", "Rechazada");
        Mockito.when(cardRepository.findCardByPan(Mockito.anyString())).thenReturn(Optional.empty());

        // Act
        Map<String, String> responsePurchase = purchaseService.createTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should not create a purchase because the status isn't enrolled")
    void shouldNotCreateAPurchaseBecauseTheStatusIsNotEnrolled() {
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("Usuario prueba")
                .withPurchaseAddress("Calle falsa # 15")
                .withPurchaseValue("150000")
                .buildDtoCreatePurchase();
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withStatus("creado")
                .withEnrollmentNumber("46")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("02", "Tarjeta no enrrolada");
        expectedResponse.put("statusTransaction", "Rechazada");
        Mockito.when(cardRepository.findCardByPan(Mockito.anyString())).thenReturn(Optional.of(card));

        // Act
        Map<String, String> responsePurchase = purchaseService.createTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test create a purchase ")
    void shouldCreateACardSuccess(){
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("Usuario prueba")
                .withPurchaseAddress("Calle falsa # 15")
                .withPurchaseValue("150000")
                .buildDtoCreatePurchase();
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withStatus("enrolada")
                .build();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000000")
                .withReferenceNumber("123456")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("00", "Compra exitosa");
        expectedResponse.put("referenceNumber", "123457");
        expectedResponse.put("statusTransaction", "Aprobada");
        Mockito.when(cardRepository.findCardByPan(Mockito.anyString())).thenReturn(Optional.of(card));
        Mockito.when(purchaseCrudRepository.findTopByOrderByReferenceNumberDesc()).thenReturn(purchase);

        // Act
        Map<String, String> responsePurchase = purchaseService.createTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should not cancel the purchase because id doesn't find the reference number")
    void shouldNotCancelThePurchaseBecauseDoesNotFind() {
        // Arrange
        DtoCancelTransactionPurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("Usuario prueba")
                .withReferenceNumber("123456")
                .withPurchaseValue("150000")
                .buildDtoCancelTransactionPurchase();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("01", "Numero de referencia invalido");
        expectedResponse.put("referenceNumber", "123456");
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        // Act
        Map<String, String> responsePurchase = purchaseService.cancelTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should not cancel the purchase because the pan doesn't match")
    void shouldNotCancelThePurchaseBecauseTheDataDoNotMatchWithPan() {
        // Arrange
        DtoCancelTransactionPurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withReferenceNumber("123456")
                .withPurchaseValue("150000")
                .buildDtoCancelTransactionPurchase();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000001")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("04", "No coinciden los 3 datos con el registro");
        expectedResponse.put("referenceNumber", "123456");
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.of(purchase));

        // Act
        Map<String, String> responsePurchase = purchaseService.cancelTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should not cancel the purchase because the purchase value doesn't match")
    void shouldNotCancelThePurchaseBecauseTheDataDoNotMatchWithPurchaseValue() {
        // Arrange
        DtoCancelTransactionPurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withReferenceNumber("123456")
                .withPurchaseValue("150000")
                .buildDtoCancelTransactionPurchase();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("100000")
                .withReferenceNumber("123456")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("04", "No coinciden los 3 datos con el registro");
        expectedResponse.put("referenceNumber", "123456");
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.of(purchase));

        // Act
        Map<String, String> responsePurchase = purchaseService.cancelTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should cancel the purchase")
    void shouldCancelThePurchase() {
        // Arrange
        DtoCancelTransactionPurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withReferenceNumber("123456")
                .withPurchaseValue("150000")
                .buildDtoCancelTransactionPurchase();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(LocalDateTime.now())
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("00", "Compra  anulada");
        expectedResponse.put("referenceNumber", "123456");
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.of(purchase));

        // Act
        Map<String, String> responsePurchase = purchaseService.cancelTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

    @Test
    @DisplayName("This test should not cancel the purchase because five minutes have already passed")
    void shouldNotCancelThePurchaseBecauseFiveMinutesHaveAlreadyPassed() {
        // Arrange
        DtoCancelTransactionPurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withReferenceNumber("123456")
                .withPurchaseValue("150000")
                .buildDtoCancelTransactionPurchase();
        LocalDateTime localDateTime = LocalDateTime.now();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(localDateTime.minusMinutes(10))
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("02", "No se puede anular transaccion");
        expectedResponse.put("referenceNumber", "123456");
        Mockito.when(purchaseCrudRepository.findById(Mockito.anyString())).thenReturn(Optional.of(purchase));

        // Act
        Map<String, String> responsePurchase = purchaseService.cancelTransaction(dtoCreatePurchase);

        // Assert
        assertEquals(responsePurchase, expectedResponse);
    }

}
