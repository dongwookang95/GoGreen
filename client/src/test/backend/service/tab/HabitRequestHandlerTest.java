package backend.service.tab;

import backend.entity.LoginRequest;
import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
import backend.entity.habit.structure.Attribute;
import backend.entity.habit.structure.Subcategory;
import backend.entity.habit.transport.PublicTransport;
import backend.entity.habit.transport.TravelByBike;
import backend.service.AuthHeader;
import backend.service.EntryRequestHandler;
import backend.service.habit.EnergyRequestHandler;
import backend.service.habit.FoodRequestHandler;
import backend.service.habit.TransportRequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;

class HabitRequestHandlerTest {

    private int port = 1080;
    private String baseUrl = "http://localhost:" + port;

    private HabitRequestHandler handler;
    private WireMockServer wireMockServer;
    ObjectMapper mapper;

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
        EntryRequestHandler.login(new LoginRequest("test", "123"));
        mapper = new ObjectMapper();
        handler = HabitRequestHandler.getInstance();

        handler.setGeneralUrl(baseUrl);
        System.out.println(handler.getGeneralUrl());

        handler.getFoodHandler().setGeneralUrl(baseUrl);
        handler.getEnergyHandler().setGeneralUrl(baseUrl);
        handler.getTransportHandler().setGeneralUrl(baseUrl);

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
    void sendHabit_LocalProduce() throws JsonProcessingException {
        LocalProduce product = new LocalProduce();
        product.setNumberOfMeals(1);
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/food/local_produce/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("NumberOfMeals", "1");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry);
        assertEquals("Created", handler.sendHabit("Local Produce", list));
    }

    @Test
    void editHabit_LocalProduce() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        LocalProduce product = new LocalProduce();
        product.setNumberOfMeals(1);
        long id = 3;
        product.setId(id);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/food/local_produce/" + id))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("NumberOfMeals", "1");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry);
        assertEquals("Updated", handler.editHabit(product, "Local Produce", list));
    }

    @Test
    void sendHabit_VegetarianMeal() throws JsonProcessingException {
        VegetarianMeal product = new VegetarianMeal();
        product.setNumberOfMeals(1);
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/food/vegetarian_meal/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("NumberOfMeals", "1");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry);
        assertEquals("Created", handler.sendHabit("Vegetarian Meal", list));
    }

    @Test
    void editHabit_VegetarianMeal() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        VegetarianMeal product = new VegetarianMeal();
        product.setNumberOfMeals(1);
        long id = 4;
        product.setId(4);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/food/vegetarian_meal/" + id))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("NumberOfMeals", "1");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry);
        assertEquals("Updated", handler.editHabit(product, "Vegetarian Meal", list));
    }

    @Test
    void sendHabit_LowerTemperature() throws JsonProcessingException {
        LowerTemperature product = new LowerTemperature();
        product.setDegrees(20);
        product.setHours(2);
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/energy/lower_temperature/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Degrees", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("Hours", "2");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        assertEquals("Created", handler.sendHabit("Lower Temperature", list));
        assertEquals("", handler.sendHabit("None of the options", list));
    }

    @Test
    void editHabit_LowerTemperature() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        LowerTemperature product = new LowerTemperature();
        product.setDegrees(20);
        product.setHours(2);
        long id = 4;
        product.setId(id);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/energy/lower_temperature/" + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Degrees", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("Hours", "2");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        assertEquals("Updated", handler.editHabit(product, "Lower Temperature", list));
    }

    @Test
    void sendHabit_SolarPanels() throws JsonProcessingException {
        SolarPanel product = new SolarPanel();
        product.setWatt(20);
        product.setHours(2);
        product.setNumberOfSolarPanels(3);
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/energy/solar_panel/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Watt", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("Hours", "2");
        Map.Entry<String, String> entry3 =
            new AbstractMap.SimpleEntry<>("NumberOfSolarPanels", "3");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        assertEquals("Created", handler.sendHabit("Solar Panel", list));
    }

    @Test
    void editHabit_SolarPanels() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        SolarPanel product = new SolarPanel();
        product.setWatt(20);
        product.setHours(2);
        product.setNumberOfSolarPanels(3);
        long id = 4;
        product.setId(id);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/energy/solar_panel/" + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Watt", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("Hours", "2");
        Map.Entry<String, String> entry3 =
            new AbstractMap.SimpleEntry<>("NumberOfSolarPanels", "3");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        assertEquals("Updated", handler.editHabit(product, "Solar Panel", list));
    }

    @Test
    void sendHabit_PublicTransport() throws JsonProcessingException {
        PublicTransport product = new PublicTransport();
        product.setKilometers(0);
        product.setTransportTypeInstead("CAR");
        product.setTransportTypeActual("TRAM");
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/transport/public_transport/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Kilometres", "0");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("TransportTypeActual", "TRAM");
        Map.Entry<String, String> entry3 =
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "CAR");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        assertEquals("Created", handler.sendHabit("Public Transport", list));
    }

    @Test
    void editHabit_PublicTransport() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        PublicTransport product = new PublicTransport();
        product.setKilometers(20);
        product.setTransportTypeInstead("CAR");
        product.setTransportTypeActual("TRAM");
        long id = 4;
        product.setId(id);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/transport/public_transport/" + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Kilometres", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("TransportTypeActual", "TRAM");
        Map.Entry<String, String> entry3 =
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "CAR");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        assertEquals("Updated", handler.editHabit(product, "Public Transport", list));
        assertEquals("", handler.editHabit(product, "Something else", list));
    }

    @Test
    void sendHabit_TravelByBike() throws JsonProcessingException {
        TravelByBike product = new TravelByBike();
        product.setKilometers(0);
        product.setTransportTypeInstead("CAR");
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo("/transport/travel_by_bike/"))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("Kilometres", "0");
        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "CAR");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);

        assertEquals("Created", handler.sendHabit("Travel By Bike", list));
    }

    @Test
    void editHabit_TravelByBike() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        TravelByBike product = new TravelByBike();
        product.setKilometers(20);
        product.setTransportTypeInstead("CAR");
        long id = 4;
        product.setId(id);
        String productString = mapper.writeValueAsString(product);

        stubFor(put(urlEqualTo("/transport/travel_by_bike/" + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        Map.Entry<String, String> entry1 =
            new AbstractMap.SimpleEntry<>("Kilometres", "20");
        Map.Entry<String, String> entry2 =
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "CAR");
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>();
        list.add(entry1);
        list.add(entry2);
        assertEquals("Updated", handler.editHabit(product, "Travel By Bike", list));
    }

    @Test
    void checkStatusCode()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ResponseEntity created = new ResponseEntity(CREATED);
        ResponseEntity updated = new ResponseEntity(NO_CONTENT);
        ResponseEntity def = new ResponseEntity(ACCEPTED);

        Method method = HabitRequestHandler.class.getDeclaredMethod("checkStatusCode", ResponseEntity.class);
        method.setAccessible(true);

        assertEquals("Created", (String) method.invoke(handler, created));
        assertEquals("Updated", (String) method.invoke(handler, updated));
        assertEquals("Error", (String) method.invoke(handler, def));
    }

    @Test
    void createCategoryDescriptionList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Food");
        list.add("Transport");
        list.add("Energy");
        assertEquals(list, handler.createCategoryDescriptionList());
    }

    @Test
    void retrieveSubcategoryList() {
        ArrayList<Subcategory> listEmpty = new ArrayList<>();
        ArrayList<Subcategory> res = handler.retrieveSubcategoryList("Food");
        assertEquals(2, res.size());
        assertEquals(listEmpty, handler.retrieveSubcategoryList("Something else"));
    }

    @Test
    void createSubcategoryDescriptionList() {
        assertEquals(2, handler.createSubcategoryDescriptionList("Food").size());
    }

    @Test
    void retrievesAttributesList() {
        ArrayList<Attribute> emptyList = new ArrayList<>();
        assertEquals("numberOfMeals",
            handler.retrieveAttributesList("Vegetarian Meal",
                handler.retrieveSubcategoryList("Food")).
                get(0).getDescription());
        assertEquals(emptyList, handler.retrieveAttributesList("Vegan Meal", handler.retrieveSubcategoryList("Food")));
    }

    @Test
    void getInstance() {
        assertEquals(HabitRequestHandler.class, HabitRequestHandler.getInstance().getClass());
    }

    @Test
    void getSetGeneralUrlTest() {
        handler.setGeneralUrl("www.google.com");
        assertEquals("www.google.com", handler.getGeneralUrl());
    }

    @Test
    void geLpUrlTest() {
        String lpUrl = handler.getLpUrl();
        assertEquals(lpUrl, handler.getLpUrl());
    }

    @Test
    void getVmUrlTest() {
        String vmUrl = handler.getVmUrl();
        assertEquals(vmUrl, handler.getVmUrl());
    }

    @Test
    void getSetFoodRequestHandlerTest() {
        FoodRequestHandler frh = FoodRequestHandler.getInstance();
        assertEquals(frh, handler.getFoodHandler());
    }

    @Test
    void getSetEnergyRequestHandlerTest() {
        EnergyRequestHandler erh = EnergyRequestHandler.getInstance();
        assertEquals(erh, handler.getEnergyHandler());
    }

    @Test
    void getSetTransportRequestHandlerTest() {
        TransportRequestHandler trh = TransportRequestHandler.getInstance();
        assertEquals(trh, handler.getTransportHandler());
    }

}