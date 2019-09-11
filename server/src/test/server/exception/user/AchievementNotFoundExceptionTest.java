package server.exception.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AchievementNotFoundExceptionTest {

    @Test
    void getId() {
        AchievementNotFoundException test = new AchievementNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);

    }
}