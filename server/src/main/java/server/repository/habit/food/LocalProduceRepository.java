package server.repository.habit.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.food.subcategory.LocalProduce;

/**
 * Repository used to do transactions on 'local_produces' table.
 */
@Repository
public interface LocalProduceRepository extends
                    JpaRepository<LocalProduce, Long> {

}
