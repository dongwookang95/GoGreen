package server.service.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import server.config.ConstantsConfig;
import server.entity.habit.category.Category;
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.food.subcategory.LocalProduce;
import server.entity.habit.food.subcategory.VegetarianMeal;
import server.entity.habit.transport.TransportType;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;
import server.entity.user.Achievement;
import server.entity.user.Badge;
import server.entity.user.User;
import server.repository.user.BadgeRepository;
import server.service.user.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
class CalculatorServiceTest {

    @Mock
    BadgeRepository badgeRepository;

    @Mock
    UserService userService;

    @InjectMocks
    CalculatorService calculatorService;

    private User user;

    private Badge badgeGold = Badge.builder().name("gold").build();
    private Badge badgeSilver = Badge.builder().name("silver").build();
    private Badge badgeBronze = Badge.builder().name("bronze").build();

    private LocalProduce localProduce = LocalProduce.builder()
            .numberOfMeals(1)
            .build();

    private VegetarianMeal vegetarianMeal = VegetarianMeal.builder()
            .numberOfMeals(1)
            .build();

    private PublicTransport publicTransport = PublicTransport.builder()
            .transportTypeActual(TransportType.TRAM)
            .transportTypeInstead(TransportType.CAR)
            .build();

    private TravelByBike travelByBike = TravelByBike.builder()
            .transportTypeInstead(TransportType.BUS)
            .build();

    private SolarPanel solarPanel = SolarPanel.builder()
            .numberOfSolarPanels(3)
            .watt(300)
            .build();

    private LowerTemperature lowerTemperature = LowerTemperature.builder()
            .degrees(4)
            .build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void calculateLocalProduce() {
        LocalProduce calculated = calculatorService.calculateLocalProduce(localProduce);
        assertEquals(ConstantsConfig.LOCAL_PRODUCE, calculated.getAmount());
    }

    @Test
    void calculateVegetarianMeal() {
        VegetarianMeal calculated = calculatorService.calculateVegetarianMeal(vegetarianMeal);
        assertEquals(ConstantsConfig.VEGETARIAN_MEAL, calculated.getAmount());
    }

    @Test
    void calculatePublicTransport() {
        publicTransport.setKilometers(1);
        PublicTransport calculated = calculatorService.calculatePublicTransport(publicTransport);
        assertEquals(ConstantsConfig.TRANSPORT_CAR - ConstantsConfig.TRANSPORT_TRAM, calculated.getAmount());

    }

    @Test
    void calculateTravelByBike() {
        travelByBike.setKilometers(1);
        TravelByBike calculated = calculatorService.calculateTravelByBike(travelByBike);
        assertEquals(ConstantsConfig.TRANSPORT_BUS, calculated.getAmount());
    }

    @Test
    void calculateLowerTemperature() {
        lowerTemperature.setHours(1);
        LowerTemperature calculated = calculatorService.calculateLowerTemperature(lowerTemperature);
        assertEquals(4*ConstantsConfig.ENERGY_DEGREE, calculated.getAmount());
    }

    @Test
    void calculateSolarPanel() {
        solarPanel.setHours(1);
        SolarPanel calculated = calculatorService.calculateSolarPanel(solarPanel);
        assertEquals(3*300*ConstantsConfig.ENERGY_KWH, calculated.getAmount());
    }

//    @Test
//    void totalScoreForSubCategoryForUser() {
//    }
//
//    @Test
//    void totalScoreForCategoryForUser() {
//    }

    @Test
    void totalScoreForUser() {
        user = User.builder().username("Bob").password("123").build();
        user.setLocalProduces(new HashSet<>(Collections.singleton(calculatorService.calculateLocalProduce(localProduce))));
        user.setVegetarianMeals(new HashSet<>(Collections.singleton(calculatorService.calculateVegetarianMeal(vegetarianMeal))));
        user.setPublicTransports(new HashSet<>(Collections.singleton(calculatorService.calculatePublicTransport(publicTransport))));
        user.setTravelByBikes(new HashSet<>(Collections.singleton(calculatorService.calculateTravelByBike(travelByBike))));
        user.setSolarPanels(new HashSet<>(Collections.singleton(calculatorService.calculateSolarPanel(solarPanel))));
        user.setLowerTemperatures(new HashSet<>(Collections.singleton(calculatorService.calculateLowerTemperature(lowerTemperature))));

        assertEquals(3685, (long) calculatorService.totalScoreForUser(user));
    }

    @Test
    void checkForBadgesForUser() {
        localProduce.setAmount(5000);
        vegetarianMeal.setAmount(6000);

        solarPanel.setAmount(12000);
        lowerTemperature.setAmount(14000);

        user = User.builder().username("Bob").password("123").build();
        user.setLocalProduces(new HashSet<>(Collections.singleton(localProduce)));
        user.setVegetarianMeals(new HashSet<>(Collections.singleton(vegetarianMeal)));
        user.setPublicTransports(new HashSet<>(Collections.singleton(publicTransport)));
        user.setTravelByBikes(new HashSet<>(Collections.singleton(travelByBike)));
        user.setSolarPanels(new HashSet<>(Collections.singleton(solarPanel)));
        user.setLowerTemperatures(new HashSet<>(Collections.singleton(lowerTemperature)));

        Achievement achievementFood = Achievement.builder().category(Category.builder().description("Food").build()).build();
        Achievement achievementTransport = Achievement.builder().category(Category.builder().description("Transport").build()).build();
        Achievement achievementEnergy = Achievement.builder().category(Category.builder().description("Energy").build()).build();

        user.setAchievements(new HashSet<>());
        user.addAchievement(achievementFood);
        user.addAchievement(achievementTransport);
        user.addAchievement(achievementEnergy);

        when(badgeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(badgeBronze));
        when(badgeRepository.findById((long) 2)).thenReturn(Optional.ofNullable(badgeSilver));
        when(badgeRepository.findById((long) 3)).thenReturn(Optional.ofNullable(badgeGold));

        calculatorService.checkForBadgesForUser(user);

        for(Achievement achievement: user.getAchievements()){
            if (achievement.getCategory().getDescription().equals("Food")) {
                assertEquals("silver", achievement.getBadge().getName());
            } else if (achievement.getCategory().getDescription().equals("Energy")) {
                assertEquals("gold", achievement.getBadge().getName());
            } else if (achievement.getCategory().getDescription().equals("Transport")) {
                assertEquals("bronze", achievement.getBadge().getName());
            }
        }

        verify(badgeRepository, times(3)).findById(any(long.class));
        verifyNoMoreInteractions(badgeRepository);
    }
}