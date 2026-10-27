package com.calculator.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculatorException Tests")
class CalculatorExceptionTest {

    @Test
    @DisplayName("Create exception with message")
    void testCreateExceptionWithMessage() {
        String message = "Test error message";
        CalculatorException exception = new CalculatorException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Exception is instance of Exception")
    void testExceptionIsInstanceOfException() {
        CalculatorException exception = new CalculatorException("Test");
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("Exception message is not empty")
    void testExceptionMessageNotEmpty() {
        CalculatorException exception = new CalculatorException("Error occurred");
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }
}
