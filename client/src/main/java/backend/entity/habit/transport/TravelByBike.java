package backend.entity.habit.transport;

import backend.entity.Habit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Class defining a habit of going by bike.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TravelByBike extends Habit {
    double kilometers;
    String transportTypeInstead;

    @Override
    public void setAttributeFromPair(Map.Entry<String,String> pair) throws NumberFormatException {
        switch (pair.getKey()) {
            case "Kilometers":
                double km = Double.parseDouble(pair.getValue());
                setKilometers(km);
                break;
            case "TransportTypeInstead":
                setTransportTypeInstead(pair.getValue());
                break;
            default:
                // Default option
                break;
        }
    }
}
