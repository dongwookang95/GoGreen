package server.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate User is not found in DB.
 */
@Getter
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to an User object could not be found.
     */
    private String username;
}
