package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubcategoryTest {

    @Test
    void setAttributesFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode info = null;
        try {
            String data = "{\"id\":\"1\",\"description\":\"Description.\",\"attributes\":" + "[{\"id\":\"2\",\"description\":\"Description2.\"}]}";
            info = mapper.readTree(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        att.setId(2);
        att.setDescription("Description2.");
        list.add(att);

        Subcategory test = new Subcategory();
        test.setAttributesFromJson(info);
        assertEquals(1, test.getId());
        assertEquals("Description.", test.getDescription());
        assertEquals(list, test.getAttributesList());
    }

    @Test
    void getSetId() {
        Subcategory test = new Subcategory();
        test.setId(1);
        assertEquals(1, test.getId());
    }

    @Test
    void getSetDescription() {
        Subcategory test = new Subcategory();
        test.setDescription("Description.");
        assertEquals("Description.", test.getDescription());
    }

    @Test
    void getSetAttributesList() {
        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        list.add(att);

        Subcategory test = new Subcategory();
        test.setAttributesList(list);

        assertEquals(list, test.getAttributesList());
    }

    @Test
    void equals_fail_notSubCat() {
        Subcategory test = new Subcategory();
        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        list.add(att);
        test.setAttributesList(list);

        assertNotEquals(test, new Category());
    }

    @Test
    void equals_fail_diffSize() {
        Subcategory test = new Subcategory();
        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        list.add(att);
        test.setAttributesList(list);

        Subcategory diff = new Subcategory();
        ArrayList<Attribute> list2 = new ArrayList<>();
        Attribute att2 = new Attribute();
        list2.add(att);
        list2.add(att2);
        diff.setAttributesList(list2);

        assertNotEquals(test, diff);
    }

    @Test
    void equals_fail_diffId() {
        Subcategory test = new Subcategory();
        test.setId(1);
        Subcategory diff = new Subcategory();
        diff.setId(2);
        assertNotEquals(test, diff);
    }

    @Test
    void equals_fail_diffDesc() {
        Subcategory test = new Subcategory();
        test.setDescription("Description1.");
        Subcategory diff = new Subcategory();
        diff.setDescription("Description2.");
        assertNotEquals(test, diff);
    }

    @Test
    void equals_fail_secondAttDiff() {
        Attribute att = new Attribute();
        att.setId(0);
        Attribute att1 = new Attribute();
        att1.setId(1);
        Attribute att2 = new Attribute();
        att2.setId(2);

        ArrayList<Attribute> list1 = new ArrayList<>();
        list1.add(att);
        list1.add(att1);
        ArrayList<Attribute> list2 = new ArrayList<>();
        list2.add(att);
        list2.add(att2);

        Subcategory test = new Subcategory();
        test.setAttributesList(list1);

        Subcategory diff = new Subcategory();
        diff.setAttributesList(list2);

        assertNotEquals(test, diff);
    }

    @Test
    void equals_success_same() {
        Subcategory test = new Subcategory();
        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        list.add(att);
        test.setAttributesList(list);

        assertEquals(test, test);
    }

    @Test
    void equals_success_diff() {
        ArrayList<Attribute> list = new ArrayList<>();
        Attribute att = new Attribute();
        list.add(att);

        Subcategory test = new Subcategory();
        test.setAttributesList(list);

        Subcategory same = new Subcategory();
        same.setAttributesList(list);

        assertEquals(test, same);
    }

}