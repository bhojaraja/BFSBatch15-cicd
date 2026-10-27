package com.calculator.api;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.calculator.CalculatorEngine;
import com.calculator.exception.CalculatorException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorController implements HttpHandler {
    private CalculatorEngine engine = new CalculatorEngine();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleCalculate(exchange);
        } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
            handleOptions(exchange);
        } else {
            sendError(exchange, 405, "{\"error\": \"Method not allowed\"}");
        }
    }

    private void handleCalculate(HttpExchange exchange) throws IOException {
        try {
            String requestBody = readRequestBody(exchange);
            CalculationRequest request = parseRequest(requestBody);

            double result = engine.calculate(request.operand1, request.operator, request.operand2);

            String response = String.format(
                "{\"result\": %s, \"operand1\": %s, \"operator\": \"%s\", \"operand2\": %s}",
                result, request.operand1, request.operator, request.operand2
            );

            sendResponse(exchange, 200, response);
        } catch (CalculatorException e) {
            sendResponse(exchange, 400, "{\"error\": \"" + escapeJson(e.getMessage()) + "\"}");
        } catch (NumberFormatException e) {
            sendResponse(exchange, 400, "{\"error\": \"Invalid number format\"}");
        } catch (Exception e) {
            sendResponse(exchange, 500, "{\"error\": \"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private void handleOptions(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }

    private CalculationRequest parseRequest(String json) throws Exception {
        json = json.trim();
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON format");
        }

        json = json.substring(1, json.length() - 1);
        CalculationRequest request = new CalculationRequest();

        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");

                if ("operand1".equals(key)) {
                    request.operand1 = Double.parseDouble(value);
                } else if ("operand2".equals(key)) {
                    request.operand2 = Double.parseDouble(value);
                } else if ("operator".equals(key)) {
                    request.operator = value;
                }
            }
        }

        if (request.operator == null || request.operator.isEmpty()) {
            throw new IllegalArgumentException("Missing operator");
        }

        return request;
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder sb = new StringBuilder();
        while ((bytesRead = is.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    private void sendResponse(HttpExchange exchange, int code, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(code, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.close();
    }

    private void sendError(HttpExchange exchange, int code, String message) throws IOException {
        byte[] responseBytes = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.close();
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r");
    }

    private static class CalculationRequest {
        double operand1;
        String operator;
        double operand2;
    }
}
