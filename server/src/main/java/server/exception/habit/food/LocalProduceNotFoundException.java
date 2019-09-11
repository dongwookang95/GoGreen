package server.exception.habit.food;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate LocalProduce is not found in DB.
 */
@Getter
@AllArgsConstructor
public class LocalProduceNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a LocalProduce object could
     * not be found.
     */
    private Long id;
}
