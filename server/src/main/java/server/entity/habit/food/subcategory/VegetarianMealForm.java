package server.entity.habit.food.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Object used to retrieve data from client requests.
 * This object contains all attributes to create and
 * calculate a VegetarianMeal entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VegetarianMealForm {

    /**
     * Number of meals eaten.
     */
    private int numberOfMeals;
}
