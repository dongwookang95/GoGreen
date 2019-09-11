package server.exception.habit.energy;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LowerTemperatureNotFoundExceptionTest {

    @Test
    void getId() {
        LowerTemperatureNotFoundException test = new LowerTemperatureNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}