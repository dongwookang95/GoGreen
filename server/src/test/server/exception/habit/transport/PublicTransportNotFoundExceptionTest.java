package server.exception.habit.transport;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.exception.habit.food.VegetarianMealNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PublicTransportNotFoundExceptionTest {

    @Test
    void getId() {
        PublicTransportNotFoundException test = new PublicTransportNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}