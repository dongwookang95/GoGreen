package backend.entity.habit.transport;

import backend.entity.user.User;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PublicTransportTest {

    PublicTransport test;

    @BeforeEach
    void setUp() {
        test = new PublicTransport();
    }

    @Test
    void getSetKilometers() {
        test.setKilometers(20);
        assertEquals(20, test.getKilometers());
    }

    @Test
    void getSetTransportTypeInstead() {
        test.setTransportTypeInstead("CAR");
        assertEquals("CAR", test.getTransportTypeInstead());
    }

    @Test
    void getSetTransportTypeActual() {
        test.setTransportTypeInstead("BUS");
        assertEquals("BUS", test.getTransportTypeInstead());
    }


    @Test
    void setAttributeFromPair_Kilometers() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("Kilometers", "20");
        test.setAttributeFromPair(entry);
        assertEquals(20, test.getKilometers());
    }

    @Test
    void setAttributesFromPair_TransportTypeInstead() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "CAR");
        test.setAttributeFromPair(entry);
        assertEquals( "CAR", test.getTransportTypeInstead());
    }

    @Test
    void setAttributesFromPair_TransportTypeActual() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("TransportTypeActual", "BUS");
        test.setAttributeFromPair(entry);
        assertEquals( "BUS", test.getTransportTypeActual());
    }

    @Test
    void setAttributesFromPair_Default() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Something else", "10");
        test.setAttributeFromPair(entry);
        assertEquals(0.0, test.getKilometers());
        assertNull(test.getTransportTypeInstead());
        assertNull(test.getTransportTypeActual());
    }

    @Test
    void setAttributesFromPair_Null() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>(null, "10");
        boolean thrown = false;
        try {
            test.setAttributeFromPair(entry);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}