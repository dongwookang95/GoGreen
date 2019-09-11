package server.controller.habit.category;

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
import server.entity.user.User;
import server.repository.habit.CategoryRepository;
import server.repository.habit.SubCategoryRepository;
import server.service.user.UserService;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EnergyControllerTest {

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

    private SolarPanelForm solarPanel = SolarPanelForm.builder().hours(1).build();
    private LowerTemperatureForm lowerTemperature = LowerTemperatureForm.builder().degrees(2).build();

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
        userBob.setSolarPanels(new HashSet<>());
        userBob.setLowerTemperatures(new HashSet<>());
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
    void getLowerTemperatureForUser() throws Exception {
        mockMvc.perform(get("/energy/lower_temperature")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getSolarPanels() throws Exception {
        mockMvc.perform(get("/energy/solar_panel")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addLowerTemperature() throws Exception {
        setUpCategories();
        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void addSolarPanel() throws Exception {
        setUpCategories();
        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void updateLowerTemperature() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("lowerTemperatures");
        JSONObject lowerTemperatureJSON = (JSONObject) list.get(0);
        Long id = lowerTemperatureJSON.getLong("id");

        mockMvc.perform(put("/energy/lower_temperature/" + id)
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void updateLowerTemperatureForbidden() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("lowerTemperatures");
        JSONObject lowerTemperatureJSON = (JSONObject) list.get(0);
        Long id = lowerTemperatureJSON.getLong("id");

        mockMvc.perform(put("/energy/lower_temperature/" + id)
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void updateSolarPanel() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("solarPanels");
        JSONObject solarPanelJSON = (JSONObject) list.get(0);
        Long id = solarPanelJSON.getLong("id");

        mockMvc.perform(put("/energy/solar_panel/" + id)
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void updateSolarPanelForbidden() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("solarPanels");
        JSONObject solarPanelJSON = (JSONObject) list.get(0);
        Long id = solarPanelJSON.getLong("id");

        mockMvc.perform(put("/energy/solar_panel/" + id)
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void deleteLowerTemperature() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtAlice))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("lowerTemperatures");
        JSONObject lowerTemperatureJSON = (JSONObject) list.get(0);
        Long id = lowerTemperatureJSON.getLong("id");

        mockMvc.perform(delete("/energy/lower_temperature/" + id)
                .header("Authorization", jwtAlice))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteLowerTemperatureForbidden() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/lower_temperature")
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lowerTemperature)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtAlice))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("lowerTemperatures");
        JSONObject lowerTemperatureJSON = (JSONObject) list.get(0);
        Long id = lowerTemperatureJSON.getLong("id");

        mockMvc.perform(delete("/energy/lower_temperature/" + id)
                .header("Authorization", jwtBob))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void deleteSolarPanel() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtAlice))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("solarPanels");
        JSONObject solarPanelJSON = (JSONObject) list.get(0);
        Long id = solarPanelJSON.getLong("id");


        mockMvc.perform(delete("/energy/solar_panel/" + id)
                .header("Authorization", jwtAlice))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteSolarPanelForbidden() throws Exception {
        setUpCategories();

        mockMvc.perform(post("/energy/solar_panel")
                .header("Authorization", jwtAlice)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solarPanel)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult result = mockMvc.perform(get("/user/me")
                .header("Authorization", jwtAlice))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
        JSONArray list = jsonObject.getJSONArray("solarPanels");
        JSONObject solarPanelJSON = (JSONObject) list.get(0);
        Long id = solarPanelJSON.getLong("id");


        mockMvc.perform(delete("/energy/solar_panel/" + id)
                .header("Authorization", jwtBob))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}