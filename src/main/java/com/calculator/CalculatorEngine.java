package com.calculator;

import com.calculator.exception.CalculatorException;

public class CalculatorEngine {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws CalculatorException {
        if (b == 0) {
            throw new CalculatorException("Error: Division by zero is not allowed");
        }
        return a / b;
    }

    public double calculate(double operand1, String operator, double operand2) throws CalculatorException {
        switch (operator) {
            case "+":
                return add(operand1, operand2);
            case "-":
                return subtract(operand1, operand2);
            case "*":
                return multiply(operand1, operand2);
            case "/":
                return divide(operand1, operand2);
            default:
                throw new CalculatorException("Unknown operator: " + operator);
        }
    }
}
