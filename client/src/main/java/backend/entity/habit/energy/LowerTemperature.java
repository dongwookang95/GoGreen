package backend.entity.habit.energy;

import backend.entity.Habit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Class defining habit of lowering temperature.
 */
@NoArgsConstructor
@Getter
@Setter
public class LowerTemperature extends Habit {
    private double hours;
    private double degrees;

    @Override
    public void setAttributeFromPair(Map.Entry<String,String> pair) throws NumberFormatException {
        switch (pair.getKey()) {
            case "Hours":
                double numberOfHours = Double.parseDouble(pair.getValue());
                setHours(numberOfHours);
                break;
            case "Degrees":
                double numberOfDegrees = Double.parseDouble(pair.getValue());
                setDegrees(numberOfDegrees);
                break;
            default:
                // Default option
                break;
        }
    }

}
