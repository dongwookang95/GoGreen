package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AttributeTest {

    @Test
    void setAttributesFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode info = null;
        try {
            info = mapper.readTree("{\"id\":\"1\",\"description\":\"Description.\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Attribute test = new Attribute();
        test.setAttributesFromJson(info);
        assertEquals(1, test.getId());
        assertEquals("Description.", test.getDescription());
    }

    @Test
    void getSetId() {
        Attribute test = new Attribute();
        test.setId(1);
        assertEquals(1, test.getId());
    }

    @Test
    void getSetDescription() {
        Attribute test = new Attribute();
        test.setDescription("Description.");
        assertEquals("Description.", test.getDescription());
    }

    @Test
    void equals_fail_notAtt() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        assertNotEquals(test, new Category());
    }

    @Test
    void equals_fail_diff1() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        Attribute diff = new Attribute();
        diff.setId(1);
        diff.setDescription("Description2.");
        assertNotEquals(test, diff);
    }

    @Test
    void equals_fail_diff2() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        Attribute diff = new Attribute();
        diff.setId(2);
        diff.setDescription("Description.");
        assertNotEquals(test, diff);
    }

    @Test
    void equals_fail_diff3() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        Attribute diff = new Attribute();
        diff.setId(2);
        diff.setDescription("Description2.");
        assertNotEquals(test, diff);
    }

    @Test
    void equals_success_same() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        assertEquals(test, test);
    }

    @Test
    void equals_success_diff() {
        Attribute test = new Attribute();
        test.setId(1);
        test.setDescription("Description.");
        Attribute same = new Attribute();
        same.setId(1);
        same.setDescription("Description.");
        assertEquals(test, same);
    }

}