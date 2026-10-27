# Calculator Application - Testing Guide

## Quick Start

Run all tests with one command:
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

## Test Structure

### 📊 Test Suite Overview

```
src/test/java/com/calculator/
├── CalculatorEngineTest.java        (21 tests) - Arithmetic operations
├── InputParserTest.java             (18 tests) - Input validation & parsing
├── IntegrationTest.java             (10 tests) - End-to-end flows
├── api/
│   └── CalculatorControllerTest.java (9 tests) - REST API endpoints
└── exception/
    ├── CalculatorExceptionTest.java  (3 tests) - Exception hierarchy
    └── InvalidInputExceptionTest.java (3 tests) - Input exception tests
```

**Total: 64 JUnit Tests**

---

## Test Categories

### 1️⃣ CalculatorEngineTest (21 tests)
**What it tests:** Core calculation logic

```java
// Examples of test coverage:
✓ add(5, 3) → 8.0
✓ subtract(10, 3) → 7.0
✓ multiply(7, 8) → 56.0
✓ divide(10, 2) → 5.0
✓ divide(10, 0) → Exception ✓
✓ calculate(5, "+", 3) → 8.0
```

**All operations tested with:**
- Positive numbers
- Negative numbers
- Zero values
- Decimal numbers
- Large numbers

### 2️⃣ InputParserTest (18 tests)
**What it tests:** Input parsing and validation

```java
// Examples:
✓ parse("5 + 3") → Operation(5, "+", 3)
✓ parse("10.5 - 2.5") → Operation(10.5, "-", 2.5)
✓ parse("") → InvalidInputException
✓ parse("abc + 3") → InvalidInputException
✓ parse("5 % 3") → InvalidInputException
```

**Coverage:**
- Valid expressions for all operators
- Decimal and negative numbers
- Whitespace handling
- Error detection

### 3️⃣ CalculatorControllerTest (9 tests)
**What it tests:** REST API endpoint behavior

```java
// Examples:
✓ POST /calculate with valid JSON → 200 OK
✓ POST /calculate with division by zero → 400 Error
✓ OPTIONS /calculate → CORS headers
✓ GET /calculate → 405 Method Not Allowed
```

**Coverage:**
- HTTP method handling
- JSON parsing
- Error responses
- CORS support

### 4️⃣ IntegrationTest (10 tests)
**What it tests:** End-to-end calculator flows

```java
// Examples:
✓ Parse("25 + 17") → Calculate → 42
✓ Parse("-10 * 5") → Calculate → -50
✓ Parse("abc") → InvalidInputException (caught)
✓ Sequential: (5+3) * 2 - 4 = 12
```

### 5️⃣ Exception Tests (6 tests)
**What it tests:** Exception handling

```java
✓ CalculatorException creation and messages
✓ InvalidInputException extends CalculatorException
✓ Exception hierarchy validation
```

---

## Test Execution

### Full Test Suite
```bash
./build-and-test.sh
```

### Manual Execution Steps

**Step 1: Compile source**
```bash
javac -d bin src/main/java/com/calculator/*.java \
    src/main/java/com/calculator/exception/*.java \
    src/main/java/com/calculator/api/*.java
```

**Step 2: Compile tests**
```bash
javac -cp bin:lib/* -d bin/test \
    src/test/java/com/calculator/*.java \
    src/test/java/com/calculator/api/*.java \
    src/test/java/com/calculator/exception/*.java
```

**Step 3: Run tests**
```bash
java -cp bin:lib/*:bin/test org.junit.platform.console.ConsoleLauncher \
    --scan-classpath bin/test
```

---

## Test Coverage Matrix

| Operation | Positive | Negative | Zero | Decimal | Large | Error |
|-----------|----------|----------|------|---------|-------|-------|
| Addition | ✓ | ✓ | ✓ | ✓ | ✓ | - |
| Subtraction | ✓ | ✓ | ✓ | ✓ | ✓ | - |
| Multiplication | ✓ | ✓ | ✓ | ✓ | ✓ | - |
| Division | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ (÷0) |
| Parsing | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| API | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |

---

## Test Results

### Success Output
```
Test suite: JUnit Platform Suite
Tests started: 64
Tests successful: 64
Tests aborted: 0
Tests failed: 0

✓ All tests passed!
```

### Failed Test Example
```
CalculatorEngineTest > testDivision() FAILED
Expected: 5.0
Actual: 4.999999999
```

---

## Common Test Assertions

```java
// Value comparisons
assertEquals(expected, actual)
assertEquals(expected, actual, delta)  // For floats

// Exception testing
assertThrows(ExceptionType.class, () -> { code })
assertDoesNotThrow(() -> { code })

// Boolean checks
assertTrue(condition)
assertFalse(condition)

// Null checks
assertNull(value)
assertNotNull(value)
```

---

## Adding New Tests

### Template
```java
@Test
@DisplayName("Test description")
void testNameFollowsConvention() throws Exception {
    // Arrange
    CalculatorEngine engine = new CalculatorEngine();
    
    // Act
    double result = engine.add(5, 3);
    
    // Assert
    assertEquals(8.0, result);
}
```

### Naming Convention
- `test<Feature><Scenario>`
- Examples: 
  - `testAdditionWithNegatives`
  - `testDivisionByZero`
  - `testParseInvalidInput`

---

## Dependencies

All dependencies are handled automatically by `build-and-test.sh`:

| Dependency | Version | Purpose |
|------------|---------|---------|
| JUnit Jupiter API | 5.9.2 | Test framework |
| JUnit Jupiter Engine | 5.9.2 | Test runner |
| JUnit Platform Console | 1.9.2 | CLI test runner |
| Mockito Core | 5.2.0 | Mocking framework |
| Mockito JUnit Jupiter | 5.2.0 | JUnit integration |
| ByteBuddy | 1.14.1 | Mockito dependency |

---

## CI/CD Integration

### GitHub Actions Example
```yaml
name: Run Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run tests
        run: |
          chmod +x build-and-test.sh
          ./build-and-test.sh
```

---

## Troubleshooting

### Issue: "cannot find symbol" errors
**Solution:** Ensure all source files are compiled before tests
```bash
rm -rf bin/*
./build-and-test.sh
```

### Issue: Test times out
**Solution:** Check for infinite loops or blocking I/O in test code

### Issue: Class not found in tests
**Solution:** Verify classpath includes both `bin` and `bin/test`:
```bash
java -cp bin:lib/*:bin/test org.junit.platform.console.ConsoleLauncher --scan-classpath bin/test
```

---

## Test Metrics

- **Total Tests:** 64
- **Lines of Test Code:** ~900
- **Code Coverage Target:** >80% of calculator logic
- **Average Test Runtime:** <100ms
- **Critical Path Tests:** 
  - `testDivisionByZero`
  - `testParseInvalidInput`
  - `testCalculateWithInvalidOperator`

---

## Best Practices

✅ **DO:**
- Write descriptive test names with `@DisplayName`
- Test one thing per test method
- Use meaningful assertion messages
- Clean up resources after tests
- Test error conditions

❌ **DON'T:**
- Test multiple concerns in one test
- Use generic assertion messages
- Leave hardcoded magic numbers
- Depend on test execution order
- Ignore exception tests

---

## References

- **TEST_GUIDE.md** - Detailed test documentation
- **src/test/** - All test source files
- **build-and-test.sh** - Automated test build script
- **JUnit 5 Docs:** https://junit.org/junit5/

---

**Last Updated:** October 2026  
**Total Test Count:** 64  
**Framework:** JUnit 5 (Jupiter)  
**Status:** ✅ Ready for Production
