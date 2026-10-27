# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a simple Java command-line calculator application that performs basic arithmetic operations (addition, subtraction, multiplication, division). The codebase demonstrates clean separation of concerns with input parsing, calculation logic, and exception handling.

## Build and Run

**Compile:**
```bash
javac -d bin src/main/java/com/calculator/*.java src/main/java/com/calculator/exception/*.java
```

**Run:**
```bash
java -cp bin com.calculator.Calculator
```

**Clean:**
```bash
rm -rf bin/com
```

## Code Architecture

The project follows a modular design with clear responsibilities:

- **Calculator.java** - Main entry point and REPL (read-eval-print loop) that handles user interaction, command parsing (help, quit, clear), and exception handling
- **CalculatorEngine.java** - Core calculation logic with individual methods for each operation (+, -, *, /) and a dispatcher method
- **InputParser.java** - Parses user input into a structured Operation object, validates format and operators
- **Exception hierarchy** - `CalculatorException` (base) and `InvalidInputException` (for parsing errors)

**Data Flow:**
1. Calculator prompts user for input
2. InputParser parses the input string into an Operation object (handles validation)
3. CalculatorEngine performs the calculation
4. Result is displayed or exception is caught and shown to user

## Key Design Decisions

- **Exceptions over error codes** - Exceptions provide clear error handling with specific message information
- **Separate concerns** - InputParser handles format validation, CalculatorEngine handles calculation logic
- **Static Operation class** - Used as a simple data container for parsed expressions
- **Division by zero check** - Explicitly validated in the divide method to provide a user-friendly error
