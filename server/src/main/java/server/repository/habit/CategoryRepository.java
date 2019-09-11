package server.repository.habit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.category.Category;

/**
 * Repository used to do transactions on 'categories' table.
 */
@Repository
public interface CategoryRepository extends
                        JpaRepository<Category, Long> {

}
