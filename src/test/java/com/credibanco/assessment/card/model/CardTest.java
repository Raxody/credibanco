package com.credibanco.assessment.card.model;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.testdatabuilder.CardTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private ValidatorFactory validatorFactory ;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldFailWhenTryCreateADtoCreateCardAndInThisMissingAttributes() {
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("1000000000000000")
                .withId("1000495217")
                .buildDtoCreateCard();

        // Act
        Set<ConstraintViolation<DtoCreateCard>> violations = validator.validate(dtoCreateCard);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyADtoCreateCardOfCreditCard() {
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withTypeCard("credito")
                .buildDtoCreateCard();

        // Act
        Set<ConstraintViolation<DtoCreateCard>> violations = validator.validate(dtoCreateCard);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyADtoCreateCardOfDebitCard() {
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withTypeCard("debito")
                .buildDtoCreateCard();

        // Act
        Set<ConstraintViolation<DtoCreateCard>> violations = validator.validate(dtoCreateCard);

        // Assert
        assertTrue(violations.isEmpty());
    }


    @Test
    void shouldFailWhenTryCreateADtoEnrollCardAndInThisMissingAttributes() {
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000f")
                .buildDtoEnrollCard();

        // Act
        Set<ConstraintViolation<DtoEnrollCard>> violations = validator.validate(dtoEnrollCard);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyADtoEnrollCard() {
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("55")
                .buildDtoEnrollCard();

        // Act
        Set<ConstraintViolation<DtoEnrollCard>> violations = validator.validate(dtoEnrollCard);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyACard() {
        // Arrange
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withStatus("creada")
                .withEnrollmentNumber("46")
                .build();
        // Act - Assert
        assertEquals("1000000000000000", card.getPan());
        assertEquals("Usuario prueba", card.getOwner());
        assertEquals("123456780008", card.getId());
        assertEquals("9876543210", card.getNumberPhone());
        assertEquals( "creada", card.getStatus());
        assertEquals(46, card.getEnrollmentNumber());
    }

}
