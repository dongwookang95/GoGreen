package server.exception.habit.energy;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate SolarPanel is not found in DB.
 */
@Getter
@AllArgsConstructor
public class SolarPanelNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a SolarPanel object could
     * not be found.
     */
    private Long id;
}
