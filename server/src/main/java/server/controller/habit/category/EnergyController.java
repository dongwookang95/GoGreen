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
import server.entity.habit.energy.subcategory.LowerTemperature;
import server.entity.habit.energy.subcategory.LowerTemperatureForm;
import server.entity.habit.energy.subcategory.SolarPanel;
import server.entity.habit.energy.subcategory.SolarPanelForm;
import server.entity.user.User;
import server.service.calculator.CalculatorService;
import server.service.habit.CategoryService;
import server.service.habit.category.EnergyService;
import server.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles all the requests concerning objects with category 'Energy'.
 */
@RestController
@RequestMapping("/energy")
public class EnergyController {

    private static final long ENERGY_CONTROLLER_ID = 3;
    private static final long LOWER_TEMPERATURE_ID = 6;
    private static final long SOLAR_PANEL_ID = 5;

    /**
     * Autowired EnergyService to use its functionality.
     */
    private final EnergyService energyService;

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
     * @param energyService EnergyService
     * @param userService UserService
     * @param categoryService CategoryService
     * @param calculatorService CalculatorService
     */
    @Autowired
    public EnergyController(EnergyService energyService,
                            UserService userService,
                            CategoryService categoryService,
                            CalculatorService calculatorService) {
        this.energyService = energyService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.calculatorService = calculatorService;
    }

    //  [GET MAPPING]

    /**
     * Get all LowerTemperature objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return lower temperatures
     */
    @GetMapping("/lower_temperature")
    public ResponseEntity getLowerTemperatureForUser(
            @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getLowerTemperatures());
    }

    /**
     * Get all SolarPanel objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return solar panels
     */
    @GetMapping("/solar_panel")
    public ResponseEntity getSolarPanels(
            @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getSolarPanels());
    }


    //  [POST MAPPING]

    /**
     * Create a new LowerTemperature object DB.
     * @param form LowerTemperature that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return created lower temperature
     */
    @PostMapping("/lower_temperature")
    public ResponseEntity addLowerTemperature(@RequestBody
                          LowerTemperatureForm form,
                          HttpServletRequest request,
        @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById(ENERGY_CONTROLLER_ID);
        SubCategory subCategory = categoryService.findSubCategoryById(LOWER_TEMPERATURE_ID);

        LowerTemperature lowerTemperature = LowerTemperature.builder()
                .degrees(form.getDegrees())
                .build();

        lowerTemperature.setUser(user);
        lowerTemperature.setHours(form.getHours());
        lowerTemperature.setCategory(category);
        lowerTemperature.setSubCategory(subCategory);

        LowerTemperature calculated = calculatorService.calculateLowerTemperature(lowerTemperature);

        LowerTemperature saved =
                energyService.createLowerTemperature(calculated);



        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/energy/lower_temperature")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    /**
     * Create a new SolarPanel object DB.
     * @param form SolarPanel that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return created solar panel
     */
    @PostMapping("/solar_panel")
    public ResponseEntity addSolarPanel(@RequestBody final SolarPanelForm form,
                                        final HttpServletRequest request,
                    @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById(ENERGY_CONTROLLER_ID);
        SubCategory subCategory = categoryService.findSubCategoryById(SOLAR_PANEL_ID);

        SolarPanel solarPanel = SolarPanel.builder()
                .watt(form.getWatt())
                .numberOfSolarPanels(form.getNumberOfSolarPanels())
                .build();

        solarPanel.setHours(form.getHours());
        solarPanel.setUser(user);
        solarPanel.setCategory(category);
        solarPanel.setSubCategory(subCategory);

        SolarPanel calculated = calculatorService.calculateSolarPanel(solarPanel);

        SolarPanel saved = energyService.createSolarPanel(calculated);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/energy/solar_panel")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    //  [PUT MAPPING]

    /**
     * Update LowerTemperature properties in DB.
     * @param form LowerTemperature  object containing the new properties
     * @param id Path variable LowerTemperature  id corresponding to
     *           LowerTemperature  object that needs to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/lower_temperature/{id}")
    public ResponseEntity updateLowerTemperature(
            @RequestBody final LowerTemperatureForm form,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal final UserDetails userDetails) {

        LowerTemperature lowerTemperature  = energyService.findLowerTemperatureById(id);

        if (!lowerTemperature.getUser().getUsername().equals(userDetails
                                                    .getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        lowerTemperature.setHours(form.getHours());
        lowerTemperature.setDegrees(form.getDegrees());

        LowerTemperature calculated = calculatorService.calculateLowerTemperature(lowerTemperature);

        energyService.updateLowerTemperature(calculated);
        return noContent().build();
    }

    /**
     * Update SolarPanel properties in DB.
     * @param form SolarPanel object containing the new properties
     * @param id Path variable SolarPanel id corresponding to SolarPanel
     *           that needs to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/solar_panel/{id}")
    public ResponseEntity updateSolarPanel(
            @RequestBody final SolarPanelForm form,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal final UserDetails userDetails) {

        SolarPanel solarPanel = energyService.findSolarPanelById(id);

        if (!solarPanel.getUser().getUsername().equals(userDetails
                                                .getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        solarPanel.setHours(form.getHours());
        solarPanel.setNumberOfSolarPanels(form.getNumberOfSolarPanels());
        solarPanel.setWatt(form.getWatt());

        SolarPanel calculated = calculatorService.calculateSolarPanel(solarPanel);

        energyService.updateSolarPanel(calculated);
        return noContent().build();
    }


    //  [DELETE MAPPING]

    /**
     * Delete a LowerTemperature  in DB.
     * @param id LowerTemperature  id referring to LowerTemperature  that
     *           needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/lower_temperature/{id}")
    public ResponseEntity deleteLowerTemperature(
            @PathVariable("id") final Long id,
        @AuthenticationPrincipal final UserDetails userDetails) {

        LowerTemperature lowerTemperature = energyService
                    .findLowerTemperatureById(id);

        if (!lowerTemperature.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        energyService.deleteLowerTemperature(lowerTemperature);
        return noContent().build();
    }

    /**
     * Delete a SolarPanel in DB.
     * @param id SolarPanel id referring to SolarPanel that needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/solar_panel/{id}")
    public ResponseEntity deleteSolarPanel(@PathVariable("id") final Long id,
                   @AuthenticationPrincipal final UserDetails userDetails) {

        SolarPanel solarPanel = energyService.findSolarPanelById(id);

        if (!solarPanel.getUser().getUsername().equals(userDetails
                                                .getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        energyService.deleteSolarPanel(solarPanel);
        return noContent().build();
    }
}
