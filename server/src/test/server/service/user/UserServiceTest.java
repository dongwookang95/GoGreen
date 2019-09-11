package server.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import server.entity.user.User;
import server.exception.user.UserNotFoundException;
import server.repository.user.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        User user = User.builder().username("Bob").password("123").build();

        when(userRepository.save(any(User.class))).thenReturn(user);
        User created = userService.createUser(user);

        assertEquals(user.getUsername(), created.getUsername());
        assertEquals(user.getPassword(), created.getPassword());
    }

    @Test
    void findAll() {
        User userBob = User.builder().username("Bob").password("123").build();
        User userAlice = User.builder().username("Alice").password("123").build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(userBob, userAlice));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void findByUsername() {
        User user = User.builder().username("Bob").password("123").build();

        when(userRepository.findById("Bob")).thenReturn(Optional.ofNullable(user));
        User found = userService.findByUsername("Bob");

        assertEquals(user.getUsername(), found.getUsername());
        assertEquals(user.getPassword(), found.getPassword());
    }

    @Test
    void findByUsernameNotThere() {
        when(userRepository.findById(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("Test"));
    }

    @Test
    void deleteUser() {
        User user = User.builder().username("Bob").password("456").build();

        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser() {
        User user = User.builder().username("Bob").password("456").build();

        when(userRepository.save(user)).thenReturn(user);
        userService.updateUser(user);

        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserRepository() {
    }

    @Test
    void setUserRepository() {
    }

    @Test
    void getUserGroupRepository() {
    }

    @Test
    void setUserGroupRepository() {
    }
}