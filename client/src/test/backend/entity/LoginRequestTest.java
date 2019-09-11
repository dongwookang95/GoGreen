package backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginRequestTest {

    @Test
    void getSetUsername() {
        LoginRequest test = new LoginRequest("Andy", "123");
        test.setUsername("Jan");
        assertEquals("Jan", test.getUsername());
    }

    @Test
    void getSetetPassword() {
        LoginRequest test = new LoginRequest("Andy", "123");
        test.setPassword("abc");
        assertEquals("abc", test.getPassword());
    }
}