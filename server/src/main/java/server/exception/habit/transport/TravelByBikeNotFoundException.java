package server.exception.habit.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate TravelByBike is not found in DB.
 */
@Getter
@AllArgsConstructor
public class TravelByBikeNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a TravelByBike object could
     * not be found.
     */
    private Long id;
}
