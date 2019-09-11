package server.controller.habit.category;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.entity.habit.category.Category;
import server.entity.habit.category.SubCategory;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.LocalProduceForm;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.food.subcategory.VegetarianMealForm;
import server.entity.user.User;
import server.service.calculator.CalculatorService;
import server.service.habit.CategoryService;
import server.service.habit.category.FoodService;
import server.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles all the requests concerning objects with category 'Food'.
 */
@RestController
@RequestMapping("/food")
public class FoodController {

    /**
     * Autowired FoodService to use its functionality.
     */
    private final FoodService foodService;

    /**
     * Autowired UserService to use its functionality.
     */
    private final UserService userService;

    /**
     * Autowired CategoryService to use its functionality.
     */
    private final CategoryService categoryService;

    /**
     * Autowired CalculatorService to use its functionality.
     */
    private final CalculatorService calculatorService;

    /**
     * Injecting repositories into service.
     * @param foodService FoodService
     * @param userService UserService
     * @param categoryService CategoryService
     * @param calculatorService CalculatorService
     */
    @Autowired
    public FoodController(FoodService foodService,
                          UserService userService,
                          CategoryService categoryService,
                          CalculatorService calculatorService) {
        this.foodService = foodService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.calculatorService = calculatorService;
    }


    //  [GET MAPPING]

    /**
     * Get all LocalProduce objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return user's local produces
     */
    @GetMapping("/local_produce")
    public ResponseEntity getLocalProducesForUser(@AuthenticationPrincipal
                                          final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getLocalProduces());
    }

    /**
     * Get all VegetarianMeal objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return user's vegetarian meals
     */
    @GetMapping("/vegetarian_meal")
    public ResponseEntity getVegetarianMealsForUser(@AuthenticationPrincipal
                                            final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getVegetarianMeals());
    }

    //  [POST MAPPING]

    /**
     * Create a new LocalProduce object DB.
     * @param form LocalProduce that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return local produce
     */
    @PostMapping("/local_produce")
    public ResponseEntity addLocalProduce(@RequestBody final LocalProduceForm form,
                                          final HttpServletRequest request,
                          @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById((long) 1);
        SubCategory subCategory = categoryService.findSubCategoryById((long) 2);

        LocalProduce localProduce = LocalProduce.builder()
                .build();

        localProduce.setNumberOfMeals(form.getNumberOfMeals());
        localProduce.setUser(user);
        localProduce.setCategory(category);
        localProduce.setSubCategory(subCategory);

        LocalProduce calculated = calculatorService.calculateLocalProduce(localProduce);

        LocalProduce saved = foodService.createLocalProduce(calculated);

        calculatorService.checkForBadgesForUser(user);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/food/local_produce")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    /**
     * Create a new VegetarianMeal object DB.
     * @param form VegetarianMeal that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return created vegetarian meal
     */
    @PostMapping("/vegetarian_meal")
    public ResponseEntity addVegetarianMeal(@RequestBody final VegetarianMealForm form,
                                            final HttpServletRequest request,
                                @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById((long) 1);
        SubCategory subCategory = categoryService.findSubCategoryById((long) 1);

        VegetarianMeal vegetarianMeal = VegetarianMeal.builder()
                .build();

        vegetarianMeal.setNumberOfMeals(form.getNumberOfMeals());
        vegetarianMeal.setUser(user);
        vegetarianMeal.setCategory(category);
        vegetarianMeal.setSubCategory(subCategory);

        VegetarianMeal calculated = calculatorService.calculateVegetarianMeal(vegetarianMeal);

        VegetarianMeal saved = foodService.createVegetarianMeal(calculated);

        calculatorService.checkForBadgesForUser(user);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/food/vegetarian_meal")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    //  [PUT MAPPING]

    /**
     * Update LocalProduce properties in DB.
     * @param form LocalProduce object containing the new properties
     * @param id Path variable LocalProduce id corresponding to LocalProduce
     *           object that needs to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/local_produce/{id}")
    public ResponseEntity updateLocalProduce(@RequestBody final LocalProduceForm form,
                                             @PathVariable("id") final Long id,
                             @AuthenticationPrincipal final UserDetails userDetails) {

        LocalProduce localProduce = foodService.findLocalProduceById(id);

        if (!localProduce.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }
        localProduce.setNumberOfMeals(form.getNumberOfMeals());
        LocalProduce calculated = calculatorService.calculateLocalProduce(localProduce);
        foodService.updateLocalProduce(calculated);
        calculatorService.checkForBadgesForUser(userService.findByUsername(
                userDetails.getUsername()));
        calculatorService.checkForBadgesForUser(userService
                .findByUsername(userDetails.getUsername()));
        return noContent().build();
    }

    /**
     * Update VegetarianMeal properties in DB.
     * @param form VegetarianMeal object containing the new properties
     * @param id Path variable corresponding to VegetarianMeal that needs
     *           to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/vegetarian_meal/{id}")
    public ResponseEntity updateVegetarianMeal(@RequestBody final VegetarianMealForm form,
                                               @PathVariable("id") final Long id,
                               @AuthenticationPrincipal final UserDetails userDetails) {

        VegetarianMeal vegetarianMeal = foodService.findVegetarianMealById(id);

        if (!vegetarianMeal.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        vegetarianMeal.setNumberOfMeals(form.getNumberOfMeals());

        VegetarianMeal calculated = calculatorService.calculateVegetarianMeal(vegetarianMeal);

        foodService.updateVegetarianMeal(calculated);
        calculatorService.checkForBadgesForUser(userService.findByUsername(
                userDetails.getUsername()));
        calculatorService.checkForBadgesForUser(userService
                .findByUsername(userDetails.getUsername()));
        return noContent().build();
    }

    //  [DELETE MAPPING]

    /**
     * Delete a LocalProduce in DB.
     * @param id LocalProduce id referring to LocalProduce that needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/local_produce/{id}")
    public ResponseEntity deleteLocalProduce(@PathVariable("id") final Long id,
                         @AuthenticationPrincipal final UserDetails userDetails) {
        LocalProduce localProduce = foodService.findLocalProduceById(id);

        if (!localProduce.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        foodService.deleteLocalProduce(localProduce);
        calculatorService.checkForBadgesForUser(userService.findByUsername(
                userDetails.getUsername()));
        calculatorService.checkForBadgesForUser(userService
                .findByUsername(userDetails.getUsername()));
        return noContent().build();
    }

    /**
     * Delete an VegetarianMeal in DB.
     * @param id VegetarianMeal id referring to VegetarianMeal that needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/vegetarian_meal/{id}")
    public ResponseEntity deleteVegetarianMeal(@PathVariable("id") final Long id,
                       @AuthenticationPrincipal final UserDetails userDetails) {
        VegetarianMeal vegetarianMeal = foodService.findVegetarianMealById(id);

        if (!vegetarianMeal.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        foodService.deleteVegetarianMeal(vegetarianMeal);
        calculatorService.checkForBadgesForUser(userService.findByUsername(
                userDetails.getUsername()));
        calculatorService.checkForBadgesForUser(userService
                .findByUsername(userDetails.getUsername()));
        return noContent().build();
    }
}
