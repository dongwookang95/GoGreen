package server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate there is a DB error (e.g. constraint violation).
 */
@Getter
@AllArgsConstructor
public class DataBaseException extends RuntimeException {

    /**
     * Message containing the reason of the DB error.
     */
    private String message;
}


