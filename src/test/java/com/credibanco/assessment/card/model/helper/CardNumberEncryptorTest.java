package com.credibanco.assessment.card.model.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberEncryptorTest {

    @Test
    void shouldEncryptedThePan(){
        // Arrange
        String pan = "1000000000000000";
        String expectedPan = "100000******0000";

        // Act
        String responseEncryptedPan = CardNumberEncryptor.panEncryptFirstSixAndLastFourCardNumbers(pan);

        // Assert
        assertEquals(expectedPan,responseEncryptedPan);
    }
}
