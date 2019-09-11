package server.config;

/**
 * File to configure all constants used for calculations.
 * Easy to adapt.
 */
public class ConstantsConfig {

    //  [FOOD]

    /**
     * Grams of CO2 saved by eating one vegetarian meal.
     */
    public static final double VEGETARIAN_MEAL = 3023.95;

    /**
     * Grams of CO2 saved by eating one locally produced meal.
     */
    public static final double LOCAL_PRODUCE = 661.49;


    //  [TRANSPORT]

    /**
     * Grams of CO2 emitted per kilometer by travelling by car.
     */
    public static final double TRANSPORT_CAR = 127.00;

    /**
     * Grams of CO2 emitted per kilometer by travelling by train.
     */
    public static final double TRANSPORT_TRAIN = 28.00;

    /**
     * Grams of CO2 emitted per kilometer by travelling by bus.
     */
    public static final double TRANSPORT_BUS = 75.00;

    /**
     * Grams of CO2 emitted per kilometer by travelling by tram.
     */
    public static final double TRANSPORT_TRAM = 23.00;

    /**
     * Grams of CO2 emitted per kilometer by travelling by metro.
     */
    public static final double TRANSPORT_METRO = 30.50;


    // [ENERGY]

    /**
     * Grams of CO2 saved by one kWh produced by a solar panel.
     */
    public static final double ENERGY_KWH = 544.31;

    /**
     * Grams of CO2 saved by lowering temperature per degree per hour.
     */
    public static final double ENERGY_DEGREE = 34.30;
}
