package com.calculator.api;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

public class CalculatorServer {
    private HttpServer server;
    private static final int PORT = 9090;
    private static final String RESOURCES_DIR = "src/main/resources";

    public CalculatorServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.setExecutor(Executors.newFixedThreadPool(10));
        setupRoutes();
    }

    private void setupRoutes() {
        server.createContext("/", new StaticFileHandler());
        server.createContext("/calculate", new CalculatorController());
    }

    public void start() {
        server.start();
        System.out.println("\n================================");
        System.out.println("Calculator server started!");
        System.out.println("Open your browser: http://localhost:" + PORT);
        System.out.println("================================\n");
    }

    public void stop() {
        server.stop(0);
    }

    private static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            String filePath = RESOURCES_DIR + path;
            try {
                byte[] content = Files.readAllBytes(Paths.get(filePath));
                String contentType = getContentType(filePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, content.length);
                exchange.getResponseBody().write(content);
                exchange.close();
            } catch (IOException e) {
                sendError(exchange, 404, "File not found: " + path);
            }
        }

        private String getContentType(String filePath) {
            if (filePath.endsWith(".html")) return "text/html";
            if (filePath.endsWith(".css")) return "text/css";
            if (filePath.endsWith(".js")) return "application/javascript";
            if (filePath.endsWith(".json")) return "application/json";
            return "text/plain";
        }

        private void sendError(HttpExchange exchange, int code, String message) throws IOException {
            exchange.sendResponseHeaders(code, message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.close();
        }
    }
}
