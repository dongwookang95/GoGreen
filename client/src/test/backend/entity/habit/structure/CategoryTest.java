package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    void setAttributesFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode info = null;
        try {
            String data = "{\"id\":\"1\",\"description\":\"Description.\",\"subCategories\":" + "[{\"id\":\"1\",\"description\":\"Description.\",\"attributes\":" + "[{\"id\":\"2\",\"description\":\"Description2.\"}]}]}";
            info = mapper.readTree(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Subcategory> list = new ArrayList<>();
        Subcategory subCat = new Subcategory();
        subCat.setId(1);
        subCat.setDescription("Description.");
        ArrayList<Attribute> listAtt = new ArrayList<>();
        Attribute att = new Attribute();
        att.setId(2);
        att.setDescription("Description2.");
        listAtt.add(att);
        subCat.setAttributesList(listAtt);
        list.add(subCat);

        Category test = new Category();
        test.setAttributesFromJson(info);
        assertEquals(1, test.getId());
        assertEquals("Description.", test.getDescription());
        assertEquals(list, test.getSubCatList());
    }

    @Test
    void getSetId() {
        Category test = new Category();
        test.setId(1);
        assertEquals(1, test.getId());
    }

    @Test
    void getSetDescription() {
        Category test = new Category();
        test.setDescription("Description.");
        assertEquals("Description.", test.getDescription());
    }

    @Test
    void getSetSubCatList() {
        ArrayList<Subcategory> list = new ArrayList<>();
        Subcategory subCat = new Subcategory();
        list.add(subCat);

        Category test = new Category();
        test.setSubCatList(list);

        assertEquals(list, test.getSubCatList());
    }

}