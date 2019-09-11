package backend.service.habit;

import backend.entity.LoginRequest;
import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
import backend.service.AuthHeader;
import backend.service.EntryRequestHandler;
import backend.service.tab.HabitRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnergyRequestHandlerTest {

    private int port = 1080;
    private String host = "http://localhost:" + port;
    private String tempUrl = "/energy/lower_temperature/";
    private String solarUrl = "/energy/solar_panel/";

    private EnergyRequestHandler energyHandler;
    private WireMockServer wireMockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private String lowerTemperaturesResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null,\"amount\":0.0,\"hours\":0.0,\"degrees\":2.0}]";

    private String solarPanelsResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null,\"amount\":0.0,\"hours\":10.0,\"watt\":0," +
        "\"numberOfSolarPanels\":0}]";

    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    @BeforeEach
    void startServer() {
        EntryRequestHandler.login(new LoginRequest("test", "123"));
        
        energyHandler = EnergyRequestHandler.getInstance();
        energyHandler.setGeneralUrl(host);

        wireMockServer = new WireMockServer(wireMockConfig()
            .port(port)
            .asynchronousResponseEnabled(true)
            .asynchronousResponseThreads(6)
        );
        wireMockServer = new WireMockServer(port);
        WireMock.configureFor("localhost", port);
        wireMockServer.start();
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void updateLowerTemperatureByIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        LowerTemperature temp = new LowerTemperature();
        temp.setDegrees(20.0);
        long id = 3;
        String tempString = mapper.writeValueAsString(temp);

        stubFor(put(urlEqualTo(tempUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(tempString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = energyHandler.updateTemperatureById(temp, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void updateSolarPanelIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        SolarPanel panel = new SolarPanel();
        panel.setHours(10.0);
        long id = 3;
        String panelString = mapper.writeValueAsString(panel);

        stubFor(put(urlEqualTo(solarUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(panelString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = energyHandler.updateSolarPanelById(panel, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void getLowerTemperaturesTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(tempUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(lowerTemperaturesResponse)
                )
        );

        String response = mapper.writeValueAsString(energyHandler.getUserTemperatures());
        assertEquals(lowerTemperaturesResponse, response);
    }

    @Test
    void getSolarPanelsTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(solarUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(solarPanelsResponse)
                )
        );

        String response = mapper.writeValueAsString(energyHandler.getUserSolarPanels());
        assertEquals(solarPanelsResponse, response);
    }

    @Test
    void sendSolarPanelTest() throws JsonProcessingException {
        SolarPanel panel = new SolarPanel();
        String panelString = mapper.writeValueAsString(panel);

        stubFor(post(urlEqualTo(solarUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(panelString))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = energyHandler.sendSolarPanel(panel).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void sendLowerTemperatureTest() throws JsonProcessingException {
        LowerTemperature temp = new LowerTemperature();
        String tempString = mapper.writeValueAsString(temp);

        stubFor(post(urlEqualTo(tempUrl))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .withRequestBody(containing(tempString))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = energyHandler.sendLowerTemperature(temp).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void deleteSolarPanelByIdTest() {
        SolarPanel solar = new SolarPanel();
        long id = 3;

        stubFor(delete(urlEqualTo(solarUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        ResponseEntity response = energyHandler.deleteSolarPanelById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void deleteLowerTempIdTest() {
        LowerTemperature temp = new LowerTemperature();
        long id = 3;

        stubFor(delete(urlEqualTo(tempUrl + id))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        ResponseEntity response = energyHandler.deleteLowerTemperatureById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void getFoodHabitTest() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(solarUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(solarPanelsResponse)
            )
        );

        stubFor(get(urlEqualTo(tempUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(lowerTemperaturesResponse)
            )
        );

        Method method = EnergyRequestHandler.class.getDeclaredMethod("getEnergyHabit", String.class);
        method.setAccessible(true);

        ArrayList temp = (ArrayList) method.invoke(energyHandler, tempUrl);
        ArrayList panel = (ArrayList) method.invoke(energyHandler, solarUrl);
        String tempString = mapper.writeValueAsString(temp);
        String panelString = mapper.writeValueAsString(panel);

        assertEquals(lowerTemperaturesResponse, tempString);
        assertEquals(solarPanelsResponse, panelString);
    }

    @Test
    void getFoodHabitTest_differentUrls() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(solarUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(solarPanelsResponse)
            )
        );

        stubFor(get(urlEqualTo(tempUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(lowerTemperaturesResponse)
            )
        );

        stubFor(get(urlEqualTo(solarUrl + "wrong"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(solarPanelsResponse)
            )
        );

        stubFor(get(urlEqualTo(tempUrl + "wrong"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(lowerTemperaturesResponse)
            )
        );

        Method method = EnergyRequestHandler.class.getDeclaredMethod("getEnergyHabit", String.class);
        method.setAccessible(true);

        ArrayList temp = (ArrayList) method.invoke(energyHandler, tempUrl + "/wrong");
        ArrayList panel = (ArrayList) method.invoke(energyHandler, solarUrl + "/wrong");
        String tempString = mapper.writeValueAsString(temp);
        String panelString = mapper.writeValueAsString(panel);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, tempString);
        assertEquals(expectedResString, panelString);
    }

    @Test
    void getFoodHabitTest_empty() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(solarUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        stubFor(get(urlEqualTo(tempUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        Method method = EnergyRequestHandler.class.getDeclaredMethod("getEnergyHabit", String.class);
        method.setAccessible(true);

        ArrayList temp = (ArrayList) method.invoke(energyHandler, tempUrl);
        ArrayList panel = (ArrayList) method.invoke(energyHandler, solarUrl);
        String tempString = mapper.writeValueAsString(temp);
        String panelString = mapper.writeValueAsString(panel);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, tempString);
        assertEquals(expectedResString, panelString);
    }

    @Test
    void getFoodHabitTest_exception() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {

        String wrongLowerTemperaturesResponse = "[{" +
            "\"id\":5," +
            "\"user\":{\"username\":\"hana\"}," +
            "\"description\":\"Turning down the heat\"," +
            "\"amount\":[8.0, 5.0]" +
            "}]";

        String wrongSolarPanelsResponse = "[{" +
            "\"id\":6," +
            "\"user\":{\"username\":\"hana\"}," +
            "\"description\":\"Panels from Eneco\"," +
            "\"amount\":[10.0, 5.0]}]";

        stubFor(get(urlEqualTo(solarUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongSolarPanelsResponse)
            )
        );

        stubFor(get(urlEqualTo(tempUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongLowerTemperaturesResponse)
            )
        );

        Method method = EnergyRequestHandler.class.getDeclaredMethod("getEnergyHabit", String.class);
        method.setAccessible(true);

        ArrayList temp = (ArrayList) method.invoke(energyHandler, tempUrl);
        ArrayList panel = (ArrayList) method.invoke(energyHandler, solarUrl);
        String tempString = mapper.writeValueAsString(temp);
        String panelString = mapper.writeValueAsString(panel);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, tempString);
        assertEquals(expectedResString, panelString);
    }

    @Test
    void getInstance() {
        assertEquals(EnergyRequestHandler.class, energyHandler.getClass());
    }

    @Test
    void getSetGeneralUrlTest() {
        energyHandler.setGeneralUrl("www.google.com");
        assertEquals("www.google.com", energyHandler.getGeneralUrl());
    }

    @Test
    void getTempUrlTest() {
        String lpUrl = energyHandler.getTempUrl();
        assertEquals(lpUrl, energyHandler.getTempUrl());
    }

    @Test
    void getSolarUrlTest() {
        String vmUrl = energyHandler.getSolarUrl();
        assertEquals(vmUrl, energyHandler.getSolarUrl());
    }

    @Test
    void getSetAuthHeaderTest() {
        AuthHeader auth = new AuthHeader();
        energyHandler.setAuth(auth);
        assertEquals(auth, energyHandler.getAuth());
    }

    @Test
    void getSetRestTemplateTest() {
        RestTemplate rest = new RestTemplate();
        energyHandler.setRestTemplate(rest);
        assertEquals(rest, energyHandler.getRestTemplate());
    }

    @Test
    void getSetObjectMapperTest() {
        ObjectMapper mapperrr = new ObjectMapper();
        energyHandler.setObjectMapper(mapperrr);
        assertEquals(mapperrr, energyHandler.getObjectMapper());
    }

    @Test
    void getSetHabitHandlerTest() {
        HabitRequestHandler jojo = HabitRequestHandler.getInstance();
        energyHandler.setHabitHandler(jojo);
        assertEquals(jojo, energyHandler.getHabitHandler());
    }

}