package backend.entity.habit.food;

import backend.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LocalProduceTest {

    LocalProduce test;

    @BeforeEach
    void setUp() {
        test = new LocalProduce();
    }

    @Test
    void getSetNumberOfMeals() {
        test.setNumberOfMeals(1);
        assertEquals(1, test.getNumberOfMeals());
    }

    @Test
    void setAttributesFromPair_numberOfMeals() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("NumberOfMeals", "10");
        test.setAttributeFromPair(entry);
        assertEquals(10, test.getNumberOfMeals());
    }

    @Test
    void setAttributesFromPair_nothing() {
        Map.Entry<String, String> entry =
            new AbstractMap.SimpleEntry<>("Amount", "10");
        test.setAttributeFromPair(entry);
        assertEquals( 0, test.getAmount());
    }
}