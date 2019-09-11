package server.entity.habit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import server.entity.AuditEntity;
import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;
import server.entity.user.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class for habits with attributes that all habits have in common.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Habit extends AuditEntity {

    /**
     * User that 'owns' the habit.
     */
    @ManyToOne
    @JoinColumn(name = "username", updatable = false, nullable = false)
    @JsonIgnoreProperties({"vegetarianMeals", "localProduces"})
    private User user;

    /**
     * Category of the habit.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, nullable = false)
    @JsonIgnoreProperties("subCategories")
    private Category category;

    /**
     * Subcategory of the habit.
     */
    @ManyToOne
    @JoinColumn(name = "subcategory_id", updatable = false, nullable = false)
    @JsonIgnoreProperties({"attributes", "category"})
    private SubCategory subCategory;

    /**
     * Amount of CO2 saved by this habit.
     */
    private double amount;
}
