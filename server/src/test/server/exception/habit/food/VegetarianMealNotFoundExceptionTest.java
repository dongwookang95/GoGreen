package server.exception.habit.food;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VegetarianMealNotFoundExceptionTest {

    @Test
    void getId() {
        VegetarianMealNotFoundException test = new VegetarianMealNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}