package server.exception.habit.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate PublicTransport is not found in DB.
 */
@Getter
@AllArgsConstructor
public class PublicTransportNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a PublicTransport object
     * could not be found.
     */
    private Long id;
}
