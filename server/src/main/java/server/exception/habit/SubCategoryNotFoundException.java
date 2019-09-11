package server.exception.habit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate SubCategory is not found in DB.
 */
@Getter
@AllArgsConstructor
public class SubCategoryNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to an SubCategory object
     * could not be found.
     */
    private Long id;
}
