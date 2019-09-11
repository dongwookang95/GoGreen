package server.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.user.Achievement;

/**
 * Repository used to do transactions on 'achievements' table.
 */
@Repository
public interface AchievementRepository extends
                    JpaRepository<Achievement, Long> {

}
