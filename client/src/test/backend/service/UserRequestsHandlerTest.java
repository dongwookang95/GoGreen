package backend.service;

import backend.entity.LoginRequest;
import backend.entity.user.Friend;
import backend.entity.user.Me;
import backend.entity.user.User;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRequestsHandlerTest {

    private int port = 1080;
    private String baseUrl = "http://localhost:" + port;

    private UserRequestsHandler user;
    private WireMockServer wireMockServer;

    /**
     * This method turns off the Jetty logging.
     * Otherwise it'll use too much memory/whatever on GitLab and
     * the job will fail.
     */
    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    @BeforeEach
    void startServer() {
        EntryRequestHandler.login(new LoginRequest("test", "123"));
        user = UserRequestsHandler.getInstance();

        user.setUrl(baseUrl);
        System.out.println(user.getUrl());

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
    void retrieveAllUsers() {
        stubFor(get(urlEqualTo("/all"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("[\"andy\",\"pitt\",\"janmark\"]")));

        ArrayList<User> res = new ArrayList<User>();
        User u1 = new User("andy");
        User u2 = new User("pitt");
        User u3 = new User("janmark");
        res.add(u1);
        res.add(u2);
        res.add(u3);

        assertEquals(res, user.retrieveAllUsers());
    }

    @Test
    void retrieveAllUsersNull() {
        stubFor(get(urlEqualTo("/all"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")));

        ArrayList<User> res = new ArrayList<User>();
        assertEquals(res, user.retrieveAllUsers());
    }

    @Test
    void retrieveMeNull() {
        stubFor(get(urlEqualTo("/me"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")));
        Me res = user.retrieveMe();
        assertNull(res.getUsername());
    }

    @Test
    void retrieveMeFriends() {
        String bodyString = "{\"username\": \"test\", \"friends\": [{\"username\": \"hana\"," +
            "\"localProduces\": [{\"id\": 3,\"createdDate\": \"2019-04-05\",\"lastModifiedDate\": " +
            "\"2019-04-05\",\"category\": {\"id\": 1,\"createdDate\": \"2019-04-02\"," +
            "\"lastModifiedDate\": \"2019-04-02\",\"description\": \"Food\"},\"subCategory\": " +
            "{\"id\": 2,\"createdDate\": \"2019-04-02\",\"lastModifiedDate\": \"2019-04-02\"," +
            "\"description\": \"Local Produce\"},\"amount\": 2645.9599609375," +
            "\"numberOfMeals\": 4 }],\"vegetarianMeals\": [],\"solarPanels\": []," +
            "\"lowerTemperatures\": [],\"publicTransports\": [],\"travelByBikes\": []," +
            "\"achievements\": [{\"id\": 42,\"createdDate\": \"2019-04-05\"," +
            "\"lastModifiedDate\": \"2019-04-06\",\"badge\": {\"id\": 2,\"createdDate\": " +
            "\"2019-04-02\",\"lastModifiedDate\": \"2019-04-02\",\"name\": \"silver\"}," +
            "\"category\": {\"id\": 2,\"createdDate\": \"2019-04-02\",\"lastModifiedDate\": " +
            "\"2019-04-02\",\"description\": \"Transport\"}} ]}],\"invites\": []," +
            "\"requests\": [],\"achievements\": []}";

        stubFor(get(urlEqualTo("/me"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody(bodyString)));

        Me res = user.retrieveMe();
        assertEquals("test", res.getUsername());
        assertEquals(1, res.getFriends().size());
    }

    // If for some reason your tests for a PUT request
    // fails (likely due to a "connection refused error"
    // use Thread.sleep(5000) as your first line of code in your test
    // The reason is, to prevent the mock server from shutting down too fast.

    @Test
    void getInstance() {
        assertEquals(UserRequestsHandler.class, UserRequestsHandler.getInstance().getClass());
    }

}