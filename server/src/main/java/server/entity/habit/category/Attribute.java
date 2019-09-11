package server.entity.habit.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.AuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Attribute entity which corresponds to 'attributes' table in DB.
 */
@Entity
@Table(name = "attributes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attribute extends AuditEntity {

    /**
     * Description of the attribute.
     */
    private String description;

    /**
     * Type of the attribute.
     */
    @Column(name = "attribute_type")
    private String type;

    @Override
    public String toString() {
        return "test";
    }

}
