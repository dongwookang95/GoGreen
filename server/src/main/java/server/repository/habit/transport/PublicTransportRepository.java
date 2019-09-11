package server.repository.habit.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.transport.subcategory.PublicTransport;

/**
 * Repository used to do transactions on 'public_transports' table.
 */
@Repository
public interface PublicTransportRepository extends JpaRepository<PublicTransport, Long> {

}
