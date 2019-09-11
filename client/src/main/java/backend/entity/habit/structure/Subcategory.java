package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Class defining a subcategory.
 */
@Setter
@Getter
public class Subcategory {
    /**
     * Id of the subcategory.
     */
    private long id = 0;
    /**
     * Description of the subcategory.
     */
    private String description = "";
    /**
     * List of all the attributes of the subcategory.
     */
    private ArrayList<Attribute> attributesList = new ArrayList<>();

    /**
     * Setting all the fields of subcategory from database information.
     *
     * @param subcategory JsonNode from information in the database
     */
    public void setAttributesFromJson(JsonNode subcategory) {
        setId(subcategory.get("id").asLong());
        setDescription(subcategory.get("description").asText());

        JsonNode attribute = subcategory.get("attributes");
        for (int k = 0; k < attribute.size(); k++) {
            JsonNode fields = attribute.get(k);
            Attribute att = new Attribute();
            att.setAttributesFromJson(fields);
            attributesList.add(att);
        }
    }

    /**
     * Compares two objects, obj to this.
     *
     * @param obj Object to be compared to this
     * @return True if obj is Subcategory and equal to this
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Subcategory) {
            Subcategory that = (Subcategory) obj;
            ArrayList<Attribute> thisList = this.getAttributesList();
            ArrayList<Attribute> thatList = that.getAttributesList();
            if (thisList.size() == thatList.size()) {
                boolean sameAttList = true;
                for (int i = 0; i < thisList.size(); i++) {
                    if (!(thisList.get(i).equals(thatList.get(i)))) {
                        sameAttList = false;
                        break;
                    }
                }
                boolean sameId = this.getId() == that.getId();
                String thatDesc = that.getDescription();
                boolean sameDesc = this.getDescription().equals(thatDesc);
                return sameId && sameDesc && sameAttList;
            }
        }
        return false;
    }
}
