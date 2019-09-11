package server.service.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.config.ConstantsConfig;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;
import server.entity.user.Achievement;
import server.entity.user.User;
import server.repository.user.BadgeRepository;
import server.service.user.UserService;

import java.util.Set;

/**
 * Service to be the connecting part between the controller.
 * Here the objects should be updated according to the values calculated
 */
@Service
public class CalculatorService implements ICalculatorService {

    private static final long BADGE_BRONZE_ID = 1;
    private static final long BADGE_SILVER_ID = 2;
    private static final long BADGE_GOLD_ID = 3;

    /**
     * Autowired BadgeRepository to make use of its functionality.
     */
    private final BadgeRepository badgeRepository;

    /**
     * Autowired UserService to make use of its functionality.
     */
    private final UserService userService;

    /**
     * Injecting repositories into service.
     * @param badgeRepository BadgeRepository
     * @param userService UserRepository
     */
    @Autowired
    public CalculatorService(BadgeRepository badgeRepository, UserService userService) {
        this.badgeRepository = badgeRepository;
        this.userService = userService;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of LocalProduce afterwards.
     * @param localProduce LocalProduce object to calculate
     * @return updated/calculated LocalProduce object
     */
    @Override
    public LocalProduce calculateLocalProduce(LocalProduce localProduce) {
        double amount = localProduce.getNumberOfMeals() * ConstantsConfig.LOCAL_PRODUCE;
        localProduce.setAmount(amount);
        return localProduce;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of VegetarianMeal afterwards.
     * @param vegetarianMeal VegetarianMeal to calculate
     * @return updated/calculated VegetarianMeal object
     */
    @Override
    public VegetarianMeal calculateVegetarianMeal(VegetarianMeal vegetarianMeal) {
        double amount = vegetarianMeal.getNumberOfMeals() * ConstantsConfig.VEGETARIAN_MEAL;
        vegetarianMeal.setAmount(amount);
        return vegetarianMeal;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of PublicTransport afterwards.
     * @param publicTransport PublicTransport object to calculate
     * @return updated/calculated PublicTransport object
     */
    @Override
    public PublicTransport calculatePublicTransport(PublicTransport publicTransport) {
        double amountInstead = publicTransport.getTransportTypeInstead().getEmission()
                * publicTransport.getKilometers();
        double amountActual = publicTransport.getTransportTypeActual().getEmission()
                * publicTransport.getKilometers();
        publicTransport.setAmount(amountInstead - amountActual);
        return publicTransport;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of TravelByBike afterwards.
     * @param travelByBike TravelByBike object to calculate
     * @return updated/calculated TravelByBike object
     */
    @Override
    public TravelByBike calculateTravelByBike(TravelByBike travelByBike) {
        double amount = travelByBike
                .getTransportTypeInstead()
                .getEmission() * travelByBike.getKilometers();
        travelByBike.setAmount(amount);
        return travelByBike;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of LowerTemperature afterwards.
     * @param lowerTemperature LowerTemperature object to calculate
     * @return updated/calculated LowerTemperature object
     */
    @Override
    public LowerTemperature calculateLowerTemperature(LowerTemperature lowerTemperature) {
        double amount = lowerTemperature.getDegrees() * lowerTemperature.getHours()
                * ConstantsConfig.ENERGY_DEGREE;
        lowerTemperature.setAmount(amount);
        return lowerTemperature;
    }

    /**
     * Calculate amount CO2 saved.
     * Updating values of SolarPanel afterwards.
     * @param solarPanel SolarPanel object to calculate
     * @return updated/calculated SolarPanel object
     */
    @Override
    public SolarPanel calculateSolarPanel(SolarPanel solarPanel) {
        double amount = solarPanel.getNumberOfSolarPanels() * solarPanel.getHours()
                * solarPanel.getWatt() * ConstantsConfig.ENERGY_KWH;
        solarPanel.setAmount(amount);
        return solarPanel;
    }

    /**
     * Calculating total score of a certain subcategory for a specific user.
     * @param user User object
     * @param subCategory name of the subcategory, String
     * @return double total score
     */
    public double totalScoreForSubCategoryForUser(User user, String subCategory) {

        double totalScore = 0.00;

        if (subCategory.equals("Vegetarian Meal")) {

            Set<VegetarianMeal> vegetarianMeals = user.getVegetarianMeals();
            for (VegetarianMeal vegetarianMeal: vegetarianMeals) {
                totalScore += vegetarianMeal.getAmount();
            }
        } else if (subCategory.equals("Local Produce")) {

            Set<LocalProduce> localProduces = user.getLocalProduces();
            for (LocalProduce localProduce: localProduces) {
                totalScore += localProduce.getAmount();
            }
        } else if (subCategory.equals("Public Transport")) {

            Set<PublicTransport> publicTransports = user.getPublicTransports();
            for (PublicTransport publicTransport: publicTransports) {
                totalScore += publicTransport.getAmount();
            }
        } else if (subCategory.equals("Travel By Bike")) {

            Set<TravelByBike> travelByBikes = user.getTravelByBikes();
            for (TravelByBike travelByBike: travelByBikes) {
                totalScore += travelByBike.getAmount();
            }
        } else if (subCategory.equals("Solar Panel")) {

            Set<SolarPanel> solarPanels = user.getSolarPanels();
            for (SolarPanel solarPanel: solarPanels) {
                totalScore += solarPanel.getAmount();
            }
        } else {

            Set<LowerTemperature> lowerTemperatures = user.getLowerTemperatures();
            for (LowerTemperature lowerTemperature: lowerTemperatures) {
                totalScore += lowerTemperature.getAmount();
            }
        }

        return totalScore;
    }

    /**
     * Calculating total score of a certain category for a specific user.
     * @param user User object
     * @param category name of the category, String
     * @return double total score
     */
    public double totalScoreForCategoryForUser(User user, String category) {

        double totalScore = 0.00;

        if (category.equals("Food")) {
            totalScore = totalScoreForSubCategoryForUser(user, "Local Produce")
                    + totalScoreForSubCategoryForUser(user,"Vegetarian Meal");
        } else if (category.equals("Energy")) {
            totalScore = totalScoreForSubCategoryForUser(user, "Solar Panel")
                    + totalScoreForSubCategoryForUser(user,"Lower Temperature");

        } else {
            totalScore = totalScoreForSubCategoryForUser(user, "Public Transport")
                    + totalScoreForSubCategoryForUser(user,"Public Transport");
        }

        return totalScore;
    }

    /**
     * Calculating total score of a certain user.
     * @param user User object
     * @return double total score
     */
    public double totalScoreForUser(User user) {
        return totalScoreForCategoryForUser(user, "Food")
                + totalScoreForCategoryForUser(user, "Energy")
                + totalScoreForCategoryForUser(user, "Transport");
    }

    /**
     * Checks and updates badges for user.
     * @param user User
     */
    public void checkForBadgesForUser(User user) {
        Set<Achievement> achievements = user.getAchievements();

        for (Achievement achievement: achievements) {
            if (achievement.getCategory().getDescription().equals("Food")) {
                updateBadgeForUser(user, achievement, "Food");
            } else if (achievement.getCategory().getDescription().equals("Energy")) {
                updateBadgeForUser(user, achievement, "Energy");
            } else {
                updateBadgeForUser(user, achievement, "Transport");
            }
        }

        userService.updateUser(user);
    }

    /**
     * Updates a badge for an achievement for an user.
     * @param user User
     * @param achievement Achievement
     * @param category Category
     */
    public void updateBadgeForUser(User user, Achievement achievement, String category) {
        double totalScore = totalScoreForCategoryForUser(user, category);
        if (totalScore >= 25000.00) {
            achievement.setBadge(badgeRepository.findById(BADGE_GOLD_ID).get());
        } else if (totalScore >= 10000.00) {
            achievement.setBadge(badgeRepository.findById(BADGE_SILVER_ID).get());
        } else {
            achievement.setBadge(badgeRepository.findById(BADGE_BRONZE_ID).get());
        }
    }
}
