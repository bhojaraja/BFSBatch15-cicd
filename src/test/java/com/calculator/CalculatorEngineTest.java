package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.calculator.exception.CalculatorException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculatorEngine Tests")
class CalculatorEngineTest {
    private CalculatorEngine engine;

    @BeforeEach
    void setUp() {
        engine = new CalculatorEngine();
    }

    @Test
    @DisplayName("Test addition of positive numbers")
    void testAddition() {
        assertEquals(8.0, engine.add(5, 3));
    }

    @Test
    @DisplayName("Test addition with negative numbers")
    void testAdditionWithNegatives() {
        assertEquals(-2.0, engine.add(5, -7));
    }

    @Test
    @DisplayName("Test addition with zeros")
    void testAdditionWithZeros() {
        assertEquals(5.0, engine.add(5, 0));
        assertEquals(0.0, engine.add(0, 0));
    }

    @Test
    @DisplayName("Test subtraction of positive numbers")
    void testSubtraction() {
        assertEquals(2.0, engine.subtract(5, 3));
    }

    @Test
    @DisplayName("Test subtraction resulting in negative")
    void testSubtractionNegativeResult() {
        assertEquals(-2.0, engine.subtract(3, 5));
    }

    @Test
    @DisplayName("Test subtraction with zeros")
    void testSubtractionWithZeros() {
        assertEquals(5.0, engine.subtract(5, 0));
        assertEquals(0.0, engine.subtract(0, 0));
    }

    @Test
    @DisplayName("Test multiplication of positive numbers")
    void testMultiplication() {
        assertEquals(15.0, engine.multiply(5, 3));
    }

    @Test
    @DisplayName("Test multiplication with negative numbers")
    void testMultiplicationWithNegatives() {
        assertEquals(-15.0, engine.multiply(5, -3));
        assertEquals(15.0, engine.multiply(-5, -3));
    }

    @Test
    @DisplayName("Test multiplication with zero")
    void testMultiplicationWithZero() {
        assertEquals(0.0, engine.multiply(5, 0));
    }

    @Test
    @DisplayName("Test division of positive numbers")
    void testDivision() throws CalculatorException {
        assertEquals(5.0, engine.divide(10, 2));
    }

    @Test
    @DisplayName("Test division resulting in decimal")
    void testDivisionDecimal() throws CalculatorException {
        assertEquals(2.5, engine.divide(5, 2));
    }

    @Test
    @DisplayName("Test division by zero throws exception")
    void testDivisionByZero() {
        CalculatorException exception = assertThrows(
            CalculatorException.class,
            () -> engine.divide(10, 0)
        );
        assertTrue(exception.getMessage().contains("Division by zero"));
    }

    @Test
    @DisplayName("Test division with negative numbers")
    void testDivisionWithNegatives() throws CalculatorException {
        assertEquals(-5.0, engine.divide(10, -2));
        assertEquals(5.0, engine.divide(-10, -2));
    }

    @Test
    @DisplayName("Test calculate with addition operator")
    void testCalculateAddition() throws CalculatorException {
        assertEquals(12.0, engine.calculate(7, "+", 5));
    }

    @Test
    @DisplayName("Test calculate with subtraction operator")
    void testCalculateSubtraction() throws CalculatorException {
        assertEquals(2.0, engine.calculate(7, "-", 5));
    }

    @Test
    @DisplayName("Test calculate with multiplication operator")
    void testCalculateMultiplication() throws CalculatorException {
        assertEquals(35.0, engine.calculate(7, "*", 5));
    }

    @Test
    @DisplayName("Test calculate with division operator")
    void testCalculateDivision() throws CalculatorException {
        assertEquals(2.0, engine.calculate(10, "/", 5));
    }

    @Test
    @DisplayName("Test calculate with invalid operator")
    void testCalculateInvalidOperator() {
        CalculatorException exception = assertThrows(
            CalculatorException.class,
            () -> engine.calculate(5, "%", 3)
        );
        assertTrue(exception.getMessage().contains("Unknown operator"));
    }

    @Test
    @DisplayName("Test calculate with division by zero")
    void testCalculateDivisionByZero() {
        assertThrows(
            CalculatorException.class,
            () -> engine.calculate(10, "/", 0)
        );
    }

    @Test
    @DisplayName("Test calculate with large numbers")
    void testCalculateLargeNumbers() throws CalculatorException {
        assertEquals(1000000.0, engine.calculate(500000, "+", 500000));
        assertEquals(250000000000.0, engine.calculate(500000, "*", 500000));
    }

    @Test
    @DisplayName("Test calculate with decimal numbers")
    void testCalculateDecimalNumbers() throws CalculatorException {
        assertEquals(5.5, engine.calculate(2.5, "+", 3.0));
        assertEquals(0.5, engine.calculate(1.5, "-", 1.0));
        assertEquals(6.25, engine.calculate(2.5, "*", 2.5));
    }
}
