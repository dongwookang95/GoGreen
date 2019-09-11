package server.entity.habit.category;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


class SubCategoryTest {

    @Test
    void setDescription() {
        SubCategory test = new SubCategory();
        test.setDescription("test");
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);
    }

    @Test
    void setCategory() {
        SubCategory test = new SubCategory();
        Category categoryTested= new Category();
        test.setCategory(categoryTested);

        Category result = test.getCategory();
        Assert.assertEquals(test.getCategory(), result);

    }

    @Test
    void setAttributes() {
        SubCategory test = new SubCategory();
        Set<Attribute> AttributeTested = new HashSet<>();
        test.setAttributes(AttributeTested);
        Set result = test.getAttributes();

        Assert.assertEquals(test.getAttributes(), result);
    }

    @Test
    void getDescription() {
        SubCategory test = new SubCategory();
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);
    }

    @Test
    void getCategory() {
        SubCategory test = new SubCategory();
        Category result = test.getCategory();

        Assert.assertEquals(test.getCategory(), result);
    }

    @Test
    void getAttributes() {
        SubCategory test = new SubCategory();
        Set result = test.getAttributes();

        Assert.assertEquals(test.getAttributes(), result);
    }
    @Test
    void builder(){
        Set<Attribute> tested = new HashSet<>();
        Category tested2 = new Category();
        SubCategory test = SubCategory.builder().description("test")
                .attributes(tested).category(tested2).build();
        String result = test.toString();

        Assert.assertEquals(test.getCategory(), tested2);
        Assert.assertEquals(test.getDescription(), "test");
        Assert.assertEquals(test.toString(), result);
    }

    @Test
    void testToString() {
        SubCategory test = SubCategory.builder().description("test").build();
        Assert.assertEquals(test.toString(), "test");
    }
}