package com.calculator;

import java.util.Scanner;
import com.calculator.exception.CalculatorException;
import com.calculator.api.CalculatorServer;
import java.io.IOException;

public class Calculator {
    private final CalculatorEngine engine;
    private final InputParser parser;
    private final CommandHandler commandHandler;

    public Calculator() {
        this.engine = new CalculatorEngine();
        this.parser = new InputParser();
        this.commandHandler = new CommandHandler();
    }

    public void run() {
        displayWelcome();
        try (Scanner scanner = new Scanner(System.in)) {
            while (commandHandler.isRunning()) {
                System.out.print("\nEnter calculation (or 'help' for commands): ");
                String input = scanner.nextLine();

                if (commandHandler.handle(input)) {
                    continue;
                }

                processCalculation(input);
            }
        } catch (CalculatorException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void processCalculation(String input) throws CalculatorException {
        try {
            InputParser.Operation operation = parser.parse(input);
            double result = engine.calculate(
                operation.operand1,
                operation.operator,
                operation.operand2
            );

            System.out.printf("Result: %s %s %s = %s%n",
                operation.operand1,
                operation.operator,
                operation.operand2,
                result);

        } catch (CalculatorException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void displayWelcome() {
        System.out.println("================================");
        System.out.println("  Welcome to Java Calculator");
        System.out.println("================================");
    }

    private static void runWebServer() {
        try {
            CalculatorServer server = new CalculatorServer();
            server.start();
            System.out.println("\nPress Ctrl+C to stop the server");
            Thread.currentThread().join();
        } catch (IOException e) {
            System.err.println("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Server stopped");
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--cli")) {
            Calculator calculator = new Calculator();
            calculator.run();
        } else {
            runWebServer();
        }
    }
}
