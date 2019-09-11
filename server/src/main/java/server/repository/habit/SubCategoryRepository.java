package server.repository.habit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.habit.category.SubCategory;

/**
 * Repository used to do transactions on 'subcategories' table.
 */
@Repository
public interface SubCategoryRepository extends
                    JpaRepository<SubCategory, Long> {

}
