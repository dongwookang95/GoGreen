package server.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass for all DB entities (except User) to reduce redundant code.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity implements Serializable {

    /**
     * Id for entity, generated automatically, is primary key in DB.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Created date to keep track of when an entity is created.
     */
    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate;

    /**
     * Last modified date to keep track of when an entity is last modified.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;
}
