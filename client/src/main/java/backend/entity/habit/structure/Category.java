package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Class defining a category.
 */
@Setter
@Getter
@NoArgsConstructor
public class Category {
    /**
     * Id of the category.
     */
    private long id = 0;
    /**
     * Description of the category.
     */
    private String description = "";
    /**
     * List with all the subcategories of this category.
     */
    private ArrayList<Subcategory> subCatList = new ArrayList<>();

    /**
     * Setting all attributes of category from database information.
     *
     * @param category JsonNode containing information from the database
     */
    public void setAttributesFromJson(JsonNode category) {
        setId(category.get("id").asLong());
        setDescription(category.get("description").asText());

        JsonNode subcategories = category.get("subCategories");
        for (int j = 0; j < subcategories.size(); j++) {
            JsonNode subCategory = subcategories.get(j);
            Subcategory subCat = new Subcategory();
            subCat.setAttributesFromJson(subCategory);
            subCatList.add(subCat);
        }
    }
}
