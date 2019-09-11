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
import server.entity.habit.transport.TransportType;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.PublicTransportForm;
import server.entity.habit.transport.subcategory.TravelByBike;
import server.entity.habit.transport.subcategory.TravelByBikeForm;
import server.entity.user.User;
import server.service.calculator.CalculatorService;
import server.service.habit.CategoryService;
import server.service.habit.category.TransportService;
import server.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles all the requests concerning objects with category 'Transport'.
 */
@RestController
@RequestMapping("/transport")
public class TransportController {

    /**
     * Autowired TransportProduceService to use its functionality.
     */
    private final TransportService transportService;

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
     * @param transportService TransportService
     * @param userService UserService
     * @param categoryService CategoryService
     * @param calculatorService CalculatorService
     */
    @Autowired
    public TransportController(TransportService transportService,
                               UserService userService,
                               CategoryService categoryService,
                               CalculatorService calculatorService) {
        this.transportService = transportService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.calculatorService = calculatorService;
    }

    //  [GET MAPPING]

    /**
     * Get all PublicTransport objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return user's public transports
     */
    @GetMapping("/public_transport")
    public ResponseEntity getPublicTransportsForUser(@AuthenticationPrincipal
                                             final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getPublicTransports());
    }

    /**
     * Get all TravelByBike objects of a specific user.
     * @param userDetails automatically gets sent along by JWT token header
     * @return user's travels by bike
     */
    @GetMapping("/travel_by_bike")
    public ResponseEntity getTravelByBikesForUser(@AuthenticationPrincipal
                                              final UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ok(user.getTravelByBikes());
    }


    //  [POST MAPPING]

    /**
     * Create a new PublicTransport object DB.
     * @param form PublicTransport that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return public transport
     */
    @PostMapping("/public_transport")
    public ResponseEntity addPublicTransport(@RequestBody final PublicTransportForm form,
                                             final HttpServletRequest request,
                                 @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById((long) 2);
        SubCategory subCategory = categoryService.findSubCategoryById((long) 3);

        PublicTransport publicTransport = PublicTransport.builder()
                .transportTypeInstead(TransportType.fromString(form.getTransportTypeInstead()))
                .transportTypeActual(TransportType.fromString(form.getTransportTypeActual()))
                .build();

        publicTransport.setKilometers(form.getKilometers());
        publicTransport.setUser(user);
        publicTransport.setCategory(category);
        publicTransport.setSubCategory(subCategory);

        PublicTransport calculated = calculatorService.calculatePublicTransport(publicTransport);

        PublicTransport saved = transportService.createPublicTransport(calculated);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/transport/public_transport")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    /**
     * Create a new TravelByBike object DB.
     * @param form TravelByBike that needs to be created
     * @param userDetails automatically gets sent along by JWT token header
     * @return travel by bikeS
     */
    @PostMapping("/travel_by_bike")
    public ResponseEntity addTravelByBike(@RequestBody final TravelByBikeForm form,
                                          final HttpServletRequest request,
                              @AuthenticationPrincipal final UserDetails userDetails) {

        User user = userService.findByUsername(userDetails.getUsername());
        Category category = categoryService.findCategoryById((long) 2);
        SubCategory subCategory = categoryService.findSubCategoryById((long) 4);

        TravelByBike travelByBike = TravelByBike.builder()
                .transportTypeInstead(TransportType.fromString(form.getTransportTypeInstead()))
                .build();

        travelByBike.setKilometers(form.getKilometers());
        travelByBike.setUser(user);
        travelByBike.setCategory(category);
        travelByBike.setSubCategory(subCategory);

        TravelByBike calculated = calculatorService.calculateTravelByBike(travelByBike);

        TravelByBike saved = transportService.createTravelByBike(calculated);

        return created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/v1/food/travel_by_bike")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }


    //  [PUT MAPPING]

    /**
     * Update PublicTransport properties in DB.
     * @param form PublicTransport object containing the new properties
     * @param id Path variable PublicTransport id corresponding to
     *           PublicTransport object that needs to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/public_transport/{id}")
    public ResponseEntity updatePublicTransport(@RequestBody final PublicTransportForm form,
                                                @PathVariable("id") final Long id,
                                @AuthenticationPrincipal final UserDetails userDetails) {

        PublicTransport publicTransport = transportService.findPublicTransportById(id);

        if (!publicTransport.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        publicTransport.setKilometers(form.getKilometers());

        publicTransport.setTransportTypeInstead(
                TransportType.fromString(form.getTransportTypeInstead())
        );
        publicTransport.setTransportTypeActual(
                TransportType.fromString(form.getTransportTypeActual())
        );

        PublicTransport calculated = calculatorService.calculatePublicTransport(publicTransport);

        transportService.updatePublicTransport(calculated);
        return noContent().build();
    }

    /**
     * Update TravelByBike properties in DB.
     * @param form TravelByBike object containing the new properties
     * @param id Path variable TravelByBike id corresponding to TravelByBike
     *           that needs to be updated
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @PutMapping("/travel_by_bike/{id}")
    public ResponseEntity updateTravelByBike(@RequestBody final TravelByBikeForm form,
                                             @PathVariable("id") final Long id,
                             @AuthenticationPrincipal final UserDetails userDetails) {

        TravelByBike travelByBike = transportService.findTravelByBikeById(id);

        if (!travelByBike.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        travelByBike.setKilometers(form.getKilometers());
        travelByBike.setTransportTypeInstead(
                TransportType.fromString(form.getTransportTypeInstead())
        );

        TravelByBike calculated = calculatorService.calculateTravelByBike(travelByBike);

        transportService.updateTravelByBike(calculated);
        return noContent().build();
    }

    //  [DELETE MAPPING]

    /**
     * Delete a PublicTransport in DB.
     * @param id PublicTransport id referring to PublicTransport that needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/public_transport/{id}")
    public ResponseEntity deletePublicTransport(@PathVariable("id") final Long id,
                        @AuthenticationPrincipal final UserDetails userDetails) {
        PublicTransport publicTransport = transportService.findPublicTransportById(id);

        if (!publicTransport.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        transportService.deletePublicTransport(publicTransport);
        return noContent().build();
    }

    /**
     * Delete a TravelByBike in DB.
     * @param id TravelByBike id referring to TravelByBike that needs to be deleted
     * @param userDetails automatically gets sent along by JWT token header
     * @return unauthorized / no content
     */
    @DeleteMapping("/travel_by_bike/{id}")
    public ResponseEntity deleteTravelByBike(@PathVariable("id") final Long id,
                     @AuthenticationPrincipal final UserDetails userDetails) {
        TravelByBike travelByBike = transportService.findTravelByBikeById(id);

        if (!travelByBike.getUser().getUsername().equals(userDetails.getUsername())) {
            return status(UNAUTHORIZED).build();
        }

        transportService.deleteTravelByBike(travelByBike);
        return noContent().build();
    }
}
