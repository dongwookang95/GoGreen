package backend.entity.user;

import backend.entity.habit.food.LocalProduce;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getSetUsername() {
        User test = new User();
        test.setUsername("Jan");
        assertEquals("Jan", test.getUsername());
    }

    @Test
    void equals_failDiff() {
        User u1 = new User("andy");
        User u2 = new User("jan");
        assertNotEquals(u1, u2);
    }

    @Test
    void equals_failClass() {
        LocalProduce lp = new LocalProduce();
        User u1 = new User("andy");
        assertNotEquals(u1, lp);
    }

    @Test
    void equals_successDiff() {
        User u1 = new User("andy");
        User u2 = new User("andy");
        assertEquals(u1, u2);
        assertEquals(u1, u1);
    }

    @Test
    void equals_bothNull() {
        User u1 = new User();
        User u2 = new User();
        assertEquals(u1, u2);
    }

    @Test
    void equalsNullUsernames() {
        User u1 = new User();
        User u2 = new User("andy");
        assertNotEquals(u2, u1);
    }

}