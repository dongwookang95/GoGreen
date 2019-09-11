package server.exception.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate Achievement is not found in DB.
 */
@Getter
@AllArgsConstructor
public class AchievementNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to an Achievement object
     * could not be found.
     */
    private Long id;
}
