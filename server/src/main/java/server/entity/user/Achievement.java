package server.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.entity.AuditEntity;
import server.entity.habit.category.Category;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Achievement entity which corresponds to 'achievements' table in DB.
 */
@Entity
@Table(name = "achievements")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement extends AuditEntity {

    /**
     * Badge corresponding to this achievement.
     */
    @ManyToOne
    @JoinColumn(name = "badge_id")
    @JsonIgnoreProperties("achievements")
    private Badge badge;

    /**
     * Category corresponding to this achievement.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("subCategories")
    private Category category;

    /**
     * User corresponding to this achievement.
     */
    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;
}
