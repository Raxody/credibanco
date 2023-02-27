package com.credibanco.assessment.purchase.model;
import com.credibanco.assessment.purchase.model.dto.DtoCancelTransactionPurchase;
import com.credibanco.assessment.purchase.model.dto.DtoCreatePurchase;
import com.credibanco.assessment.purchase.model.entity.Purchase;
import com.credibanco.assessment.purchase.model.testdatabuilder.PurchaseTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTest {

    private ValidatorFactory validatorFactory ;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldFailWhenTryCreateADtoCreatePurchaseAndInThisMissingAttributes() {
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1651651651165")
                .withStatus("creado")
                .buildDtoCreatePurchase();

        // Act
        Set<ConstraintViolation<DtoCreatePurchase>> violations = validator.validate(dtoCreatePurchase);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyADtoCreatePurchase() {
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withPurchaseValue("155000")
                .buildDtoCreatePurchase();

        // Act
        Set<ConstraintViolation<DtoCreatePurchase>> violations = validator.validate(dtoCreatePurchase);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenTryCreateADtoCancelTransactionPurchaseAndInThisMissingAttributes() {
        // Arrange
        DtoCancelTransactionPurchase dtoCancelTransactionPurchase = new PurchaseTestDataBuilder()
                .withPan("1651651651165")
                .withPurchaseValue("555555")
                .buildDtoCancelTransactionPurchase();

        // Act
        Set<ConstraintViolation<DtoCancelTransactionPurchase>> violations = validator.validate(dtoCancelTransactionPurchase);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyADtoCancelTransactionPurchase() {
        // Arrange
        DtoCreatePurchase dtoCreatePurchase = new PurchaseTestDataBuilder()
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withPurchaseValue("155000")
                .buildDtoCreatePurchase();

        // Act
        Set<ConstraintViolation<DtoCreatePurchase>> violations = validator.validate(dtoCreatePurchase);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyACard() {
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        Purchase purchase = new PurchaseTestDataBuilder()
                .withTimePurchase(localDateTime)
                .withPan("1000000000000000")
                .withPurchaseAddress("Calle falsa # 15")
                .withStatus("enrolado")
                .withPurchaseValue("150000")
                .withReferenceNumber("123456")
                .build();

        // Act - Assert
        assertEquals("1000000000000000", purchase.getPan());
        assertEquals(localDateTime, purchase.getTimePurchase());
        assertEquals("Calle falsa # 15", purchase.getPurchaseAddress());
        assertEquals(150000, purchase.getPurchaseValue());
        assertEquals( "enrolado", purchase.getStatus());
        assertEquals( "123456", purchase.getReferenceNumber());
    }


}
