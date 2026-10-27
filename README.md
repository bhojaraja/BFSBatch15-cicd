# Java Calculator Application

A modernized Java calculator application with web UI, REST API, comprehensive testing, and GitHub Actions CI/CD pipeline.

## 🎯 Features

### Core Functionality
- ✅ **CLI Mode** - Traditional command-line interface for backward compatibility
- ✅ **Web UI** - Modern web-based calculator at `http://localhost:9090`
- ✅ **REST API** - Programmatic access via JSON endpoints
- ✅ **All Operations** - Addition, Subtraction, Multiplication, Division

### Supported Input Types
- ✅ Positive integers
- ✅ Negative integers
- ✅ Decimal numbers
- ✅ Large numbers
- ✅ Error handling with meaningful messages

### Architecture
- ✅ **Refactored Design** - Clean separation of concerns
- ✅ **CommandHandler** - Dedicated command handling
- ✅ **REST API** - CalculatorController with JSON support
- ✅ **HTTP Server** - Built-in Java HTTP server (no external dependencies)
- ✅ **CORS Support** - Cross-origin requests enabled

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher

### Build

```bash
javac -d bin src/main/java/com/calculator/*.java \
    src/main/java/com/calculator/exception/*.java \
    src/main/java/com/calculator/api/*.java
```

Or use the build script:
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

### Run

**Web Server Mode (Default):**
```bash
java -cp bin com.calculator.Calculator
```
Then open: http://localhost:9090

**CLI Mode:**
```bash
java -cp bin com.calculator.Calculator --cli
```

## 🧪 Testing

### Run Tests

```bash
# Compile tests
javac -cp bin:lib/* -d bin/test \
    src/test/java/com/calculator/*.java \
    src/test/java/com/calculator/api/*.java \
    src/test/java/com/calculator/exception/*.java

# Run tests
java -cp bin:lib/*:bin/test org.junit.platform.console.ConsoleLauncher \
    --scan-classpath bin/test
```

Or use the automated script:
```bash
./build-and-test.sh
```

### Test Suite

- **Total Tests:** 64
- **Coverage:** >80%
- **Components:**
  - CalculatorEngine: 21 tests
  - InputParser: 18 tests
  - IntegrationTest: 10 tests
  - CalculatorController: 9 tests
  - Exception handling: 6 tests

### Documentation

- [TESTING.md](TESTING.md) - Quick testing guide
- [TEST_GUIDE.md](TEST_GUIDE.md) - Comprehensive test documentation

## 📡 REST API

### Endpoint: POST /calculate

**Request:**
```bash
curl -X POST http://localhost:9090/calculate \
  -H "Content-Type: application/json" \
  -d '{"operand1": 5, "operator": "+", "operand2": 3}'
```

**Response (Success):**
```json
{
  "result": 8.0,
  "operand1": 5.0,
  "operator": "+",
  "operand2": 3.0
}
```

**Response (Error):**
```json
{
  "error": "Error: Division by zero is not allowed"
}
```

### Supported Operators
- `+` (Addition)
- `-` (Subtraction)
- `*` (Multiplication)
- `/` (Division)

## 🔄 CI/CD Pipeline

GitHub Actions CI/CD pipeline included with three workflows:

### 1. Build and Test
- **Triggers:** Push/PR to main/develop
- **Tests:** Java 11, 17, 21 (parallel)
- **Checks:** Compilation, tests, code quality, security
- **Duration:** 2-3 minutes
- **Status:** Required for PR merge

### 2. Test Coverage
- **Generates:** Detailed coverage reports
- **PR Comments:** Auto-posts coverage details
- **Duration:** 3-4 minutes

### 3. Release
- **Triggers:** Git tags (v*.*.*)
- **Creates:** GitHub Release with artifacts
- **Artifacts:** JAR, Web package, Release notes
- **Duration:** 3-5 minutes

### Documentation

- [CI_CD_GUIDE.md](CI_CD_GUIDE.md) - Comprehensive CI/CD guide
- [GITHUB_ACTIONS_SUMMARY.md](GITHUB_ACTIONS_SUMMARY.md) - Complete overview
- [.github/WORKFLOWS_README.md](.github/WORKFLOWS_README.md) - Quick reference

