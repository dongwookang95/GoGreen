package backend.entity.habit.food;

import backend.entity.Habit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Class defining a habit of buying local produce.
 */
@NoArgsConstructor
@Getter
@Setter
public class LocalProduce extends Habit {
    private int numberOfMeals;

    @Override
    public void setAttributeFromPair(Map.Entry<String,String> pair) throws NumberFormatException {
        if ("NumberOfMeals".equals(pair.getKey())) {
            int quantity = Integer.parseInt(pair.getValue());
            setNumberOfMeals(quantity);
        }
    }

}
