package server.entity.habit.food.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.habit.food.Food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VegetarianMeal entity which corresponds to
 * 'vegetarian_meals' table in DB.
 */
@Entity
@Table(name = "vegetarian_meals")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VegetarianMeal extends Food {

    /**
     * Number of meals eaten.
     */
    @Column(name = "number_of_meals")
    private int numberOfMeals;
}
