package server.repository.habit.energy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.energy.subcategory.SolarPanel;

/**
 * Repository used to do transactions on 'solar_panels' table.
 */
@Repository
public interface SolarPanelRepository extends
                    JpaRepository<SolarPanel, Long> {
}
