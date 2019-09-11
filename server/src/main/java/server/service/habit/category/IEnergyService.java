package server.service.habit.category;

import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;

/**
 * Interface to define functions in EnergyService.
 */
public interface IEnergyService {

    //  [LOWERING TEMPERATURE]

    /**
     * Create a new LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be created
     * @return LowerTemperature that is created
     */
    LowerTemperature createLowerTemperature(LowerTemperature lowerTemperature);

    /**
     * Find a specific LowerTemperature in DB.
     * @param id Id that refers to LowerTemperature.
     * @return Optional LowerTemperature
     */
    LowerTemperature findLowerTemperatureById(Long id);

    /**
     * Delete a LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be deleted
     */
    void deleteLowerTemperature(LowerTemperature lowerTemperature);

    /**
     * Update a LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be updated.
     */
    void updateLowerTemperature(LowerTemperature lowerTemperature);


    //  [SOLAR PANELS]


    /**
     * Create a new SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be created
     * @return SolarPanel that is created
     */
    SolarPanel createSolarPanel(SolarPanel solarPanel);

    /**
     * Find a specific SolarPanel in DB.
     * @param id Id that refers to SolarPanel.
     * @return Optional SolarPanel
     */
    SolarPanel findSolarPanelById(Long id);

    /**
     * Delete a SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be deleted
     */
    void deleteSolarPanel(SolarPanel solarPanel);

    /**
     * Update a SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be updated
     */
    void updateSolarPanel(SolarPanel solarPanel);
}
