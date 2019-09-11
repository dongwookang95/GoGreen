package server.exception.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void getUsername() {
        UserNotFoundException test = new UserNotFoundException("test");
        String result = test.getUsername();

        Assert.assertEquals(test.getUsername(), result);
    }
}