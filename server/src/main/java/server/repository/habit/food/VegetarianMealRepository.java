package server.repository.habit.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.food.subcategory.VegetarianMeal;

/**
 * Repository used to do transactions on 'vegetarian_meals' table.
 */
@Repository
public interface VegetarianMealRepository extends
                    JpaRepository<VegetarianMeal, Long> {

}
