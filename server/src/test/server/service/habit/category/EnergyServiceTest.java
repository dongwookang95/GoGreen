package server.service.habit.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.exception.habit.energy.LowerTemperatureNotFoundException;
import server.exception.habit.energy.SolarPanelNotFoundException;
import server.repository.habit.energy.LowerTemperatureRepository;
import server.repository.habit.energy.SolarPanelRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class EnergyServiceTest {

    @Mock
    SolarPanelRepository solarPanelRepository;

    @Mock
    LowerTemperatureRepository lowerTemperatureRepository;

    @InjectMocks
    EnergyService energyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createLowerTemperature() {
        LowerTemperature lowerTemperature = LowerTemperature.builder().build();

        when(lowerTemperatureRepository.save(any(LowerTemperature.class))).thenReturn(lowerTemperature);
        LowerTemperature created = energyService.createLowerTemperature(lowerTemperature);

        verify(lowerTemperatureRepository, times(1)).save(lowerTemperature);
        verifyNoMoreInteractions(lowerTemperatureRepository);
    }

    @Test
    void findLowerTemperatureById() {
        LowerTemperature lowerTemperature = LowerTemperature.builder().build();

        when(lowerTemperatureRepository.findById((long) 1)).thenReturn(Optional.ofNullable(lowerTemperature));
        LowerTemperature found = energyService.findLowerTemperatureById((long) 1);

        verify(lowerTemperatureRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(lowerTemperatureRepository);
    }

    @Test
    void findLowerTemperatureByIdNotFound() {
        when(lowerTemperatureRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(LowerTemperatureNotFoundException.class, () -> energyService.findLowerTemperatureById((long) 1));
    }

    @Test
    void deleteLowerTemperature() {
        LowerTemperature lowerTemperature = LowerTemperature.builder().build();

        energyService.deleteLowerTemperature(lowerTemperature);

        verify(lowerTemperatureRepository, times(1)).delete(lowerTemperature);
        verifyNoMoreInteractions(lowerTemperatureRepository);
    }

    @Test
    void updateLowerTemperature() {
        LowerTemperature lowerTemperature = LowerTemperature.builder().build();

        energyService.updateLowerTemperature(lowerTemperature);

        verify(lowerTemperatureRepository, times(1)).save(lowerTemperature);
        verifyNoMoreInteractions(lowerTemperatureRepository);
    }

    @Test
    void createSolarPanel() {
        SolarPanel solarPanel = SolarPanel.builder().build();

        when(solarPanelRepository.save(any(SolarPanel.class))).thenReturn(solarPanel);
        SolarPanel created = energyService.createSolarPanel(solarPanel);

        verify(solarPanelRepository, times(1)).save(solarPanel);
        verifyNoMoreInteractions(solarPanelRepository);
    }

    @Test
    void findSolarPanelById() {
        SolarPanel solarPanel = SolarPanel.builder().build();

        when(solarPanelRepository.findById((long) 1)).thenReturn(Optional.ofNullable(solarPanel));
        SolarPanel found = energyService.findSolarPanelById((long) 1);

        verify(solarPanelRepository, times(1)).findById((long) 1);
        verifyNoMoreInteractions(solarPanelRepository);
    }

    @Test
    void SolarPanelByIdNotFound() {
        when(solarPanelRepository.findById((long) 1)).thenReturn(Optional.empty());

        assertThrows(SolarPanelNotFoundException.class, () -> energyService.findSolarPanelById((long) 1));
    }

    @Test
    void deleteSolarPanel() {
        SolarPanel solarPanel = SolarPanel.builder().build();

        energyService.deleteSolarPanel(solarPanel);

        verify(solarPanelRepository, times(1)).delete(solarPanel);
        verifyNoMoreInteractions(solarPanelRepository);
    }

    @Test
    void updateSolarPanel() {
        SolarPanel solarPanel = SolarPanel.builder().build();

        energyService.updateSolarPanel(solarPanel);

        verify(solarPanelRepository, times(1)).save(solarPanel);
        verifyNoMoreInteractions(solarPanelRepository);
    }

    @Test
    void getSolarPanelRepository() {
    }

    @Test
    void setSolarPanelRepository() {
    }

    @Test
    void getLowerTemperatureRepository() {
    }

    @Test
    void setLowerTemperatureRepository() {
    }
}