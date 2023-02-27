package com.credibanco.assessment.card.model.helper;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityCardTest {

    @Test
    void testRandomNumberFromOneToOneHundredToEnrollCard() {
        // Arrange - Act
        int randomNumber = UtilityCard.randomNumberFromOneToOneHundredToEnrollCard();
        // Assert
        assertTrue(randomNumber >= 1 && randomNumber <= 100,
                "Random number should be between 1 and 100, but was: " + randomNumber);
    }

    @Test
    void assignCardNumberForPan(){
        // Arrange
        String expected = "1000000000000001";
        String pan = "1000000000000000";

        // Act
        String response = UtilityCard.assignCardNumberForPan(pan);

        // Assert
        assertEquals(expected,response);
    }

}
