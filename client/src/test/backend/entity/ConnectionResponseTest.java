package backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionResponseTest {

    @Test
    void getSetUsername() {
        ConnectionResponse test = new ConnectionResponse();
        test.setUsername("Andy");
        assertEquals("Andy", test.getUsername());
    }

    @Test
    void getSetToken() {
        ConnectionResponse test = new ConnectionResponse();
        test.setToken("abc123");
        assertEquals("abc123", test.getToken());
    }
}