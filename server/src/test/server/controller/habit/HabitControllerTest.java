package server.controller.habit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;
import server.entity.habit.energy.subcategory.LowerTemperatureForm;
import server.entity.habit.energy.subcategory.SolarPanelForm;
import server.entity.habit.food.subcategory.LocalProduceForm;
import server.entity.habit.food.subcategory.VegetarianMealForm;
import server.entity.habit.transport.subcategory.PublicTransportForm;
import server.entity.habit.transport.subcategory.TravelByBikeForm;
import server.entity.user.User;
import server.repository.habit.CategoryRepository;
import server.repository.habit.SubCategoryRepository;
import server.service.user.UserService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class HabitControllerTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User userBob;
    private User userAlice;

    private String jwtBob;
    private String jwtAlice;

    private LocalProduceForm localProduce = LocalProduceForm.builder().numberOfMeals(1).build();
    private VegetarianMealForm vegetarianMeal = VegetarianMealForm.builder().numberOfMeals(2).build();
    private SolarPanelForm solarPanel = SolarPanelForm.builder().hours(3).build();
    private LowerTemperatureForm lowerTemperature = LowerTemperatureForm.builder().degrees(4).build();
    private PublicTransportForm publicTransport = PublicTransportForm.builder().kilometers(5).transportTypeInstead("Bus").transportTypeActual("Metro").build();
    private TravelByBikeForm travelByBike = TravelByBikeForm.builder().kilometers(6).transportTypeInstead("Bus").build();

    private Category categoryFood = Category.builder().description("Food").build();
    private Category categoryTransport = Category.builder().description("Transport").build();
    private Category categoryEnergy = Category.builder().description("Energy").build();

    private SubCategory categoryLocalProduce = SubCategory.builder().description("Solar Panel").build();
    private SubCategory categoryVegetarianMeal = SubCategory.builder().description("Lower Temperature").build();
    private SubCategory categoryPublicTransport = SubCategory.builder().description("Solar Panel").build();
    private SubCategory categoryTravelByBike = SubCategory.builder().description("Lower Temperature").build();
    private SubCategory categorySolarPanel = SubCategory.builder().description("Solar Panel").build();
    private SubCategory categoryLowerTemperature = SubCategory.builder().description("Lower Temperature").build();

    @BeforeEach
    void init() throws Exception {
        userBob = User.builder()
                .username("Bob")
                .password(passwordEncoder.encode("123"))
                .role("ROLE_USER")
                .enabled(true)
                .build();

        userAlice = User.builder()
                .username("Alice")
                .password(passwordEncoder.encode("456"))
                .role("ROLE_ADMIN")
                .enabled(true)
                .build();

        userService.createUser(userBob);
        userService.createUser(userAlice);

        jwtBob = login("Bob", "123");
        jwtAlice = login("Alice", "456");
    }

    @AfterEach
    void cleanUp() {
        userBob.setLocalProduces(new HashSet<>());
        userBob.setVegetarianMeals(new HashSet<>());
        userBob.setPublicTransports(new HashSet<>());
        userBob.setTravelByBikes(new HashSet<>());
        userBob.setSolarPanels(new HashSet<>());
        userBob.setLowerTemperatures(new HashSet<>());
        userAlice.setLocalProduces(new HashSet<>());
        userAlice.setVegetarianMeals(new HashSet<>());
        userAlice.setPublicTransports(new HashSet<>());
        userAlice.setTravelByBikes(new HashSet<>());
        userAlice.setSolarPanels(new HashSet<>());
        userAlice.setLowerTemperatures(new HashSet<>());

        userService.deleteUser(userBob);
        userService.deleteUser(userAlice);
    }

    void setUpCategories(){
        categoryRepository.save(categoryFood);
        categoryRepository.save(categoryTransport);
        categoryRepository.save(categoryEnergy);

        subCategoryRepository.save(categoryLocalProduce);
        subCategoryRepository.save(categoryVegetarianMeal);
        subCategoryRepository.save(categoryPublicTransport);
        subCategoryRepository.save(categoryTravelByBike);
        subCategoryRepository.save(categorySolarPanel);
        subCategoryRepository.save(categoryLowerTemperature);
    }

    String login(String name, String password) throws Exception {
        MvcResult result = this.mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"" + name + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject jsonObj = new JSONObject(content);
        return "Bearer " + jsonObj.getString("token");
    }

    @Test
    void getAllCategories() throws Exception {
        setUpCategories();

        mockMvc.perform(get("/habit/category/all")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getOverviewForUser() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/transport/travel_by_bike")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(travelByBike)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/transport/public_transport")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicTransport)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/food/vegetarian_meal")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vegetarianMeal)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(post("/food/local_produce")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(localProduce)))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(get("/habit/overview/Bob")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();

    }
}