package server.service.habit.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entity.habit.transport.subcategory.PublicTransport;
import server.entity.habit.transport.subcategory.TravelByBike;
import server.exception.habit.transport.PublicTransportNotFoundException;
import server.exception.habit.transport.TravelByBikeNotFoundException;
import server.repository.habit.transport.PublicTransportRepository;
import server.repository.habit.transport.TravelByBikeRepository;

/**
 * Service for all operations concerning objects with category 'Transport'.
 */
@Service
public class TransportService implements ITransportService {

    /**
     * Autowired PublicTransportRepository to use its functions.
     */
    private final PublicTransportRepository publicTransportRepository;

    /**
     * Autowired TravelByBikeRepository to use its functions.
     */
    private final TravelByBikeRepository travelByBikeRepository;

    /**
     * Injecting repositories into service.
     * @param publicTransportRepository PublicTransportRepository
     * @param travelByBikeRepository TravelByBikeRepository
     */
    @Autowired
    public TransportService(PublicTransportRepository publicTransportRepository,
                            TravelByBikeRepository travelByBikeRepository) {
        this.publicTransportRepository = publicTransportRepository;
        this.travelByBikeRepository = travelByBikeRepository;
    }


    //  [PUBLIC TRANSPORT]

    /**
     * Create a new PublicTransport in DB.
     * @param publicTransport PublicTransport that needs to be created
     * @return PublicTransport that is created
     */
    @Override
    public PublicTransport createPublicTransport(PublicTransport publicTransport) {
        return publicTransportRepository.save(publicTransport);
    }

    /**
     * Find a specific PublicTransport in DB.
     * @param id Id that refers to PublicTransport.
     * @return Optional PublicTransport
     */
    @Override
    public PublicTransport findPublicTransportById(Long id) {
        return publicTransportRepository.findById(id).orElseThrow(() ->
                new PublicTransportNotFoundException(id));
    }

    /**
     * Delete a PublicTransport in DB.
     * @param publicTransport LocalProduce that needs to be deleted
     */
    @Override
    public void deletePublicTransport(PublicTransport publicTransport) {
        publicTransportRepository.delete(publicTransport);
    }

    /**
     * Update a PublicTransport in DB.
     * @param publicTransport PublicTransport that needs to be updated.
     */
    @Override
    public void updatePublicTransport(PublicTransport publicTransport) {
        publicTransportRepository.save(publicTransport);
    }


    //  [TRAVEL BY BIKE]

    /**
     * Create a new TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be created
     * @return TravelByBike that is created
     */
    @Override
    public TravelByBike createTravelByBike(TravelByBike travelByBike) {
        return travelByBikeRepository.save(travelByBike);
    }

    /**
     * Find a specific TravelByBike in DB.
     * @param id Id that refers to TravelByBike.
     * @return Optional TravelByBike
     */
    @Override
    public TravelByBike findTravelByBikeById(Long id) {
        return travelByBikeRepository.findById(id).orElseThrow(() ->
                new TravelByBikeNotFoundException(id));
    }

    /**
     * Delete a TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be deleted
     */
    @Override
    public void deleteTravelByBike(TravelByBike travelByBike) {
        travelByBikeRepository.delete(travelByBike);
    }

    /**
     * Update a TravelByBike in DB.
     * @param travelByBike TravelByBike that needs to be updated
     */
    @Override
    public void updateTravelByBike(TravelByBike travelByBike) {
        travelByBikeRepository.save(travelByBike);
    }
}
