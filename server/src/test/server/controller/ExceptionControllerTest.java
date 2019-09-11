package server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.controller.user.UserController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
class ExceptionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserController userController;




//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new ExceptionController(), achievementController, userController)
//                .build();
//
//    }

    @Test
    void userNotFound() {

    }

    @Test
    void userGroupNotFound() {
    }

    @Test
    void localProduceNotFound() {
    }

    @Test
    void vegetarianMealNotFound() {
    }

    @Test
    void solarPanelNotFound() {
    }

    @Test
    void loweringTemperatureNotFound() {
    }

    @Test
    void publicTransportNotFound() {
    }

    @Test
    void travelByBikeMealNotFound() {
    }

    @Test
    void databaseException() {
    }

    @Test
    void invalidJwtAuthentication() {
    }
}