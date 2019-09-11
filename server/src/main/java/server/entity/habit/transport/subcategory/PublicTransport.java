package server.entity.habit.transport.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.habit.transport.Transport;
import server.entity.habit.transport.TransportType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * PublicTransport entity which corresponds to 'public_transports'
 * table in DB.
 */
@Entity
@Table(name = "public_transports")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublicTransport extends Transport {

    /**
     * Transport type that was replaced by public transport.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type_instead")
    private TransportType transportTypeInstead;

    /**
     * Transport type that was actually used.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type_actual")
    private TransportType transportTypeActual;
}
