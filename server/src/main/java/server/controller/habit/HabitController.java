package server.controller.habit;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.entity.user.User;
import server.message.HabitOverview;
import server.service.calculator.CalculatorService;
import server.service.habit.CategoryService;
import server.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all the requests concerning general habit actions.
 */
@RestController
@RequestMapping("/habit")
public class HabitController {

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
     * @param userService UserService
     * @param categoryService CategoryService
     * @param calculatorService CalculatorService
     */
    @Autowired
    public HabitController(UserService userService,
                           CategoryService categoryService,
                           CalculatorService calculatorService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.calculatorService = calculatorService;
    }


    //  [GET MAPPING]

    /**
     * Get all categories currently in use and in DB.
     * @return List of all categories objects
     */
    @GetMapping("/category/all")
    public ResponseEntity getAllCategories() {

        return ok(categoryService.findAllCategories());
    }

    /**
     * Get an overview of the habits of an user.
     * @param username User id referring to a specific user
     * @return List of habit overviews, with details of different habits
     */
    @GetMapping("/overview/{username}")
    public List<HabitOverview> getOverviewForUser(
            @PathVariable("username") final String username) {

        //Find user in DB and check if it exists

        User user = userService.findByUsername(username);

        //Create HabitOverview objects with calculated data

        HabitOverview vegetarianMealsOverview = new HabitOverview(
                "Vegetarian Meals",
                calculatorService.totalScoreForSubCategoryForUser(user, "Vegetarian Meal"));
        HabitOverview localProducesOverview = new HabitOverview(
                "Local Produces",
                calculatorService.totalScoreForSubCategoryForUser(user, "Local Produce"));
        HabitOverview solarPanelsOverview = new HabitOverview(
                "Solar Panels",
                calculatorService.totalScoreForSubCategoryForUser(user, "Solar Panel"));
        HabitOverview lowerTemperatureOverview = new HabitOverview(
                "Lower Temperature",
                calculatorService.totalScoreForSubCategoryForUser(user, "Lower Temperature"));
        HabitOverview publicTransportOverview = new HabitOverview(
                "Public Transport",
                calculatorService.totalScoreForSubCategoryForUser(user, "Public Transport"));
        HabitOverview travelByBikesOverview = new HabitOverview(
                "Travel By Bike",
                calculatorService.totalScoreForSubCategoryForUser(user, "Travel By Bike"));

        //Construct result list and return it

        List<HabitOverview> result = new ArrayList<>();

        result.add(vegetarianMealsOverview);
        result.add(localProducesOverview);
        result.add(solarPanelsOverview);
        result.add(lowerTemperatureOverview);
        result.add(publicTransportOverview);
        result.add(travelByBikesOverview);

        return result;
    }
}
