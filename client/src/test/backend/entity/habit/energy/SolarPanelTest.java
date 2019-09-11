package backend.entity.habit.energy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelTest {

    SolarPanel test;

    @BeforeEach
    void setUp() {
        test = new SolarPanel();
    }

    @Test
    void getSetHours() {
        test.setHours(10.0);
        assertEquals(10.0, test.getHours());
    }

    @Test
    void getSetWatt() {
        test.setWatt(1);
        assertEquals(1, test.getWatt());
    }

    @Test
    void getSetNumberOfLocalPanels() {
        test.setNumberOfSolarPanels(1);
        assertEquals(1, test.getNumberOfSolarPanels());
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
    void setAttributeFromPair_Watt() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Watt", "10");
        test.setAttributeFromPair(entry);
        assertEquals(10, test.getWatt());
    }

    @Test
    void setAttributeFromPair_WattException() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Watt", "10.0");
        boolean thrown = false;
        try {
            test.setAttributeFromPair(entry);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    void setAttributeFromPair_numberOfSolarPanels() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("NumberOfSolarPanels", "10");
        test.setAttributeFromPair(entry);
        assertEquals(10, test.getNumberOfSolarPanels());
    }

    @Test
    void setAttributeFromPair_NumberOfSolarPanelsException() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("NumberOfSolarPanels", "this is not a number");
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
        assertEquals(0, test.getWatt());
        assertEquals(0, test.getNumberOfSolarPanels());
    }

}