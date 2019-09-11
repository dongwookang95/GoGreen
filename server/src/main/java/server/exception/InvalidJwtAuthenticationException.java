package server.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception used to indicate JWT Token is not valid or is expired.
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {

    /**
     * Constructor of exception.
     * @param exception Indicates what caused exception
     */
    public InvalidJwtAuthenticationException(final String exception) {
        super(exception);
    }
}
