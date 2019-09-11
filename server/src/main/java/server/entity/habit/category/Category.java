package server.entity.habit.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.AuditEntity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Category entity which corresponds to 'categories' table in DB.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category extends AuditEntity {

    /**
     * Description of the category.
     */
    private String description;

    /**
     * Set of subcategories of this category.
     */
    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    private Set<SubCategory> subCategories = new HashSet<>();

    @Override
    public String toString() {
        return "test";
    }
}
