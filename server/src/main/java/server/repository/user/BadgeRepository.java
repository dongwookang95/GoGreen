package server.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.user.Badge;

/**
 * Repository used to do transactions on 'badges' table.
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

}
