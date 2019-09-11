package server.exception.habit;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubCategoryNotFoundExceptionTest {

    @Test
    void getId() {
        SubCategoryNotFoundException test = new SubCategoryNotFoundException(12L);
        Long result = test.getId();

        Assert.assertEquals(test.getId(), result);
    }
}