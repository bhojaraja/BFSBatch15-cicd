# Calculator Test Guide

This document describes the JUnit test suite for the Calculator application.

## Test Structure

The test suite is organized into the following test classes:

### 1. **CalculatorEngineTest** (`src/test/java/com/calculator/CalculatorEngineTest.java`)
Tests all arithmetic operations in the CalculatorEngine class.

**Test Coverage:**
- ✅ Addition (positive, negative, zero)
- ✅ Subtraction (positive, negative, zero)
- ✅ Multiplication (positive, negative, zero)
- ✅ Division (positive, negative, decimal)
- ✅ Division by zero error handling
- ✅ Large numbers
- ✅ Decimal numbers
- ✅ All operators via `calculate()` method
- ✅ Invalid operator error

**Total Tests: 21**

### 2. **InputParserTest** (`src/test/java/com/calculator/InputParserTest.java`)
Tests input parsing and validation.

**Test Coverage:**
- ✅ Valid expressions for all operators (+, -, *, /)
- ✅ Negative numbers
- ✅ Decimal numbers
- ✅ Extra and leading/trailing spaces
- ✅ Empty input validation
- ✅ Invalid format detection
- ✅ Invalid operand detection
- ✅ Invalid operator detection
- ✅ Zero operands
- ✅ Very large and very small numbers

**Total Tests: 18**

### 3. **CalculatorControllerTest** (`src/test/java/com/calculator/api/CalculatorControllerTest.java`)
Tests the REST API endpoint handling.

**Test Coverage:**
- ✅ Valid POST requests for all operations
- ✅ CORS OPTIONS request handling
- ✅ Unsupported HTTP methods
- ✅ Division by zero error response
- ✅ Invalid JSON format handling
- ✅ Missing required fields
- ✅ Response structure validation

**Total Tests: 9**

### 4. **IntegrationTest** (`src/test/java/com/calculator/IntegrationTest.java`)
End-to-end integration tests combining parser and engine.

**Test Coverage:**
- ✅ Complete flow: Parse → Calculate
- ✅ All operations in integration
- ✅ Decimal operations
- ✅ Negative operands
- ✅ Error propagation
- ✅ Invalid format handling
- ✅ Sequential calculations
- ✅ Complex decimal calculations

**Total Tests: 10**

### 5. **Exception Tests**
- `CalculatorExceptionTest.java` - Exception creation and hierarchy (3 tests)
- `InvalidInputExceptionTest.java` - InvalidInputException handling (3 tests)

**Total Tests: 6**

## Running Tests

### Quick Start (Using provided script):
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

### Manual Test Execution:

**1. Compile main code:**
```bash
javac -d bin src/main/java/com/calculator/*.java \
    src/main/java/com/calculator/exception/*.java \
    src/main/java/com/calculator/api/*.java
```

**2. Download JUnit dependencies:**
```bash
mkdir -p lib
# Use the build-and-test.sh script which handles dependency downloading
```

**3. Compile tests:**
```bash
javac -cp bin:lib/* -d bin/test \
    src/test/java/com/calculator/*.java \
    src/test/java/com/calculator/api/*.java \
    src/test/java/com/calculator/exception/*.java
```

**4. Run tests:**
```bash
java -cp bin:lib/*:bin/test org.junit.platform.console.ConsoleLauncher \
    --scan-classpath bin/test
```

## Test Statistics

| Category | Count |
|----------|-------|
| Engine Tests | 21 |
| Parser Tests | 18 |
| Controller Tests | 9 |
| Integration Tests | 10 |
| Exception Tests | 6 |
| **Total** | **64** |

## Test Features

### Comprehensive Coverage
- All arithmetic operations tested
- Error conditions properly validated
- Edge cases (zero, negatives, decimals, large numbers)
- HTTP/API behavior tested

### Best Practices
- Clear, descriptive test names with `@DisplayName`
- Each test focuses on a single concern
- Proper exception assertions using JUnit 5
- Mock objects for isolated controller testing
- Integration tests verify end-to-end flows

### Naming Convention
Tests follow the pattern: `test<Operation><Condition>`
Example: `testAdditionWithNegatives`, `testDivisionByZero`

## Expected Test Results

When all tests pass, you should see output similar to:
```
✓ 64 tests passed
✓ 0 tests skipped
✓ 0 tests aborted
```

## Debugging Failed Tests

If a test fails:

1. **Check the error message** - JUnit provides detailed assertion messages
2. **Run a single test class:**
   ```bash
   java -cp bin:lib/*:bin/test org.junit.platform.console.ConsoleLauncher \
       --scan-class bin/test/com/calculator/CalculatorEngineTest.class
   ```
3. **Enable verbose output** for more details
4. **Review test expectations** against actual implementation

## Adding New Tests

To add new tests:

1. Create a test method in the appropriate test class
2. Use `@Test` annotation
3. Use `@DisplayName("description")` for clarity
4. Follow naming convention: `test<Feature><Scenario>`
5. Use appropriate assertions:
   - `assertEquals()` for value comparisons
   - `assertThrows()` for exception testing
   - `assertTrue()/assertFalse()` for boolean checks
   - `assertNotNull()/assertNull()` for null checks

Example:
```java
@Test
@DisplayName("Test addition with large numbers")
void testAdditionLargeNumbers() throws CalculatorException {
    CalculatorEngine engine = new CalculatorEngine();
    assertEquals(2000000.0, engine.calculate(1000000, "+", 1000000));
}
```

## Dependencies

- **JUnit 5 (Jupiter)** - Test framework
- **Mockito** - For mocking in controller tests
- **Byte Buddy** - Dependency for Mockito

All dependencies are automatically downloaded by `build-and-test.sh`

## CI/CD Integration

These tests are designed to run in CI/CD pipelines. The script exits with:
- `0` if all tests pass
- `1` if any test fails or compilation errors occur

Example for GitHub Actions or other CI systems:
```yaml
- name: Run Tests
  run: ./build-and-test.sh
```

---

**Last Updated:** October 2026
**Test Framework:** JUnit 5 (Jupiter)
**Java Version:** Java 8+
