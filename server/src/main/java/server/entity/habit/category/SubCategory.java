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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SubCategory entity which corresponds to 'subcategory' table in DB.
 */
@Entity
@Table(name = "subcategories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCategory extends AuditEntity {

    /**
     * Description of the subcategory.
     */
    private String description;

    /**
     * Main category of this subcategory.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("subCategories")
    private Category category;

    /**
     * Set of attributes of this subcategory.
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "subcategories_attributes",
            joinColumns = { @JoinColumn(name = "subcategory_id") },
            inverseJoinColumns = { @JoinColumn(name = "attribute_id") } )
    private Set<Attribute> attributes = new HashSet<>();



    @Override
    public String toString() {
        return "test";
    }

}
