package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.calculator.exception.InvalidInputException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputParser Tests")
class InputParserTest {
    private InputParser parser;

    @BeforeEach
    void setUp() {
        parser = new InputParser();
    }

    @Test
    @DisplayName("Parse valid addition expression")
    void testParseValidAddition() throws InvalidInputException {
        InputParser.Operation op = parser.parse("5 + 3");
        assertEquals(5.0, op.operand1);
        assertEquals("+", op.operator);
        assertEquals(3.0, op.operand2);
    }

    @Test
    @DisplayName("Parse valid subtraction expression")
    void testParseValidSubtraction() throws InvalidInputException {
        InputParser.Operation op = parser.parse("10 - 4");
        assertEquals(10.0, op.operand1);
        assertEquals("-", op.operator);
        assertEquals(4.0, op.operand2);
    }

    @Test
    @DisplayName("Parse valid multiplication expression")
    void testParseValidMultiplication() throws InvalidInputException {
        InputParser.Operation op = parser.parse("7 * 8");
        assertEquals(7.0, op.operand1);
        assertEquals("*", op.operator);
        assertEquals(8.0, op.operand2);
    }

    @Test
    @DisplayName("Parse valid division expression")
    void testParseValidDivision() throws InvalidInputException {
        InputParser.Operation op = parser.parse("20 / 4");
        assertEquals(20.0, op.operand1);
        assertEquals("/", op.operator);
        assertEquals(4.0, op.operand2);
    }

    @Test
    @DisplayName("Parse expression with negative numbers")
    void testParseNegativeNumbers() throws InvalidInputException {
        InputParser.Operation op = parser.parse("-5 + -3");
        assertEquals(-5.0, op.operand1);
        assertEquals("+", op.operator);
        assertEquals(-3.0, op.operand2);
    }

    @Test
    @DisplayName("Parse expression with decimal numbers")
    void testParseDecimalNumbers() throws InvalidInputException {
        InputParser.Operation op = parser.parse("5.5 + 3.2");
        assertEquals(5.5, op.operand1);
        assertEquals("+", op.operator);
        assertEquals(3.2, op.operand2);
    }

    @Test
    @DisplayName("Parse expression with extra spaces")
    void testParseExtraSpaces() throws InvalidInputException {
        InputParser.Operation op = parser.parse("5   +   3");
        assertEquals(5.0, op.operand1);
        assertEquals("+", op.operator);
        assertEquals(3.0, op.operand2);
    }

    @Test
    @DisplayName("Parse expression with leading/trailing spaces")
    void testParseLeadingTrailingSpaces() throws InvalidInputException {
        InputParser.Operation op = parser.parse("   10 / 2   ");
        assertEquals(10.0, op.operand1);
        assertEquals("/", op.operator);
        assertEquals(2.0, op.operand2);
    }

    @Test
    @DisplayName("Empty input throws exception")
    void testEmptyInput() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("")
        );
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    @DisplayName("Whitespace only input throws exception")
    void testWhitespaceOnlyInput() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("   ")
        );
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    @DisplayName("Invalid format - missing operator")
    void testMissingOperator() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5 3")
        );
        assertTrue(exception.getMessage().contains("Invalid format"));
    }

    @Test
    @DisplayName("Invalid format - only one operand")
    void testOnlyOneOperand() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5")
        );
        assertTrue(exception.getMessage().contains("Invalid format"));
    }

    @Test
    @DisplayName("Invalid format - too many operands")
    void testTooManyOperands() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5 + 3 + 2")
        );
        assertTrue(exception.getMessage().contains("Invalid format"));
    }

    @Test
    @DisplayName("Invalid first operand")
    void testInvalidFirstOperand() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("abc + 3")
        );
        assertTrue(exception.getMessage().contains("Invalid first number"));
    }

    @Test
    @DisplayName("Invalid second operand")
    void testInvalidSecondOperand() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5 + xyz")
        );
        assertTrue(exception.getMessage().contains("Invalid second number"));
    }

    @Test
    @DisplayName("Invalid operator")
    void testInvalidOperator() {
        InvalidInputException exception = assertThrows(
            InvalidInputException.class,
            () -> parser.parse("5 % 3")
        );
        assertTrue(exception.getMessage().contains("Invalid operator"));
    }

    @Test
    @DisplayName("Parse zero operands")
    void testParseZeroOperands() throws InvalidInputException {
        InputParser.Operation op = parser.parse("0 + 0");
        assertEquals(0.0, op.operand1);
        assertEquals(0.0, op.operand2);
    }

    @Test
    @DisplayName("Parse very large numbers")
    void testParseVeryLargeNumbers() throws InvalidInputException {
        InputParser.Operation op = parser.parse("999999999 * 999999999");
        assertEquals(999999999.0, op.operand1);
        assertEquals(999999999.0, op.operand2);
    }

    @Test
    @DisplayName("Parse very small decimal numbers")
    void testParseVerySmallDecimals() throws InvalidInputException {
        InputParser.Operation op = parser.parse("0.0001 + 0.0002");
        assertEquals(0.0001, op.operand1, 0.00001);
        assertEquals(0.0002, op.operand2, 0.00001);
    }
}
