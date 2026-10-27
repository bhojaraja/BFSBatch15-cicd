package com.calculator.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.sun.net.httpserver.HttpExchange;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CalculatorController Tests")
class CalculatorControllerTest {
    private CalculatorController controller;
    private HttpExchange mockExchange;

    @BeforeEach
    void setUp() {
        controller = new CalculatorController();
        mockExchange = mock(HttpExchange.class);
    }

    @Test
    @DisplayName("Handle valid POST request for addition")
    void testHandleValidAddition() throws IOException {
        String requestBody = "{\"operand1\": 5, \"operator\": \"+\", \"operand2\": 3}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(200, -1);
    }

    @Test
    @DisplayName("Handle valid POST request for division")
    void testHandleValidDivision() throws IOException {
        String requestBody = "{\"operand1\": 10, \"operator\": \"/\", \"operand2\": 2}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(200, -1);
    }

    @Test
    @DisplayName("Handle OPTIONS request for CORS")
    void testHandleOptionsRequest() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("OPTIONS");
        when(mockExchange.getResponseHeaders()).thenReturn(
            mock(com.sun.net.httpserver.Headers.class)
        );
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(200, 0);
    }

    @Test
    @DisplayName("Handle unsupported HTTP method")
    void testHandleUnsupportedMethod() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("GET");
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(405, -1);
    }

    @Test
    @DisplayName("Handle division by zero error")
    void testHandleDivisionByZero() throws IOException {
        String requestBody = "{\"operand1\": 10, \"operator\": \"/\", \"operand2\": 0}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(400, -1);
    }

    @Test
    @DisplayName("Handle invalid JSON format")
    void testHandleInvalidJSON() throws IOException {
        String requestBody = "{invalid json}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(400, -1);
    }

    @Test
    @DisplayName("Handle missing operator field")
    void testHandleMissingOperator() throws IOException {
        String requestBody = "{\"operand1\": 5, \"operand2\": 3}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(400, -1);
    }

    @Test
    @DisplayName("Handle multiplication operation")
    void testHandleMultiplication() throws IOException {
        String requestBody = "{\"operand1\": 12, \"operator\": \"*\", \"operand2\": 7}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(200, -1);
    }

    @Test
    @DisplayName("Handle subtraction operation")
    void testHandleSubtraction() throws IOException {
        String requestBody = "{\"operand1\": 50, \"operator\": \"-\", \"operand2\": 20}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        verify(mockExchange).sendResponseHeaders(200, -1);
    }

    @Test
    @DisplayName("Response contains correct result")
    void testResponseContainsResult() throws IOException {
        String requestBody = "{\"operand1\": 7, \"operator\": \"+\", \"operand2\": 5}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        when(mockExchange.getRequestMethod()).thenReturn("POST");
        when(mockExchange.getRequestBody()).thenReturn(inputStream);
        when(mockExchange.getResponseBody()).thenReturn(outputStream);

        controller.handle(mockExchange);

        String response = outputStream.toString();
        assertTrue(response.contains("\"result\": 12.0"));
    }
}
