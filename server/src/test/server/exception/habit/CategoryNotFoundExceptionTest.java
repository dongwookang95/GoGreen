package server.exception.habit;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import server.exception.habit.transport.TravelByBikeNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CategoryNotFoundExceptionTest {

    @Test
    void getId() {
        CategoryNotFoundException test = new CategoryNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}