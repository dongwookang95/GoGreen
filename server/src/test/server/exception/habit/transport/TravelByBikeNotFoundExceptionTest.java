package server.exception.habit.transport;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TravelByBikeNotFoundExceptionTest {

    @Test
    void getId() {
        TravelByBikeNotFoundException test = new TravelByBikeNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}