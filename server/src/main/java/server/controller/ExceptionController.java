package server.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.exception.DataBaseException;
import server.exception.InvalidJwtAuthenticationException;
import server.exception.habit.energy.LowerTemperatureNotFoundException;
import server.exception.habit.energy.SolarPanelNotFoundException;
import server.exception.habit.food.LocalProduceNotFoundException;
import server.exception.habit.food.VegetarianMealNotFoundException;
import server.exception.habit.transport.PublicTransportNotFoundException;
import server.exception.habit.transport.TransportTypeNotFoundException;
import server.exception.habit.transport.TravelByBikeNotFoundException;
import server.exception.user.UserNotFoundException;

/**
 * Handles all exceptions thrown by other controllers or functions.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * Handling of UserNotFoundException.
     * @param ex UserNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity userNotFound(final UserNotFoundException ex) {

        log.debug("User not found: " + ex.getUsername());
        return notFound().build();
    }

    /**
     * Handling of LocalProduceNotFoundException.
     * @param ex LocalProduceNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {LocalProduceNotFoundException.class})
    public ResponseEntity localProduceNotFound(
            final LocalProduceNotFoundException ex) {

        log.debug("LocalProduce not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of VegetarianMealNotFoundException.
     * @param ex VegetarianMealNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {VegetarianMealNotFoundException.class})
    public ResponseEntity vegetarianMealNotFound(
            final VegetarianMealNotFoundException ex) {

        log.debug("VegetarianMeal not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of SolarPanelNotFoundException.
     * @param ex SolarPanelNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {SolarPanelNotFoundException.class})
    public ResponseEntity solarPanelNotFound(
            final SolarPanelNotFoundException ex) {

        log.debug("SolarPanel not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of LoweringTemperatureNotFoundException.
     * @param ex LoweringTemperatureNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {LowerTemperatureNotFoundException.class})
    public ResponseEntity loweringTemperatureNotFound(
            final LowerTemperatureNotFoundException ex) {

        log.debug("LoweringTemperature not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of PublicTransportNotFoundException.
     * @param ex PublicTransportNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {PublicTransportNotFoundException.class})
    public ResponseEntity publicTransportNotFound(
            final PublicTransportNotFoundException ex) {

        log.debug("PublicTransport not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of TravelByBikeNotFoundException.
     * @param ex TravelByBikeNotFoundException that is thrown
     * @return ResponseEntity Not Found
     */
    @ExceptionHandler(value = {TravelByBikeNotFoundException.class})
    public ResponseEntity travelByBikeNotFound(
            final TravelByBikeNotFoundException ex) {

        log.debug("TravelByBike not found: " + ex.getId());
        return notFound().build();
    }

    /**
     * Handling of TransportTypeNotFoundException.
     * @param ex TransportTypeNotFoundException that is thrown
     * @return ResponseEntity Bad Request
     */
    @ExceptionHandler(value = {TransportTypeNotFoundException.class})
    public ResponseEntity transportTypeNotFound(
            final TransportTypeNotFoundException ex) {

        log.debug("TransportType not found: " + ex.getTransportType());
        return badRequest().build();
    }

    /**
     * Handling of DBException.
     * @param ex DBException that is thrown
     * @return ResponseEntity Bad Request
     */
    @ExceptionHandler(value = {DataBaseException.class})
    public ResponseEntity databaseException(final DataBaseException ex) {

        log.debug("DB error: " + ex.getMessage());
        return badRequest().build();
    }

    /**
     * Handling of InvalidJwtAuthenticationException.
     * @return ResponseEntity Not Authorized
     */
    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity invalidJwtAuthentication() {

        return status(UNAUTHORIZED).build();
    }
}
