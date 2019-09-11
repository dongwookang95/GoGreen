package backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Class defining attributes of a general habit.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Habit {

    private long id;
    private String createdDate;
    private String lastModifiedDate;
    private double amount;

    /**
     * Method to set values from a map's entry (GUI inputs).
     * @param pair Pair of values to be set
     * @throws NumberFormatException If any number conversion fails.
     */
    public void setAttributeFromPair(Map.Entry<String,String> pair) { }
}
