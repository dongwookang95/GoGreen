package server.security;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import server.entity.user.User;
import server.repository.user.UserRepository;
import server.service.user.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    UserService userService;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService1;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    void loadUserByUsername() {
        User user = User.builder().username("test").password("test").build();
        when(userRepository.findById("test")).thenReturn(Optional.ofNullable(user));
        String result = new CustomUserDetailsService(userService).loadUserByUsername("test").getUsername();


        Assert.assertEquals(result, "test");


    }

}