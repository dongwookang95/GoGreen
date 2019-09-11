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
 * TravelByBike entity which corresponds to 'travel_by_bike'
 * table in DB.
 */
@Entity
@Table(name = "travel_by_bikes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TravelByBike extends Transport {

    /**
     * Transport type that was replaced by bike.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type_instead")
    private TransportType transportTypeInstead;
}
