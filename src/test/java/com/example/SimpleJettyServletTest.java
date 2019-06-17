package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleJettyServletTest {

    @Test
    void test() throws Exception {
        Server server = new Server(8080);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(HelloServlet.class, "/hello");
        server.setHandler(handler);

        server.start();

        HttpURLConnection conn = (HttpURLConnection) URI.create("http://localhost:8080/hello").toURL().openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            assertEquals("Hello Servlet!!", reader.readLine());
        }
        conn.disconnect();

        server.stop();
    }
}
