package server.service.habit;

import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;

import java.util.List;

/**
 * Interface to define functions in CategoryService.
 */
public interface ICategoryService {

    /**
     * Find all Category objects in DB.
     * @return List of all Category
     */
    List<Category> findAllCategories();

    /**
     * Find a specific Category in DB.
     * @param id Id that refers to Category.
     * @return Optional Category
     */
    Category findCategoryById(Long id);

    /**
     * Find a specific SubCategory in DB.
     * @param id Id that refers to SubCategory.
     * @return Optional SubCategory
     */
    SubCategory findSubCategoryById(Long id);
}
