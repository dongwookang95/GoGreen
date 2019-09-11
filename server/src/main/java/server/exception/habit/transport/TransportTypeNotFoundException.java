package server.exception.habit.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate transport type is not found.
 */
@Getter
@AllArgsConstructor
public class TransportTypeNotFoundException extends RuntimeException {

    /**
     * Indicates which transport could not be found.
     */
    private String transportType;
}
