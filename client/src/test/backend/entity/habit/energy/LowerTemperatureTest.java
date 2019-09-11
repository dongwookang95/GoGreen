package backend.entity.habit.energy;

import backend.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LowerTemperatureTest {

    LowerTemperature test;

    @BeforeEach
    void setUp() {
        test = new LowerTemperature();
    }

    @Test
    void getSetHours() {
        test.setHours(10.0);
        assertEquals(10.0, test.getHours());
    }

    @Test
    void getSetDegrees() {
        test.setDegrees(10.0);
        assertEquals(10.0, test.getDegrees());
    }

    @Test
    void setAttributeFromPair_Hours() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Hours", "10.0");
        test.setAttributeFromPair(entry);
        assertEquals(10.0, test.getHours());
    }

    @Test
    void setAttributeFromPair_HoursException() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Hours", "this is not a number");
        boolean thrown = false;
        try {
            test.setAttributeFromPair(entry);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void setAttributeFromPair_Degrees() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Degrees", "20.0");
        test.setAttributeFromPair(entry);
        assertEquals(20.0, test.getDegrees());
    }

    @Test
    void setAttributeFromPair_DegreesException() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Degrees", "This is not a number");
        boolean thrown = false;
        try {
            test.setAttributeFromPair(entry);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void setAttributeFromPair_Default() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Something else", "10");
        test.setAttributeFromPair(entry);
        assertEquals(0.0, test.getHours());
        assertEquals(0.0, test.getDegrees());
    }
}