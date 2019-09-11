package backend.service.habit;

import backend.entity.LoginRequest;
import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

class FoodRequestHandlerTest {

    private int port = 1080;
    private String host = "http://localhost:" + port;
    private String mealUrl = "/food/vegetarian_meal/";
    private String productUrl = "/food/local_produce/";

    private FoodRequestHandler foodHandler;
    private WireMockServer wireMockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private String vegetarianMealsResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null,\"amount\":0.0,\"numberOfMeals\":10}]";

    private String localProducesResponse = "[{\"id\":0,\"createdDate\":null," +
        "\"lastModifiedDate\":null,\"amount\":0.0,\"numberOfMeals\":10}]";

    @BeforeAll
    static void turnOffJettyLog() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
    }

    @BeforeEach
    void startServer() {
        EntryRequestHandler.login(new LoginRequest("test", "123"));
        foodHandler = FoodRequestHandler.getInstance();
        foodHandler.setGeneralUrl(host);

        wireMockServer = new WireMockServer(port);
        WireMock.configureFor("localhost", port);
        wireMockServer.start();
    }

    @AfterEach
    void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void updateLocalProductByIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        LocalProduce product = new LocalProduce();
        product.setNumberOfMeals(1);
        String productString = mapper.writeValueAsString(product);
        long id = 3;


        stubFor(put(urlEqualTo(productUrl + id))
                .withRequestBody(containing(productString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = foodHandler.updateLocProdById(product, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void updateVegetarianMealByIdTest() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        VegetarianMeal meal = new VegetarianMeal();
        meal.setNumberOfMeals(1);
        String mealString = mapper.writeValueAsString(meal);
        long id = 3;

        stubFor(put(urlEqualTo(mealUrl + id))
                .withRequestBody(containing(mealString))
                .willReturn(aResponse()
                        .withStatus(204)
                        .withHeader("Content-Type", "application/json")
                )
        );

        int response = foodHandler.updateVegMealById(meal, id).getStatusCodeValue();
        assertEquals(204, response);
    }

    @Test
    void getVegetarianMealsTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(vegetarianMealsResponse)
            )
        );

        String response = mapper.writeValueAsString(foodHandler.getUserVegetarianMeals());
        assertEquals(vegetarianMealsResponse, response);
    }

    @Test
    void getLocalProducesTest() throws JsonProcessingException {
        stubFor(get(urlPathEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(localProducesResponse)
            )
        );

        String response = mapper.writeValueAsString(foodHandler.getUserLocalProduces());
        assertEquals(localProducesResponse, response);
    }

    @Test
    void sendVegetarianMealTest() throws JsonProcessingException {
        VegetarianMeal meal = new VegetarianMeal();
        meal.setNumberOfMeals(1);
        String mealString = mapper.writeValueAsString(meal);

        stubFor(post(urlEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(mealString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        int response = foodHandler.sendVegetarianMeal(meal).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void sendLocalProduceTest() throws JsonProcessingException {
        LocalProduce product = new LocalProduce();
        product.setNumberOfMeals(1);
        String productString = mapper.writeValueAsString(product);

        stubFor(post(urlEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .withRequestBody(containing(productString))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
            )
        );

        int response = foodHandler.sendLocalProduce(product).getStatusCodeValue();
        assertEquals(201, response);
    }

    @Test
    void deleteLocProdByIdTest() {
        LocalProduce product = new LocalProduce();
        long id = 3;

        stubFor(delete(urlEqualTo(productUrl + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(204)
            )
        );

        ResponseEntity response = foodHandler.deleteLocProdById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void deleteVegMealByIdTest() {
        VegetarianMeal meal = new VegetarianMeal();
        long id = 3;

        stubFor(delete(urlEqualTo(mealUrl + id))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(204)
                .withHeader("Content-Type", "application/json")
            )
        );

        ResponseEntity response = foodHandler.deleteVegMealById(id);
        assertEquals("204 NO_CONTENT", response.getStatusCode().toString());
    }

    @Test
    void getFoodHabitTest() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            JsonProcessingException {
        stubFor(get(urlEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(localProducesResponse)
            )
        );

        stubFor(get(urlEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(vegetarianMealsResponse)
            )
        );

        Method method = FoodRequestHandler.class.getDeclaredMethod("getFoodHabit", String.class);
        method.setAccessible(true);

        ArrayList product = (ArrayList) method.invoke(foodHandler, productUrl);
        ArrayList meal = (ArrayList) method.invoke(foodHandler, mealUrl);

        String productString = mapper.writeValueAsString(product);
        String mealString = mapper.writeValueAsString(meal);


        assertEquals(localProducesResponse, productString);
        assertEquals(vegetarianMealsResponse, mealString);
    }

    @Test
    void getFoodHabitTestException() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        String wrongVegetarianMealsResponse = "[{\"id\":[0,2],\"createdDate\":null," +
            "\"lastModifiedDate\":null,\"amount\":0.0,\"numberOfMeals\":10}]";

        String wrongLocalProducesResponse = "[{\"id\":[0,2],\"createdDate\":null," +
            "\"lastModifiedDate\":null,\"amount\":0.0,\"numberOfMeals\":10}]";

        stubFor(get(urlEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongLocalProducesResponse)
            )
        );

        stubFor(get(urlEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(wrongVegetarianMealsResponse)
            )
        );

        Method method = FoodRequestHandler.class.getDeclaredMethod("getFoodHabit", String.class);
        method.setAccessible(true);

        ArrayList product = (ArrayList) method.invoke(foodHandler, productUrl);
        ArrayList meal = (ArrayList) method.invoke(foodHandler, mealUrl);
        String productString = mapper.writeValueAsString(product);
        String mealString = mapper.writeValueAsString(meal);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, productString);
        assertEquals(expectedResString, mealString);
    }

    @Test
    void getFoodHabitTest_differentUrls() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(localProducesResponse)
            )
        );

        stubFor(get(urlEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(vegetarianMealsResponse)
            )
        );

        stubFor(get(urlEqualTo(mealUrl + "wrong"))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                )
        );

        stubFor(get(urlEqualTo(productUrl + "wrong"))
                .withHeader("Accept", equalTo("application/json, application/*+json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                )
        );

        Method method = FoodRequestHandler.class.getDeclaredMethod("getFoodHabit", String.class);
        method.setAccessible(true);

        ArrayList product = (ArrayList) method.invoke(foodHandler, productUrl + "/wrong");
        ArrayList meal = (ArrayList) method.invoke(foodHandler, mealUrl + "/wrong");
        String productString = mapper.writeValueAsString(product);
        String mealString = mapper.writeValueAsString(meal);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, productString);
        assertEquals(expectedResString, mealString);
    }

    @Test
    void getFoodHabitTest_empty() throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException,
        JsonProcessingException {
        stubFor(get(urlEqualTo(productUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        stubFor(get(urlEqualTo(mealUrl))
            .withHeader("Accept", equalTo("application/json, application/*+json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            )
        );

        Method method = FoodRequestHandler.class.getDeclaredMethod("getFoodHabit", String.class);
        method.setAccessible(true);

        ArrayList product = (ArrayList) method.invoke(foodHandler, productUrl);
        ArrayList meal = (ArrayList) method.invoke(foodHandler, mealUrl);
        String productString = mapper.writeValueAsString(product);
        String mealString = mapper.writeValueAsString(meal);

        ArrayList expectedRes = new ArrayList();
        String expectedResString = mapper.writeValueAsString(expectedRes);

        assertEquals(expectedResString, productString);
        assertEquals(expectedResString, mealString);
    }

    @Test
    void getInstance() {
        assertEquals(FoodRequestHandler.class, foodHandler.getClass());
    }

    @Test
    void getSetGeneralUrlTest() {
        foodHandler.setGeneralUrl("www.google.com");
        assertEquals("www.google.com", foodHandler.getGeneralUrl());
    }

    @Test
    void getLpUrlTest() {
        String lpUrl = foodHandler.getLpUrl();
        assertEquals(lpUrl, foodHandler.getLpUrl());
    }

    @Test
    void getVmUrlTest() {
        String vmUrl = foodHandler.getVmUrl();
        assertEquals(vmUrl, foodHandler.getVmUrl());
    }

    @Test
    void getSetAuthHeaderTest() {
        AuthHeader auth = new AuthHeader();
        foodHandler.setAuth(auth);
        assertEquals(auth, foodHandler.getAuth());
    }

    @Test
    void getSetRestTemplateTest() {
        RestTemplate rest = new RestTemplate();
        foodHandler.setRestTemplate(rest);
        assertEquals(rest, foodHandler.getRestTemplate());
    }

    @Test
    void getSetObjectMapperTest() {
        ObjectMapper mapper = new ObjectMapper();
        foodHandler.setObjectMapper(mapper);
        assertEquals(mapper, foodHandler.getObjectMapper());
    }

    @Test
    void getSetHabitHandlerTest() {
        HabitRequestHandler jojo = HabitRequestHandler.getInstance();
        foodHandler.setHabitHandler(jojo);
        assertEquals(jojo, foodHandler.getHabitHandler());
    }

}