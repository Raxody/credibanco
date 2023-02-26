package com.credibanco.assessment.card.model.entitydto;

import com.credibanco.assessment.card.model.dto.DtoCreateCard;
import com.credibanco.assessment.card.model.dto.DtoEnrollCard;
import com.credibanco.assessment.card.model.entity.Card;
import com.credibanco.assessment.card.model.testdatabuilder.CardTestDataBuilder;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void shouldFailWhenTryCreateADtoCreateCardAndInThisMissingAttributes() {
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("1000000000000000")
                .withId("1000495217")
                .buildDtoCreateCard();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // Act
        Set<ConstraintViolation<DtoCreateCard>> violations = validator.validate(dtoCreateCard);

        // Assert
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }

    @Test
    void shouldCreateCorrectlyADtoCreateCard() {
        // Arrange
        DtoCreateCard dtoCreateCard = new CardTestDataBuilder()
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withTypeCard("credito")
                .buildDtoCreateCard();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

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
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // Act
        Set<ConstraintViolation<DtoEnrollCard>> violations = validator.validate(dtoEnrollCard);

        // Assert
        assertFalse(violations.isEmpty());
        System.out.println(violations);
    }

    @Test
    void shouldCreateCorrectlyADtoEnrollCard() {
        // Arrange
        DtoEnrollCard dtoEnrollCard = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withEnrollmentNumber("55")
                .buildDtoEnrollCard();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        // Act
        Set<ConstraintViolation<DtoEnrollCard>> violations = validator.validate(dtoEnrollCard);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateCorrectlyACard() {
        // Arrange
        // String pan, String owner, String id, String numberPhone, String status, Integer enrollmentNumber
        Card card = new CardTestDataBuilder()
                .withPan("1000000000000000")
                .withOwner("Usuario prueba")
                .withId("123456780008")
                .withNumberPhone("9876543210")
                .withStatus("creada")
                .withEnrollmentNumber("46")
                .build();
        // Act - Assert
        assertEquals(card.getPan(), "1000000000000000");
        assertEquals(card.getOwner(), "Usuario prueba");
        assertEquals(card.getId(), "123456780008");
        assertEquals(card.getNumberPhone(), "9876543210");
        assertEquals(card.getStatus(), "creada");
        assertEquals(card.getEnrollmentNumber(), 46);
    }

}
