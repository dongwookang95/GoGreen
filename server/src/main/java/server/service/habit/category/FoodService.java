package server.service.habit.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.exception.habit.food.LocalProduceNotFoundException;
import server.exception.habit.food.VegetarianMealNotFoundException;
import server.repository.habit.food.LocalProduceRepository;
import server.repository.habit.food.VegetarianMealRepository;

/**
 * Service for all operations concerning objects with category 'Food'.
 */
@Service
public class FoodService implements IFoodService {

    /**
     * Autowired LocalProduceRepository to use its functions.
     */
    private final LocalProduceRepository localProduceRepository;

    /**
     * Autowired VegetarianMealRepository to use its functions.
     */
    private final VegetarianMealRepository vegetarianMealRepository;

    /**
     * Injecting repositories into service.
     * @param localProduceRepository LocalProduceRepository
     * @param vegetarianMealRepository VegetarianMealRepository
     */
    @Autowired
    public FoodService(LocalProduceRepository localProduceRepository,
                       VegetarianMealRepository vegetarianMealRepository) {
        this.localProduceRepository = localProduceRepository;
        this.vegetarianMealRepository = vegetarianMealRepository;
    }


    //  [LOCAL PRODUCE]

    /**
     * Create a new LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be created
     * @return LocalProduce that is created
     */
    @Override
    public LocalProduce createLocalProduce(final LocalProduce localProduce) {
        return localProduceRepository.save(localProduce);
    }

    /**
     * Find a specific LocalProduce in DB.
     * @param id Id that refers to LocalProduce.
     * @return Optional LocalProduce
     */
    @Override
    public LocalProduce findLocalProduceById(final Long id) {
        return localProduceRepository.findById(id).orElseThrow(() ->
                new LocalProduceNotFoundException(id));
    }

    /**
     * Delete a LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be deleted
     */
    @Override
    public void deleteLocalProduce(final LocalProduce localProduce) {

        localProduceRepository.delete(localProduce);
    }

    /**
     * Update a LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be updated.
     */
    @Override
    public void updateLocalProduce(final LocalProduce localProduce) {
        localProduceRepository.save(localProduce);
    }


    //  [VEGETARIAN MEAL]

    /**
     * Create a new VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be created
     * @return VegetarianMeal that is created
     */
    @Override
    public VegetarianMeal createVegetarianMeal(
            final VegetarianMeal vegetarianMeal) {
        return vegetarianMealRepository.save(vegetarianMeal);
    }

    /**
     * Find a specific VegetarianMeal in DB.
     * @param id Id that refers to VegetarianMeal.
     * @return Optional VegetarianMeal
     */
    @Override
    public VegetarianMeal findVegetarianMealById(final Long id) {
        return vegetarianMealRepository.findById(id).orElseThrow(() ->
                new VegetarianMealNotFoundException(id));
    }

    /**
     * Delete a VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be deleted
     */
    @Override
    public void deleteVegetarianMeal(final VegetarianMeal vegetarianMeal) {
        vegetarianMealRepository.delete(vegetarianMeal);
    }

    /**
     * Update a VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be updated
     */
    @Override
    public void updateVegetarianMeal(final VegetarianMeal vegetarianMeal) {
        vegetarianMealRepository.save(vegetarianMeal);
    }
}
