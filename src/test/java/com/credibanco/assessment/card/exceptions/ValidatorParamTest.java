package com.credibanco.assessment.card.exceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidatorParamTest {

    private BindingResult bindingResult;
    private ResponseEntity<Map<String, String>> response;

    @BeforeEach
    public void setUp() {
        bindingResult = mock(BindingResult.class);
        response = null;
    }

    @Test
    public void testGetErrors() {

        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError error1 = new FieldError("myObject", "campo1", "Error 1");
        FieldError error2 = new FieldError("myObject", "campo2", "Error 2");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(error1, error2));


        // Act
        response = ValidatorParam.getErrors(bindingResult, "100");


        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("100"));
        assertEquals(" Fallido", response.getBody().get("100"));
        assertTrue(response.getBody().containsKey("campo1"));
        assertEquals("Error 1", response.getBody().get("campo1"));
        assertTrue(response.getBody().containsKey("campo2"));
        assertEquals("Error 2", response.getBody().get("campo2"));
    }

}