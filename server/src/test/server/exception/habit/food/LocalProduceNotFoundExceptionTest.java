package server.exception.habit.food;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.exception.habit.energy.SolarPanelNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class LocalProduceNotFoundExceptionTest {

    @Test
    void getId() {
        LocalProduceNotFoundException test = new LocalProduceNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}