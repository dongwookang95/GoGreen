package server.service.habit.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.exception.habit.food.LocalProduceNotFoundException;
import server.exception.habit.food.VegetarianMealNotFoundException;
import server.repository.habit.food.LocalProduceRepository;
import server.repository.habit.food.VegetarianMealRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class FoodServiceTest {

    @Mock
    VegetarianMealRepository vegetarianMealRepository;

    @Mock
    LocalProduceRepository localProduceRepository;

    @InjectMocks
    FoodService foodService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createVegetarianMeal() {
        VegetarianMeal vegetarianMeal = VegetarianMeal.builder().build();

        when(vegetarianMealRepository.save(any(VegetarianMeal.class))).thenReturn(vegetarianMeal);
        VegetarianMeal created = foodService.createVegetarianMeal(vegetarianMeal);

        verify(vegetarianMealRepository, times(1)).save(vegetarianMeal);
        verifyNoMoreInteractions(vegetarianMealRepository);
    }

    @Test
    void findVegetarianMealById() {
        VegetarianMeal vegetarianMeal = VegetarianMeal.builder().build();

        when(vegetarianMealRepository.findById((long) 1)).thenReturn(Optional.ofNullable(vegetarianMeal));
        VegetarianMeal found = foodService.findVegetarianMealById((long) 1);

        verify(vegetarianMealRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(vegetarianMealRepository);
    }

    @Test
    void findVegetarianMealByIdNotFound() {
        when(vegetarianMealRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(VegetarianMealNotFoundException.class, () -> foodService.findVegetarianMealById((long) 1));
    }

    @Test
    void deleteVegetarianMeal() {
        VegetarianMeal vegetarianMeal = VegetarianMeal.builder().build();

        foodService.deleteVegetarianMeal(vegetarianMeal);

        verify(vegetarianMealRepository, times(1)).delete(vegetarianMeal);
        verifyNoMoreInteractions(vegetarianMealRepository);
    }

    @Test
    void updateVegetarianMeal() {
        VegetarianMeal vegetarianMeal = VegetarianMeal.builder().build();

        foodService.updateVegetarianMeal(vegetarianMeal);

        verify(vegetarianMealRepository, times(1)).save(vegetarianMeal);
        verifyNoMoreInteractions(vegetarianMealRepository);
    }

    @Test
    void createLocalProduce() {
        LocalProduce localProduce = LocalProduce.builder().build();

        when(localProduceRepository.save(any(LocalProduce.class))).thenReturn(localProduce);
        LocalProduce created = foodService.createLocalProduce(localProduce);

        verify(localProduceRepository, times(1)).save(localProduce);
        verifyNoMoreInteractions(localProduceRepository);
    }

    @Test
    void findLocalProduceById() {
        LocalProduce localProduce = LocalProduce.builder().build();

        when(localProduceRepository.findById((long) 1)).thenReturn(Optional.ofNullable(localProduce));
        LocalProduce found = foodService.findLocalProduceById((long) 1);

        verify(localProduceRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(localProduceRepository);
    }

    @Test
    void LocalProduceByIdNotFound() {
        when(localProduceRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(LocalProduceNotFoundException.class, () -> foodService.findLocalProduceById((long) 1));
    }

    @Test
    void deleteLocalProduce() {
        LocalProduce localProduce = LocalProduce.builder().build();

        foodService.deleteLocalProduce(localProduce);

        verify(localProduceRepository, times(1)).delete(localProduce);
        verifyNoMoreInteractions(localProduceRepository);
    }

    @Test
    void updateLocalProduce() {
        LocalProduce localProduce = LocalProduce.builder().build();

        foodService.updateLocalProduce(localProduce);

        verify(localProduceRepository, times(1)).save(localProduce);
        verifyNoMoreInteractions(localProduceRepository);
    }

    @Test
    void getLocalProduceRepository() {
    }

    @Test
    void setLocalProduceRepository() {
    }

    @Test
    void getVegetarianMealRepository() {
    }

    @Test
    void setVegetarianMealRepository() {
    }
}