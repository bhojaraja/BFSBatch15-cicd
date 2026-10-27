package com.calculator;

public class CommandHandler {
    private static final String HELP = "help";
    private static final String QUIT = "quit";
    private static final String EXIT = "exit";
    private static final String CLEAR = "clear";

    private boolean running = true;

    public boolean handle(String input) {
        String command = input.trim().toLowerCase();

        switch (command) {
            case HELP:
                displayHelp();
                return true;
            case QUIT:
            case EXIT:
                System.out.println("Goodbye!");
                running = false;
                return true;
            case CLEAR:
                clearScreen();
                return true;
            default:
                return false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void displayHelp() {
        System.out.println("\nUsage:");
        System.out.println("  Enter: <number> <operator> <number>");
        System.out.println("  Example: 5 + 3");
        System.out.println("\nSupported operators:");
        System.out.println("  + (addition)");
        System.out.println("  - (subtraction)");
        System.out.println("  * (multiplication)");
        System.out.println("  / (division)");
        System.out.println("\nCommands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  quit  - Exit the calculator");
        System.out.println("  exit  - Exit the calculator");
        System.out.println("  clear - Clear the screen");
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }
}
