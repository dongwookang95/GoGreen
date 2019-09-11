package server.service.habit.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.exception.habit.energy.LowerTemperatureNotFoundException;
import server.exception.habit.energy.SolarPanelNotFoundException;
import server.repository.habit.energy.LowerTemperatureRepository;
import server.repository.habit.energy.SolarPanelRepository;

/**
 * Service for all operations concerning objects with category 'Energy'.
 */
@Service
public class EnergyService implements IEnergyService {

    /**
     * Autowired SolarPanelRepository to use its functions.
     */
    private final SolarPanelRepository solarPanelRepository;

    /**
     * Autowired LowerTemperatureRepository to use its functions.
     */
    private final LowerTemperatureRepository lowerTemperatureRepository;

    /**
     * Injecting repositories into service.
     * @param solarPanelRepository SolarPanelRepository
     * @param lowerTemperatureRepository LowerTemperatureRepository
     */
    @Autowired
    public EnergyService(SolarPanelRepository solarPanelRepository,
                         LowerTemperatureRepository lowerTemperatureRepository) {
        this.solarPanelRepository = solarPanelRepository;
        this.lowerTemperatureRepository = lowerTemperatureRepository;
    }

    //  [LOWERING TEMPERATURE]

    /**
     * Create a new LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be created
     * @return LowerTemperature that is created
     */
    @Override
    public LowerTemperature createLowerTemperature(
            LowerTemperature lowerTemperature) {
        return lowerTemperatureRepository.save(lowerTemperature);
    }

    /**
     * Find a specific LowerTemperature in DB.
     * @param id Id that refers to LowerTemperature.
     * @return Optional LowerTemperature
     */
    @Override
    public LowerTemperature findLowerTemperatureById(final Long id) {
        return lowerTemperatureRepository.findById(id).orElseThrow(() ->
                new LowerTemperatureNotFoundException(id));
    }

    /**
     * Delete a LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be deleted
     */
    @Override
    public void deleteLowerTemperature(final LowerTemperature lowerTemperature) {
        lowerTemperatureRepository.delete(lowerTemperature);
    }

    /**
     * Update a LowerTemperature in DB.
     * @param lowerTemperature LowerTemperature that needs to be updated.
     */
    @Override
    public void updateLowerTemperature(final LowerTemperature lowerTemperature) {
        lowerTemperatureRepository.save(lowerTemperature);
    }


    //     [SOLAR PANEL]

    /**
     * Create a new SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be created
     * @return SolarPanel that is created
     */
    @Override
    public SolarPanel createSolarPanel(final SolarPanel solarPanel) {
        return solarPanelRepository.save(solarPanel);
    }

    /**
     * Find a specific SolarPanel in DB.
     * @param id Id that refers to SolarPanel.
     * @return Optional SolarPanel
     */
    @Override
    public SolarPanel findSolarPanelById(final Long id) {
        return solarPanelRepository.findById(id).orElseThrow(() ->
                new SolarPanelNotFoundException(id));
    }

    /**
     * Delete a SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be deleted
     */
    @Override
    public void deleteSolarPanel(final SolarPanel solarPanel) {
        solarPanelRepository.delete(solarPanel);
    }

    /**
     * Update a SolarPanel in DB.
     * @param solarPanel SolarPanel that needs to be updated
     */
    @Override
    public void updateSolarPanel(final SolarPanel solarPanel) {

        solarPanelRepository.save(solarPanel);
    }
}
