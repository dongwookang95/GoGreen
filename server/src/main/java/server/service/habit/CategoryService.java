package server.service.habit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;
import server.exception.habit.CategoryNotFoundException;
import server.exception.habit.SubCategoryNotFoundException;
import server.repository.habit.CategoryRepository;
import server.repository.habit.SubCategoryRepository;

import java.util.List;

/**
 * Service for all operations concerning objects with category 'Energy'.
 */
@Service
public class CategoryService implements ICategoryService {

    /**
     * Autowired CategoryRepository to use its functions.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Autowired SubCategoryRepository to use its functions.
     */
    private final SubCategoryRepository subCategoryRepository;

    /**
     * Injecting repositories into service.
     * @param categoryRepository CategoryRepository
     * @param subCategoryRepository SubCategoryRepository
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    /**
     * Find all Category objects in DB.
     * @return List of all Category
     */
    @Override
    public List<Category> findAllCategories() {

        return categoryRepository.findAll();
    }

    /**
     * Find a specific Category in DB.
     * @param id Id that refers to Category.
     * @return Optional Category
     */
    @Override
    public Category findCategoryById(final Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new CategoryNotFoundException(id));
    }

    /**
     * Find a specific SubCategory in DB.
     * @param id Id that refers to SubCategory.
     * @return Optional SubCategory
     */
    @Override
    public SubCategory findSubCategoryById(final Long id) {
        return subCategoryRepository.findById(id).orElseThrow(() ->
                new SubCategoryNotFoundException(id));
    }
}
