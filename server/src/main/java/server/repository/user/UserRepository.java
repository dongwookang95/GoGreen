package server.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.user.User;

/**
 * Repository used to do transactions on 'users' table.
 */
@Repository
public interface UserRepository extends
                        JpaRepository<User, String> {

}