## 📁 Project Structure

```
calculator/
├── src/main/java/com/calculator/
│   ├── Calculator.java              # Main entry point
│   ├── CalculatorEngine.java        # Core logic
│   ├── InputParser.java             # Input parsing
│   ├── CommandHandler.java          # Command handling
│   ├── api/
│   │   ├── CalculatorServer.java    # HTTP server
│   │   └── CalculatorController.java # REST API
│   └── exception/
│       ├── CalculatorException.java
│       └── InvalidInputException.java
├── src/main/resources/
│   ├── index.html                   # Web UI
│   ├── style.css                    # Styling
│   └── app.js                       # Frontend logic
├── src/test/java/com/calculator/
│   ├── CalculatorEngineTest.java
│   ├── InputParserTest.java
│   ├── IntegrationTest.java
│   ├── api/
│   │   └── CalculatorControllerTest.java
│   └── exception/
│       ├── CalculatorExceptionTest.java
│       └── InvalidInputExceptionTest.java
├── .github/workflows/
│   ├── build-and-test.yml
│   ├── test-coverage.yml
│   └── release.yml
├── build-and-test.sh                # Build script
└── README.md                        # This file
```

## 💻 System Requirements

### Minimum
- Java 11 or higher
- 512 MB RAM
- Port 9090 available (for web server)

### Recommended
- Java 17 (LTS) or Java 21 (Latest)
- 1 GB RAM
- Linux/macOS/Windows

## 🔐 Security

### Built-in Checks
- ✅ Input validation
- ✅ Division by zero detection
- ✅ Error handling
- ✅ CORS support

### CI/CD Security
- ✅ Dependency scanning
- ✅ Credential detection
- ✅ Code quality checks
- ✅ Security vulnerability scanning

## 📊 Performance

### Typical Execution Times
- Build: 10-15 seconds
- Test Suite: 5-10 seconds
- JAR Creation: 5-10 seconds
- Total (with dependencies): 2-3 minutes

### Calculation Performance
- Average calculation: <1ms
- Test execution: <100ms per test
- API response: <50ms

## 🤝 Contributing

### For Development
1. Clone the repository
2. Create a feature branch
3. Make changes
4. Run tests: `./build-and-test.sh`
5. Push and create PR

### Code Standards
- ✅ 64 passing tests required
- ✅ >80% code coverage
- ✅ No hardcoded credentials
- ✅ Clean commit messages

## 📝 Documentation

- [CLAUDE.md](CLAUDE.md) - Project overview
- [TESTING.md](TESTING.md) - Testing quick start
- [TEST_GUIDE.md](TEST_GUIDE.md) - Detailed test documentation
- [JUNIT_TESTS_SUMMARY.md](JUNIT_TESTS_SUMMARY.md) - Test suite summary
- [CI_CD_GUIDE.md](CI_CD_GUIDE.md) - CI/CD pipeline guide
- [GITHUB_ACTIONS_SUMMARY.md](GITHUB_ACTIONS_SUMMARY.md) - Actions overview

## 🐛 Troubleshooting

### Issue: "Port 9090 already in use"
**Solution:** Change port in `CalculatorServer.java` or kill the process using port 9090

### Issue: "Tests fail locally but pass in CI"
**Solution:** Check Java version - use Java 17 LTS for best compatibility

### Issue: "Dependency download fails"
**Solution:** Check internet connectivity; run `./build-and-test.sh` again

## 📦 Releases

Available on GitHub Releases:
- Executable JAR files
- Web UI packages
- Release notes with changelogs

### Create a Release
```bash
git tag v1.0.0
git push origin v1.0.0
```

## 📞 Support

For issues or questions:
1. Check the relevant documentation file
2. Review test cases for usage examples
3. Check GitHub Actions logs for CI/CD errors

## 📄 License

This project is part of the BFS Batch 15 demonstration.

## 🙏 Credits

Built with:
- Java 11+
- JUnit 5 (Jupiter)
- Mockito 5.2.0
- GitHub Actions

---

**Status:** ✅ Production Ready  
**Last Updated:** October 27, 2026  
**Java Version:** 11+  
**Test Coverage:** >80%  
**CI/CD:** GitHub Actions
