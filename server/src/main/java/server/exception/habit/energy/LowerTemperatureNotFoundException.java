package server.exception.habit.energy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate LowerTemperature is not found in DB.
 */
@Getter
@AllArgsConstructor
public class LowerTemperatureNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a LowerTemperature object
     * could not be found.
     */
    private Long id;
}
