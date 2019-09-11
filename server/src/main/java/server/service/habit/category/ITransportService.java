package server.service.habit.category;

import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;

/**
 * Interface to define functions in TransportService.
 */
public interface ITransportService {

    //  [PUBLIC TRANSPORT]

    /**
     * Create a new PublicTransport in DB.
     * @param publicTransport PublicTransport that needs to be created
     * @return PublicTransport that is created
     */
    PublicTransport createPublicTransport(PublicTransport publicTransport);

    /**
     * Find a specific PublicTransport in DB.
     * @param id Id that refers to PublicTransport.
     * @return Optional PublicTransport
     */
    PublicTransport findPublicTransportById(Long id);

    /**
     * Delete a PublicTransport in DB.
     * @param publicTransport LocalProduce that needs to be deleted
     */
    void deletePublicTransport(PublicTransport publicTransport);

    /**
     * Update a PublicTransport in DB.
     * @param publicTransport PublicTransport that needs to be updated.
     */
    void updatePublicTransport(PublicTransport publicTransport);


    //  [TRAVEL BY BIKE]


    /**
     * Create a new TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be created
     * @return TravelByBike that is created
     */
    TravelByBike createTravelByBike(TravelByBike travelByBike);

    /**
     * Find a specific TravelByBike in DB.
     * @param id Id that refers to TravelByBike.
     * @return Optional TravelByBike
     */
    TravelByBike findTravelByBikeById(Long id);

    /**
     * Delete a TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be deleted
     */
    void deleteTravelByBike(TravelByBike travelByBike);

    /**
     * Update a TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be updated
     */
    void updateTravelByBike(TravelByBike travelByBike);
}
