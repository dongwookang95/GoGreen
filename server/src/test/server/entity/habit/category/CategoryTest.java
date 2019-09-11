package server.entity.habit.category;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void setDescription() {
        Category test = new Category();
        test.setDescription("test");
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);
    }

    @Test
    void setSubCategories() {
        Category test = new Category();
        Set <SubCategory> testSub = new HashSet<>();
        test.setSubCategories(testSub);
        Set result = test.getSubCategories();


        Assert.assertEquals(test.getSubCategories(), result);
    }


    @Test
    void builder() {
        Set <SubCategory> testSub = new HashSet<>();
        Category test = Category.builder().description("test").subCategories(testSub).build();
        String result = test.toString();


        Assert.assertEquals(test.getDescription(), "test");
        Assert.assertEquals(test.getSubCategories(), testSub);
        Assert.assertEquals(test.toString(), result);


    }

    @Test
    void getDescription() {
        Category test = new Category();
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);
    }

    @Test
    void getSubCategories() {
        Category test = new Category();
        Set result = test.getSubCategories();

        Assert.assertEquals(test.getSubCategories(), result);
    }
    @Test
    void testToString() {
        Category test = Category.builder().description("test").build();
        Assert.assertEquals(test.toString(), "test");
    }
}