package server.service.habit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;
import server.exception.habit.CategoryNotFoundException;
import server.exception.habit.SubCategoryNotFoundException;
import server.repository.habit.CategoryRepository;
import server.repository.habit.SubCategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    SubCategoryRepository subCategoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllCategories() {
        Category categoryFood = Category.builder().description("Food").build();
        Category categoryEnergy = Category.builder().description("Energy").build();
        Category categoryTransport = Category.builder().description("Transport").build();

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(categoryFood, categoryTransport, categoryEnergy));

        List<Category> categories = categoryService.findAllCategories();

        assertEquals(3, categories.size());
    }

    @Test
    void findCategoryById() {
        Category categoryFood = Category.builder().description("Food").build();

        when(categoryRepository.findById((long) 1)).thenReturn(Optional.ofNullable(categoryFood));
        Category found = categoryService.findCategoryById((long) 1);

        verify(categoryRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void findCategoryByIdNotFound() {
        when(categoryRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.findCategoryById((long) 1));
    }

    @Test
    void findSubCategoryById() {
        SubCategory subCategoryVegetarianMeal = SubCategory.builder().description("VegetarianMeal").build();

        when(subCategoryRepository.findById((long) 1)).thenReturn(Optional.ofNullable(subCategoryVegetarianMeal));
        SubCategory found = categoryService.findSubCategoryById((long) 1);

        verify(subCategoryRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(subCategoryRepository);
    }

    @Test
    void findSubCategoryByIdNotFound() {
        when(subCategoryRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(SubCategoryNotFoundException.class, () -> categoryService.findSubCategoryById((long) 1));
    }
}