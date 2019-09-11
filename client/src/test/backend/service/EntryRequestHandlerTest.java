package backend.service;

import backend.entity.ConnectionResponse;
import backend.entity.LoginRequest;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryRequestHandlerTest {

    private int port = 1080;
    private String baseUrl = "http://localhost:" + port;

    private EntryRequestHandler handler;
    private WireMockServer wireMockServer;

    /**
     * Turning of Jetty logging to not use too much memory.
     */
    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    @BeforeEach
    void startServer() {
        // have to create new instance so i can assign the url
        handler = new EntryRequestHandler();
        handler.setUrl(baseUrl);

        wireMockServer = new WireMockServer(port);
        // force WireMock to use ONE port
        WireMock.configureFor("localhost", port);
        wireMockServer.start();
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void loginSuccess() {
        String requestBody = "{\"username\": \"hana\"," +
            "\"password\": \"a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3\"}";
        String responseBody = "{\"username\":\"hana\",\"token\":\"abc456\"}";
        stubFor(post(urlEqualTo("/login"))
            .withRequestBody(equalToJson(requestBody))
            .willReturn(aResponse()
                .withHeader("Content-type", "application/json")
                .withBody(responseBody)));

        LoginRequest testUser = new LoginRequest("hana", "123");
        ConnectionResponse result = handler.login(testUser);
        assertEquals("hana", result.getUsername());
    }

    @Test
    void loginFail() {
        String requestBody = "{\"username\": \"hana\"," +
            "\"password\": \"a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3\"}";
        String responseBody = "{\"username\":\"hana\",\"token\":\"abc456\"}";
        stubFor(post(urlEqualTo("/login"))
            .withRequestBody(equalToJson(requestBody))
            .willReturn(aResponse()
                .withHeader("Content-type", "application/json")
                .withBody(responseBody)));

        // wrong password
        LoginRequest testUser = new LoginRequest("hana", "321");
        ConnectionResponse result = handler.login(testUser);
        assertEquals("Invalid", result.getToken());
    }

    @Test
    void createNewUserSuccess() {
        String requestBody = "{\"username\": \"hana\"," +
            "\"password\": \"a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3\"}";
        stubFor(post(urlEqualTo("/register"))
            .withRequestBody(equalToJson(requestBody))
            .willReturn(ok()));

        LoginRequest testUser = new LoginRequest("hana", "123");
        String result = handler.createNewUser(testUser);
        assertEquals("Successfully registered.", result);
    }

    @Test
    void createNewUserFail() {
        String requestBody = "{\"username\": \"hana\"," +
            "\"password\": \"a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3\"}";
        stubFor(post(urlEqualTo("/register"))
            .withRequestBody(equalToJson(requestBody))
            .willReturn(aResponse().withStatus(500)));

        LoginRequest testUser = new LoginRequest("hana", "123");
        String result = handler.createNewUser(testUser);
        assertEquals("Username already exists", result);
    }

    @Test
    void encode() {
        String hashed = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
        assertEquals(hashed, Hashing.sha256().hashString("123", StandardCharsets.UTF_8).toString());
    }
}