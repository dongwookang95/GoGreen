package server.repository.habit.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.transport.subcategory.TravelByBike;

/**
 * Repository used to do transactions on 'travel_by_bikes' table.
 */
@Repository
public interface TravelByBikeRepository extends JpaRepository<TravelByBike, Long> {

}
