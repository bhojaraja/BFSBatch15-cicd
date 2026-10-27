package com.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.calculator.exception.CalculatorException;
import com.calculator.exception.InvalidInputException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration Tests")
class IntegrationTest {

    @Test
    @DisplayName("End-to-end: Parse and calculate addition")
    void testParseAndCalculateAddition() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("25 + 17");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(42.0, result);
    }

    @Test
    @DisplayName("End-to-end: Parse and calculate multiplication")
    void testParseAndCalculateMultiplication() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("6 * 7");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(42.0, result);
    }

    @Test
    @DisplayName("End-to-end: Parse and calculate division")
    void testParseAndCalculateDivision() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("100 / 4");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(25.0, result);
    }

    @Test
    @DisplayName("End-to-end: Parse and calculate subtraction")
    void testParseAndCalculateSubtraction() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("100 - 58");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(42.0, result);
    }

    @Test
    @DisplayName("End-to-end: Decimal operations")
    void testDecimalOperations() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("7.5 * 4.2");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(31.5, result, 0.01);
    }

    @Test
    @DisplayName("End-to-end: Negative operands")
    void testNegativeOperands() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("-10 * 5");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(-50.0, result);
    }

    @Test
    @DisplayName("End-to-end: Invalid input caught in parser")
    void testInvalidInputCaught() {
        InputParser parser = new InputParser();

        assertThrows(
            InvalidInputException.class,
            () -> parser.parse("abc + def")
        );
    }

    @Test
    @DisplayName("End-to-end: Invalid format caught in parser")
    void testInvalidFormatCaught() {
        InputParser parser = new InputParser();

        assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5 + 3 + 2")
        );
    }

    @Test
    @DisplayName("End-to-end: Division by zero handled")
    void testDivisionByZeroHandled() {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        assertThrows(
            CalculatorException.class,
            () -> {
                InputParser.Operation op = parser.parse("10 / 0");
                engine.calculate(op.operand1, op.operator, op.operand2);
            }
        );
    }

    @Test
    @DisplayName("End-to-end: Complex calculation with decimals")
    void testComplexDecimalCalculation() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        InputParser.Operation op = parser.parse("123.456 - 23.456");
        double result = engine.calculate(op.operand1, op.operator, op.operand2);

        assertEquals(100.0, result, 0.01);
    }

    @Test
    @DisplayName("End-to-end: Sequential calculations")
    void testSequentialCalculations() throws InvalidInputException, CalculatorException {
        InputParser parser = new InputParser();
        CalculatorEngine engine = new CalculatorEngine();

        // First calculation: 5 + 3 = 8
        InputParser.Operation op1 = parser.parse("5 + 3");
        double result1 = engine.calculate(op1.operand1, op1.operator, op1.operand2);
        assertEquals(8.0, result1);

        // Second calculation: 8 * 2 = 16
        InputParser.Operation op2 = parser.parse(result1 + " * 2");
        double result2 = engine.calculate(op2.operand1, op2.operator, op2.operand2);
        assertEquals(16.0, result2);

        // Third calculation: 16 - 4 = 12
        InputParser.Operation op3 = parser.parse(result2 + " - 4");
        double result3 = engine.calculate(op3.operand1, op3.operator, op3.operand2);
        assertEquals(12.0, result3);
    }
}
