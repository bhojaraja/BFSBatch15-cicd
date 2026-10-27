package com.calculator.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvalidInputException Tests")
class InvalidInputExceptionTest {

    @Test
    @DisplayName("Create InvalidInputException with message")
    void testCreateInvalidInputException() {
        String message = "Invalid input format";
        InvalidInputException exception = new InvalidInputException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("InvalidInputException extends CalculatorException")
    void testInvalidInputExceptionExtendsCalculatorException() {
        InvalidInputException exception = new InvalidInputException("Test");
        assertTrue(exception instanceof CalculatorException);
    }

    @Test
    @DisplayName("Exception message is preserved")
    void testExceptionMessagePreserved() {
        String message = "Expected format: number operator number";
        InvalidInputException exception = new InvalidInputException(message);
        assertTrue(exception.getMessage().contains("Expected"));
    }
}
