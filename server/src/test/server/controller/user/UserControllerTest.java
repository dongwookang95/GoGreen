package server.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.entity.habit.category.Category;
import server.entity.user.Achievement;
import server.entity.user.User;
import server.entity.user.UserForm;
import server.repository.habit.CategoryRepository;
import server.repository.user.AchievementRepository;
import server.repository.user.UserRepository;
import server.security.CustomUserDetailsService;
import server.security.jwt.JwtTokenProvider;
import server.service.user.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User userBob;
    private User userAlice;
    private User userJohn;
    private User userMark;
    private User userHuts;
    private User userMark1;
    private User userHuts1;
    private User userMark2;
    private User userHuts2;
    private User userMark3;
    private User userHuts3;

    private String jwtBob;
    private String jwtAlice;
    private String jwtMark;
    private String jwtHuts;

    @BeforeEach
    void init() throws Exception {
        userBob = User.builder()
                .username("Bob")
                .password(passwordEncoder.encode("123"))
                .role("ROLE_USER")
                .requests(new HashSet<>())
                .invites(new HashSet<>())
                .friends(new HashSet<>())
                .enabled(true)
                .build();

        userAlice = User.builder()
                .username("Alice")
                .password(passwordEncoder.encode("456"))
                .role("ROLE_ADMIN")
                .requests(new HashSet<>())
                .invites(new HashSet<>())
                .friends(new HashSet<>())
                .enabled(true)
                .build();

        userJohn = User.builder()
                .username("John")
                .password(passwordEncoder.encode("789"))
                .role("ROLE_USER")
                .requests(new HashSet<>())
                .invites(new HashSet<>())
                .friends(new HashSet<>())
                .enabled(true)
                .build();

        userService.createUser(userBob);
        userService.createUser(userAlice);
        userService.createUser(userJohn);

        jwtBob = login("Bob", "123");
        jwtAlice = login("Alice", "456");
    }

    @AfterEach
    void cleanUp() {
        userService.deleteUser(userBob);
        userService.deleteUser(userAlice);
        userService.deleteUser(userJohn);
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
    void allUsers() throws Exception {
        mockMvc.perform(get("/user/all")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void currentUser() throws Exception {
        mockMvc.perform(get("/user/me")
                .header("Authorization", jwtBob))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findByUsername() throws Exception {
        mockMvc.perform(get("/user/Bob")
                .header("Authorization", jwtAlice))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void registerUser() throws Exception {
        UserForm userForm = UserForm.builder().username("Mart").password("123").build();

        categoryRepository.save(Category.builder().description("Food").build());
        categoryRepository.save(Category.builder().description("Transport").build());
        categoryRepository.save(Category.builder().description("Energy").build());

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void login() throws Exception {
        UserForm userForm = UserForm.builder().username("Bob").password("123").build();

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginWrong() throws Exception {
        UserForm userForm = UserForm.builder().username("Wrong").password("123").build();

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateUser() throws Exception {
        UserForm userForm = UserForm.builder().username("Bob").password("123").build();

        mockMvc.perform(put("/user")
                .header("Authorization", jwtBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void inviteFriend() throws Exception {
        mockMvc.perform(put("/user/invite/John")
                .header("Authorization", jwtBob))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void inviteFriendAlreadyFriend() throws Exception {
        userMark3 = User.builder().username("Mark3").password(passwordEncoder.encode("abc")).role("ROLE_USER").enabled(true).build();
        userHuts3 = User.builder().username("Huts3").password(passwordEncoder.encode("def")).role("ROLE_USER").enabled(true).build();
        userService.createUser(userMark3);
        userService.createUser(userHuts3);
        jwtMark = login("Mark3", "abc");
        jwtHuts = login("Huts3", "def");

        mockMvc.perform(put("/user/invite/Huts3")
                .header("Authorization", jwtMark))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(put("/user/accept/Mark3")
                .header("Authorization", jwtHuts))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(put("/user/invite/Huts3")
                .header("Authorization", jwtMark))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void acceptFriend() throws Exception {
        userMark = User.builder().username("Mark").password(passwordEncoder.encode("abc")).role("ROLE_USER").enabled(true).build();
        userHuts = User.builder().username("Huts").password(passwordEncoder.encode("def")).role("ROLE_USER").enabled(true).build();
        userService.createUser(userMark);
        userService.createUser(userHuts);
        jwtMark = login("Mark", "abc");
        jwtHuts = login("Huts", "def");

        mockMvc.perform(put("/user/invite/Huts")
                .header("Authorization", jwtMark))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(put("/user/accept/Mark")
                .header("Authorization", jwtHuts))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void declineFriend() throws Exception {
        userMark1 = User.builder().username("Mark1").password(passwordEncoder.encode("abc")).role("ROLE_USER").enabled(true).build();
        userHuts1 = User.builder().username("Huts1").password(passwordEncoder.encode("def")).role("ROLE_USER").enabled(true).build();
        userService.createUser(userMark1);
        userService.createUser(userHuts1);
        jwtMark = login("Mark1", "abc");
        jwtHuts = login("Huts1", "def");

        mockMvc.perform(put("/user/invite/Huts1")
                .header("Authorization", jwtMark))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(put("/user/decline/Mark1")
                .header("Authorization", jwtHuts))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteUserByUsername() throws Exception {
        mockMvc.perform(delete("/user/Bob")
                .header("Authorization", jwtAlice))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void deleteFriend() throws Exception {
        userMark2 = User.builder().username("Mark2").password(passwordEncoder.encode("abc")).role("ROLE_USER").enabled(true).build();
        userHuts2 = User.builder().username("Huts2").password(passwordEncoder.encode("def")).role("ROLE_USER").enabled(true).build();
        userService.createUser(userMark2);
        userService.createUser(userHuts2);
        jwtMark = login("Mark2", "abc");
        jwtHuts = login("Huts2", "def");

        mockMvc.perform(put("/user/invite/Huts2")
                .header("Authorization", jwtMark))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(put("/user/accept/Mark2")
                .header("Authorization", jwtHuts))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(delete("/user/friend/Mark2")
                .header("Authorization", jwtHuts))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}