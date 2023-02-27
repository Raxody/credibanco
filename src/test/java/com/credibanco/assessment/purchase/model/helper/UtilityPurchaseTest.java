package com.credibanco.assessment.purchase.model.helper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilityPurchaseTest {

    @Test
    void assignReferenceNumberForPurchase(){
        // Arrange
        String expected = "100001";
        String referenceNumber = "100000";

        // Act
        String response = UtilityPurchase.assignReferenceNumberForPurchase(referenceNumber);

        // Assert
        assertEquals(expected,response);
    }

    @Test
    void transactionCancellationTimeCalculatorSuccess(){
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();

        // Act
        boolean response = UtilityPurchase.transactionCancellationTimeCalculator(localDateTime);

        // Assert
        assertTrue(response);
    }

    @Test
    void transactionCancellationTimeCalculatorNoSuccess(){
        // Arrange
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.minusMinutes(10);
        // Act
        boolean response = UtilityPurchase.transactionCancellationTimeCalculator(localDateTime);

        // Assert
        assertFalse(response);
    }
}
