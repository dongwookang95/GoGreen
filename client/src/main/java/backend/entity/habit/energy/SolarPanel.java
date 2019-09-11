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
public class SolarPanel extends Habit {
    private double hours;
    private int watt;
    private int numberOfSolarPanels;

    @Override
    public void setAttributeFromPair(Map.Entry<String,String> pair) throws NumberFormatException {
        String key = pair.getKey();

        switch (key) {
            case "Hours":
                double time = Double.parseDouble(pair.getValue());
                setHours(time);
                break;
            case "Watt":
                int quantity = Integer.parseInt(pair.getValue());
                setWatt(quantity);
                break;
            case "NumberOfSolarPanels":
                int number = Integer.parseInt(pair.getValue());
                setNumberOfSolarPanels(number);
                break;
            default:
                // Default option
                break;
        }
    }

}
