package server.entity.habit.energy.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Object used to retrieve data from client requests.
 * This object contains all attributes to create and calculate
 * a LowerTemperature entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LowerTemperatureForm {

    /**
     * Number of hours active.
     */
    private double hours;

    /**
     * How many degrees the temperature is lowered.
     */
    private double degrees;
}
