package com.calculator;

import com.calculator.exception.InvalidInputException;

public class InputParser {

    public static class Operation {
        public double operand1;
        public String operator;
        public double operand2;

        public Operation(double operand1, String operator, double operand2) {
            this.operand1 = operand1;
            this.operator = operator;
            this.operand2 = operand2;
        }
    }

    public Operation parse(String input) throws InvalidInputException {
        input = input.trim();

        if (input.isEmpty()) {
            throw new InvalidInputException("Input cannot be empty");
        }

        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            throw new InvalidInputException(
                "Invalid format. Expected: <number> <operator> <number>\n" +
                "Example: 5 + 3"
            );
        }

        String operand1Str = parts[0];
        String operatorStr = parts[1];
        String operand2Str = parts[2];

        double operand1;
        double operand2;

        try {
            operand1 = Double.parseDouble(operand1Str);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid first number: " + operand1Str);
        }

        try {
            operand2 = Double.parseDouble(operand2Str);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid second number: " + operand2Str);
        }

        if (!isValidOperator(operatorStr)) {
            throw new InvalidInputException(
                "Invalid operator: " + operatorStr + "\n" +
                "Supported operators: + - * /"
            );
        }

        return new Operation(operand1, operatorStr, operand2);
    }

    private boolean isValidOperator(String operator) {
        return operator.equals("+") || operator.equals("-") ||
               operator.equals("*") || operator.equals("/");
    }
}
