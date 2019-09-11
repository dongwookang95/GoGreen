package backend.entity.habit.transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TravelByBikeTest {

    TravelByBike test;

    @BeforeEach
    void setUp() {
        test = new TravelByBike();
    }

    @Test
    void getSetKilometers() {
        test.setKilometers(20);
        assertEquals(20, test.getKilometers());
    }

    @Test
    void getSetTransportTypeInstead() {
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
            new AbstractMap.SimpleEntry<>("TransportTypeInstead", "BUS");
        test.setAttributeFromPair(entry);
        assertEquals( "BUS", test.getTransportTypeInstead());
    }

    @Test
    void setAttributesFromPair_Default() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<String, String>("Something else", "10");
        test.setAttributeFromPair(entry);
        assertEquals(0.0, test.getKilometers());
        assertEquals(null, test.getTransportTypeInstead());
    }

}