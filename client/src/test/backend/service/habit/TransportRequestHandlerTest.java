package backend.service.habit;

import backend.entity.LoginRequest;
import backend.entity.habit.transport.TravelByBike;
import backend.entity.habit.transport.PublicTransport;
import backend.entity.user.User;
import backend.service.AuthHeader;
import backend.service.EntryRequestHandler;
import backend.service.tab.HabitRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class TransportRequestHandlerTest {

    private int port = 1080;
    private String host = "http://localhost:" + port;
    private String bikeUrl = "/transport/travel_by_bike/";
    private String transportUrl = "/transport/public_transport/";

    private TransportRequestHandler transportHandler;
    private WireMockServer wireMockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private String bikeRidesResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null," +
        "\"amount\":0.0,\"kilometers\":0.0,\"transportTypeInstead\":null}]";

    private String transportsResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null," +
        "\"amount\":0.0,\"kilometers\":0.0,\"transportTypeInstead\":null," +
        "\"transportTypeActual\":null}]";

    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    @BeforeEach
    void startServer() {
        EntryRequestHandler.login(new LoginRequest("test", "123"));
        transportHandler = TransportRequestHandler.getInstance();
        transportHandler.setGeneralUrl(host);

        wireMockServer = new WireMockServer(port);
        WireMock.configureFor("localhost", port);
        wireMockServer.start();
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void updateTravelByBikeByIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        TravelByBike bike = new TravelByBike();
        long id = 3;
        String bikeString = mapper.writeValueAsString(bike);

        stubFor(put(urlEqualTo(bikeUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(bikeString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = transportHandler.updateBikeRideById(bike, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void updatePublicTransportIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        PublicTransport transport = new PublicTransport();
        long id = 4;
        String transString = mapper.writeValueAsString(transport);

        stubFor(put(urlEqualTo(transportUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(transString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = transportHandler.updatePublicTransportById(transport, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void getTravelByBikesTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(bikeUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bikeRidesResponse)
                )
        );

        String response = mapper.writeValueAsString(transportHandler.getUserBikeRides());
        assertEquals(bikeRidesResponse, response);
    }

    @Test
    void getPublicTransportsTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(transportUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(transportsResponse)
                )
        );

        String response = mapper.writeValueAsString(transportHandler.getUserPublicTransports());
        assertEquals(transportsResponse, response);
    }

    @Test
    void sendPublicTransportTest() throws JsonProcessingException {
        PublicTransport transport = new PublicTransport();
        String transString = mapper.writeValueAsString(transport);

        stubFor(post(urlEqualTo(transportUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(transString))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = transportHandler.sendPublicTransport(transport).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void sendTravelByBikeTest() throws JsonProcessingException {
        TravelByBike bike = new TravelByBike();
        String bikeString = mapper.writeValueAsString(bike);

        stubFor(post(urlEqualTo(bikeUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(bikeString))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = transportHandler.sendTravelByBike(bike).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void deletePublicTransportByIdTest() {
        PublicTransport transport = new PublicTransport();
        long id = 4;

        stubFor(delete(urlEqualTo(transportUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        ResponseEntity response = transportHandler.deletePublicTransportById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void deleteBikeRidesByIdTest() {
        TravelByBike bike = new TravelByBike();
        long id = 4;

        stubFor(delete(urlEqualTo(bikeUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        ResponseEntity response = transportHandler.deleteBikeRideById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void getFoodHabitTest() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            JsonProcessingException {
        stubFor(get(urlEqualTo(transportUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(transportsResponse)
                )
        );

        stubFor(get(urlEqualTo(bikeUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(bikeRidesResponse)
                )
        );

        Method method = TransportRequestHandler.class.getDeclaredMethod("getTransportHabit", String.class);
        method.setAccessible(true);

        ArrayList bike = (ArrayList) method.invoke(transportHandler, bikeUrl);
        ArrayList transport = (ArrayList) method.invoke(transportHandler, transportUrl);
        String bikeString = mapper.writeValueAsString(bike);
        String transString = mapper.writeValueAsString(transport);

        assertEquals(bikeRidesResponse, bikeString);
        assertEquals(transportsResponse, transString);
    }

    @Test
    void getFoodHabitTest_differentUrl() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(transportUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(transportsResponse)
            )
        );

        stubFor(get(urlEqualTo(bikeUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(bikeRidesResponse)
            )
        );

        stubFor(get(urlEqualTo(bikeUrl + "wrong"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        stubFor(get(urlEqualTo(transportUrl + "wrong"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        Method method = TransportRequestHandler.class.getDeclaredMethod("getTransportHabit", String.class);
        method.setAccessible(true);

        ArrayList bike = (ArrayList) method.invoke(transportHandler, bikeUrl + "/wrong");
        ArrayList transport = (ArrayList) method.invoke(transportHandler, transportUrl + "/wrong");
        String bikeString = mapper.writeValueAsString(bike);
        String transString = mapper.writeValueAsString(transport);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, bikeString);
        assertEquals(expectedResString, transString);
    }

    @Test
    void getFoodHabitTest_empty() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(transportUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        stubFor(get(urlEqualTo(bikeUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        Method method = TransportRequestHandler.class.getDeclaredMethod("getTransportHabit", String.class);
        method.setAccessible(true);

        ArrayList bike = (ArrayList) method.invoke(transportHandler, bikeUrl);
        ArrayList transport = (ArrayList) method.invoke(transportHandler, transportUrl);
        String bikeString = mapper.writeValueAsString(bike);
        String transString = mapper.writeValueAsString(transport);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, bikeString);
        assertEquals(expectedResString, transString);
    }

    @Test
    void getFoodHabitTest_exception() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {

        String wrongBikeRidesResponse = "[{" +
            "\"id\":5," +
            "\"user\":{\"username\":\"hana\"}," +
            "\"description\":\"cycling to somewhere\"," +
            "\"amount\":[8.0, 5.0]" +
            "}]";

        String wrongTransportsResponse = "[{" +
            "\"id\":6," +
            "\"user\":{\"username\":\"hana\"}," +
            "\"description\":\"adfaf\"," +
            "\"amount\":[10.0, 5.0]}]";

        stubFor(get(urlEqualTo(transportUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongTransportsResponse)
            )
        );

        stubFor(get(urlEqualTo(bikeUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongBikeRidesResponse)
            )
        );

        Method method = TransportRequestHandler.class.getDeclaredMethod("getTransportHabit", String.class);
        method.setAccessible(true);

        ArrayList bike = (ArrayList) method.invoke(transportHandler, bikeUrl);
        ArrayList transport = (ArrayList) method.invoke(transportHandler, transportUrl);
        String bikeString = mapper.writeValueAsString(bike);
        String transString = mapper.writeValueAsString(transport);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, bikeString);
        assertEquals(expectedResString, transString);
    }

    @Test
    void getInstance() {
        assertEquals(TransportRequestHandler.class, transportHandler.getClass());
    }

    @Test
    void getSetGeneralUrlTest() {
        transportHandler.setGeneralUrl("www.google.com");
        assertEquals("www.google.com", transportHandler.getGeneralUrl());
    }

    @Test
    void getBikeUrlTest() {
        String lpUrl = transportHandler.getBikeUrl();
        assertEquals(lpUrl, transportHandler.getBikeUrl());
    }

    @Test
    void getTransUrlTest() {
        String vmUrl = transportHandler.getPtUrl();
        assertEquals(vmUrl, transportHandler.getPtUrl());
    }

    @Test
    void getSetAuthHeaderTest() {
        AuthHeader auth = new AuthHeader();
        transportHandler.setAuth(auth);
        assertEquals(auth, transportHandler.getAuth());
    }

    @Test
    void getSetRestTemplateTest() {
        RestTemplate rest = new RestTemplate();
        transportHandler.setRestTemplate(rest);
        assertEquals(rest, transportHandler.getRestTemplate());
    }

    @Test
    void getSetObjectMapperTest() {
        ObjectMapper mapperrr = new ObjectMapper();
        transportHandler.setObjectMapper(mapperrr);
        assertEquals(mapperrr, transportHandler.getObjectMapper());
    }

    @Test
    void getSetHabitHandlerTest() {
        HabitRequestHandler jojo = HabitRequestHandler.getInstance();
        transportHandler.setHabitHandler(jojo);
        assertEquals(jojo, transportHandler.getHabitHandler());
    }

}