#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}=== Calculator Build & Test ===${NC}\n"

# Create directories
mkdir -p bin
mkdir -p bin/test

# Download JUnit and other dependencies if not present
if [ ! -f "lib/junit-jupiter-api-5.9.2.jar" ]; then
    echo -e "${YELLOW}Downloading JUnit dependencies...${NC}"
    mkdir -p lib
    cd lib

    # Download JUnit 5 jars
    echo "Downloading junit-jupiter-api-5.9.2.jar..."
    curl -s https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.2/junit-jupiter-api-5.9.2.jar -o junit-jupiter-api-5.9.2.jar

    echo "Downloading junit-jupiter-engine-5.9.2.jar..."
    curl -s https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.9.2/junit-jupiter-engine-5.9.2.jar -o junit-jupiter-engine-5.9.2.jar

    echo "Downloading junit-platform-console-standalone-1.9.2.jar..."
    curl -s https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar -o junit-platform-console-standalone-1.9.2.jar

    echo "Downloading mockito-core-5.2.0.jar..."
    curl -s https://repo1.maven.org/maven2/org/mockito/mockito-core/5.2.0/mockito-core-5.2.0.jar -o mockito-core-5.2.0.jar

    echo "Downloading mockito-junit-jupiter-5.2.0.jar..."
    curl -s https://repo1.maven.org/maven2/org/mockito/mockito-junit-jupiter/5.2.0/mockito-junit-jupiter-5.2.0.jar -o mockito-junit-jupiter-5.2.0.jar

    echo "Downloading byte-buddy dependencies..."
    curl -s https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.14.1/byte-buddy-1.14.1.jar -o byte-buddy-1.14.1.jar
    curl -s https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.14.1/byte-buddy-agent-1.14.1.jar -o byte-buddy-agent-1.14.1.jar

    cd ..
fi

# Compile main source
echo -e "${YELLOW}Compiling main source code...${NC}"
javac -d bin src/main/java/com/calculator/*.java src/main/java/com/calculator/exception/*.java src/main/java/com/calculator/api/*.java

if [ $? -ne 0 ]; then
    echo -e "${RED}✗ Main compilation failed${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Main compilation successful${NC}\n"

# Compile test source
echo -e "${YELLOW}Compiling test source code...${NC}"

CLASSPATH="bin:lib/*"
javac -cp $CLASSPATH -d bin/test \
    src/test/java/com/calculator/*.java \
    src/test/java/com/calculator/api/*.java \
    src/test/java/com/calculator/exception/*.java 2>&1

if [ $? -ne 0 ]; then
    echo -e "${RED}✗ Test compilation failed${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Test compilation successful${NC}\n"

# Run tests
echo -e "${YELLOW}Running JUnit tests...${NC}\n"
java -cp "$CLASSPATH:bin/test" org.junit.platform.console.ConsoleLauncher \
    --scan-classpath bin/test

TEST_RESULT=$?

if [ $TEST_RESULT -eq 0 ]; then
    echo -e "\n${GREEN}✓ All tests passed!${NC}\n"
else
    echo -e "\n${RED}✗ Some tests failed${NC}\n"
fi

echo -e "${YELLOW}=== Build Complete ===${NC}"
exit $TEST_RESULT
