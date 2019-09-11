package server.message;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HabitOverviewTest {

    @Test
    void getDescription() {
        HabitOverview test = new HabitOverview("test", 123);
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);
    }

    @Test
    void getTotal() {
        HabitOverview test = new HabitOverview("test", 123);
        Double result = test.getTotal();


    }
}