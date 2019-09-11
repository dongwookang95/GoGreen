package backend.entity.habit.structure;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class defining an attribute of a habit.
 */
@Setter
@Getter
@NoArgsConstructor
public class Attribute {
    /**
     * Id of the attribute.
     */
    private long id = 0;
    /**
     * Description of the attribute.
     */
    private String description = "";

    /**
     * Sets the field of attributes according to information from the database.
     *
     * @param fields Information from the database
     */
    public void setAttributesFromJson(JsonNode fields) {
        setId(fields.get("id").asLong());
        setDescription(fields.get("description").asText());
    }

    /**
     * Compares two objects.
     *
     * @param obj Object to be compared
     * @return True if obj is attribute and equal to this
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Attribute) {
            Attribute that = (Attribute) obj;
            boolean sameId = this.getId() == that.getId();
            String thatDesc = that.getDescription();
            boolean sameDesc = this.getDescription().equals(thatDesc);
            return sameId && sameDesc;
        }
        return false;
    }
}
