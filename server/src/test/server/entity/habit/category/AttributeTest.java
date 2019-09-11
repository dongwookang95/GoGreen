package server.entity.habit.category;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;


class AttributeTest {

    @Test
    void setDescription() {
        final Attribute test = new Attribute();

        test.setDescription("test");
        String result = test.getDescription();

        Assert.assertEquals(test.getDescription(), result);


    }
  /*  public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }*/
    @Test
    void setType() {
        final Attribute test = new Attribute();

        test.setType("test");
        String result = test.getType();
        Assert.assertEquals(test.getType(), result);
    }

    @Test
    void builder() {

        Attribute test = Attribute.builder().description("test").type("test").build();




        Assert.assertEquals(test.getType(), "test");
        Assert.assertEquals(test.getDescription(), "test");
        Assert.assertEquals(test.toString(), "test");

    }



    @Test
    void getDescription() {
        final Attribute test = new Attribute();

        String result = test.getDescription();
        Assert.assertEquals(test.getDescription() , result);
    }

    @Test
    void getType() {
        final Attribute test = new Attribute();

        String result = test.getType();
        Assert.assertEquals(test.getType(), result);
    }

    @Test
    void testToString() {
        Attribute test = Attribute.builder().description("test").type("test").build();
        Assert.assertEquals(test.toString(), "test");
    }
}