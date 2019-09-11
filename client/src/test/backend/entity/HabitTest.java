package backend.entity;

import backend.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HabitTest {

    Habit test;

    @BeforeEach
    void setUp() {
        test = new Habit();
    }

    @Test
    void getSetAmount() {
        test.setAmount(10);
        assertEquals(10, test.getAmount());
    }

    @Test
    void getSetId() {
        test.setId(2);
        assertEquals(2, test.getId());
    }

    @Test
    void getSetCreatedDate() {
        test.setCreatedDate("03-03-0303");
        assertEquals("03-03-0303", test.getCreatedDate());
    }

    @Test
    void getSetLastModifiedDate() {
        test.setLastModifiedDate("03-03-0303");
        assertEquals("03-03-0303", test.getLastModifiedDate());
    }

}