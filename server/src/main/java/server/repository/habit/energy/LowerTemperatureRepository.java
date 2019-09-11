package server.repository.habit.energy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.energy.subcategory.LowerTemperature;

/**
 * Repository used to do transactions on 'lower_temperatures' table.
 */
@Repository
public interface LowerTemperatureRepository extends
                    JpaRepository<LowerTemperature, Long> {

}
