package server.service.calculator;

import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;

/**
 * Interface to define functions in CalculatorService.
 */
public interface ICalculatorService {

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of LocalProduce afterwards.
     * @param localProduce LocalProduce object to calculate
     * @return updated/calculated LocalProduce object
     */
    public LocalProduce calculateLocalProduce(LocalProduce localProduce);

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of VegetarianMeal afterwards.
     * @param vegetarianMeal VegetarianMeal to calculate
     * @return updated/calculated VegetarianMeal object
     */
    public VegetarianMeal calculateVegetarianMeal(VegetarianMeal vegetarianMeal);

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of PublicTransport afterwards.
     * @param publicTransport PublicTransport object to calculate
     * @return updated/calculated PublicTransport object
     */
    public PublicTransport calculatePublicTransport(PublicTransport publicTransport);

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of TravelByBike afterwards.
     * @param travelByBike TravelByBike object to calculate
     * @return updated/calculated TravelByBike object
     */
    public TravelByBike calculateTravelByBike(TravelByBike travelByBike);

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of LowerTemperature afterwards.
     * @param lowerTemperature LowerTemperature object to calculate
     * @return updated/calculated LowerTemperature object
     */
    public LowerTemperature calculateLowerTemperature(LowerTemperature lowerTemperature);

    /**
     * Calculate amount CO2 saved by calling function that makes a call to external API.
     * Updating values of SolarPanel afterwards.
     * @param solarPanel SolarPanel object to calculate
     * @return updated/calculated SolarPanel object
     */
    public SolarPanel calculateSolarPanel(SolarPanel solarPanel);
}
