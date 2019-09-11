package backend.entity.user;

import backend.entity.Achievement;
import backend.entity.Badge;
import backend.entity.habit.energy.LowerTemperature;
import backend.entity.habit.energy.SolarPanel;
import backend.entity.habit.food.LocalProduce;
import backend.entity.habit.food.VegetarianMeal;
import backend.entity.habit.structure.OverviewElement;
import backend.entity.habit.transport.PublicTransport;
import backend.entity.habit.transport.TravelByBike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
public class Friend extends User {

    protected ArrayList<Achievement> achievements = new ArrayList<>();

    protected double totalVegetarianMeals;
    protected double totalLocalProduces;
    protected double totalSolarPanels;
    protected double totalLowerTemperatures;
    protected double totalPublicTransports;
    protected double totalTravelsByBike;

    protected double totalFood;
    protected double totalEnergy;
    protected double totalTransport;
    protected double total;

    protected ArrayList<OverviewElement> overview = new ArrayList<>();
    protected ArrayList<VegetarianMeal> vegetarianMeals = new ArrayList<>();
    protected ArrayList<LocalProduce> localProduces = new ArrayList<>();
    protected ArrayList<SolarPanel> solarPanels = new ArrayList<>();
    protected ArrayList<LowerTemperature> lowerTemperatures = new ArrayList<>();
    protected ArrayList<PublicTransport> publicTransports = new ArrayList<>();
    protected ArrayList<TravelByBike> travelByBikes = new ArrayList<>();

    public Friend(String username) {
        super(username);
    }

    /**
     * Method to set total amounts per category and subcategory.
     * Using the ArrayList of habits, sum all the amount attributes
     * of every element in the list and store the value in the
     * corresponding totalCategory and totalSubCategory field.
     */
    public void calculateTotalAmounts() {
        totalVegetarianMeals = 0;
        totalLocalProduces = 0;
        totalSolarPanels = 0;
        totalLowerTemperatures = 0;
        totalPublicTransports = 0;
        totalTravelsByBike = 0;
        // Totals for the overview elements
        for (VegetarianMeal vegMeal : vegetarianMeals) {
            totalVegetarianMeals += vegMeal.getAmount();
        }
        totalVegetarianMeals = Math.round(totalVegetarianMeals);

        for (LocalProduce locProd : localProduces) {
            totalLocalProduces += locProd.getAmount();
        }
        totalLocalProduces = Math.round(totalLocalProduces);

        for (SolarPanel solPan : solarPanels) {
            totalSolarPanels += solPan.getAmount();
        }
        totalSolarPanels = Math.round(totalSolarPanels);

        for (LowerTemperature lowTemp : lowerTemperatures) {
            totalLowerTemperatures += lowTemp.getAmount();
        }
        totalLowerTemperatures = Math.round(totalLowerTemperatures);

        for (PublicTransport pubTran : publicTransports) {
            totalPublicTransports += pubTran.getAmount();
        }
        totalPublicTransports = Math.round(totalPublicTransports);

        for (TravelByBike byBike : travelByBikes) {
            totalTravelsByBike += byBike.getAmount();
        }
        totalTravelsByBike = Math.round(totalTravelsByBike);

        // Totals for the achievements
        totalFood = totalVegetarianMeals + totalLocalProduces;
        totalEnergy = totalSolarPanels + totalLowerTemperatures;
        totalTransport = totalPublicTransports + totalTravelsByBike;

        // Total for the leader board
        total = totalEnergy + totalTransport + totalFood;

        createOverview();
    }

    /**
     * Creates the overview elements for the current user / friend.
     */
    private void createOverview() {
        if (!overview.isEmpty()) {
            overview.clear();
        }
        overview.add(new OverviewElement("Vegetarian Meals", totalVegetarianMeals ));
        overview.add(new OverviewElement("Local Produces", totalLocalProduces));
        overview.add(new OverviewElement("Solar Panels", totalSolarPanels));
        overview.add(new OverviewElement("Lower Temperatures", totalLowerTemperatures));
        overview.add(new OverviewElement("Public Transports", totalPublicTransports));
        overview.add(new OverviewElement("Travels by Bike", totalTravelsByBike));
    }

    /**
     * Helper method to assign an achievement to a badge icon.
     */
    public void setAchievementPaths() {
        for (Achievement achievement : achievements) {
            if (achievement.getBadge() == null) {
                achievement.setBadge(new Badge("default"));
                achievement.getBadge().setPath(achievement.getCategory());
            } else {
                achievement.getBadge().setPath(achievement.getCategory());
            }
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof Friend) {
            Friend that = (Friend) other;

            if ((this.getUsername() == null && that.getUsername() == null)
                    || this.getUsername().equals(that.getUsername())) {

                boolean vm = this.getVegetarianMeals().size() == that.getVegetarianMeals().size();
                vm = vm && this.getVegetarianMeals().equals(that.getVegetarianMeals());
                boolean lp = this.getLocalProduces().size() == that.getLocalProduces().size();
                lp = lp && this.getLocalProduces().equals(that.getLocalProduces());
                boolean sp = this.getSolarPanels().size() == that.getSolarPanels().size();
                sp = sp && this.getSolarPanels().equals(that.getSolarPanels());
                boolean lt = 
                        this.getLowerTemperatures().size() == that.getLowerTemperatures().size();
                lt = lt && this.getLowerTemperatures().equals(that.getLowerTemperatures());
                boolean pt = this.getPublicTransports().size() == that.getPublicTransports().size();
                pt = pt && this.getPublicTransports().equals(that.getPublicTransports());
                boolean tbb = this.getTravelByBikes().size() == that.getTravelByBikes().size();
                tbb = tbb && this.getTravelByBikes().equals(that.getTravelByBikes());

                boolean tf = this.getTotalFood() == that.getTotalFood();
                boolean te = this.getTotalEnergy() == that.getTotalEnergy();
                boolean tt = this.getTotalTransport() == that.getTotalTransport();

                return vm && lp && sp && lt && pt && tbb && tf && te && tt;

            }

        }
        return false;
    }
}
