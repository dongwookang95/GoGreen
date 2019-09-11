package backend.entity;

import backend.entity.habit.structure.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadgeTest {

    Badge test;

    @BeforeEach
    void setUp() {
        test = new Badge();
    }

    @Test
    void getSetName() {
        test.setName("name");
        assertEquals("name", test.getName());
    }

    @Test
    void getSetPath() {
        Category cat = new Category();
        cat.setDescription("Hello");
        test.setName("name");
        test.setPath(cat);
        assertEquals("hello_name", test.getPath());
    }
}