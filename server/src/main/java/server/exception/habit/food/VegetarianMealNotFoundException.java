package server.exception.habit.food;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception used to indicate VegetarianMeal is not found in DB.
 */
@Getter
@AllArgsConstructor
public class VegetarianMealNotFoundException extends RuntimeException {

    /**
     * Id indicates which id referring to a VegetarianMeal object could
     * not be found.
     */
    private Long id;
}
