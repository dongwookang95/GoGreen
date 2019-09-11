package server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.entity.AuditEntity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Badge entity which corresponds to 'badges' table in DB.
 */
@Entity
@Table(name = "badges")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Badge extends AuditEntity {

    /**
     * Name of the badge; wood, bronze, silver, gold or diamond.
     */
    private String name;

    /**
     * Achievements this badge belongs to.
     */
    @OneToMany(mappedBy = "badge")
    @JsonIgnoreProperties("badge")
    private Set<Achievement> achievements;
}
