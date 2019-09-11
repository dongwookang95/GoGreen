package server.service.habit.category;

import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;

/**
 * Interface to define functions in FoodService.
 */
public interface IFoodService {

    //  [LOCAL PRODUCE]

    /**
     * Create a new LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be created
     * @return LocalProduce that is created
     */
    LocalProduce createLocalProduce(LocalProduce localProduce);

    /**
     * Find a specific LocalProduce in DB.
     * @param id Id that refers to LocalProduce.
     * @return Optional LocalProduce
     */
    LocalProduce findLocalProduceById(Long id);

    /**
     * Delete a LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be deleted
     */
    void deleteLocalProduce(LocalProduce localProduce);

    /**
     * Update a LocalProduce in DB.
     * @param localProduce LocalProduce that needs to be updated.
     */
    void updateLocalProduce(LocalProduce localProduce);


    //  [VEGETARIAN MEAL]


    /**
     * Create a new VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be created
     * @return VegetarianMeal that is created
     */
    VegetarianMeal createVegetarianMeal(VegetarianMeal vegetarianMeal);

    /**
     * Find a specific VegetarianMeal in DB.
     * @param id Id that refers to VegetarianMeal.
     * @return Optional VegetarianMeal
     */
    VegetarianMeal findVegetarianMealById(Long id);

    /**
     * Delete a VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be deleted
     */
    void deleteVegetarianMeal(VegetarianMeal vegetarianMeal);

    /**
     * Update a VegetarianMeal in DB.
     * @param vegetarianMeal VegetarianMeal that needs to be updated
     */
    void updateVegetarianMeal(VegetarianMeal vegetarianMeal);
}
