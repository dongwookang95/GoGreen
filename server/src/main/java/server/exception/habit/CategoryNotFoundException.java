package server.exception.habit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate Category is not found in DB.
 */
@Getter
@AllArgsConstructor
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to an Category object could not be found.
     */
    private Long id;
}
