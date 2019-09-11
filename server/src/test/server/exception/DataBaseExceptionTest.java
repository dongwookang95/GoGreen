package server.exception;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DataBaseExceptionTest {

    @Test
    void getMessage() {
        DataBaseException test = new DataBaseException("test");
        String result = test.getMessage();

        Assert.assertEquals(test.getMessage(), result);
    }
}