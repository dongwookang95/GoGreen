package server.exception.habit.energy;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelNotFoundExceptionTest {

    @Test
    void getId() {
        SolarPanelNotFoundException test = new SolarPanelNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}