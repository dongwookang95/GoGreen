package server.entity.habit.transport.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Object used to retrieve data from client requests.
 * This object contains all attributes to create and
 * calculate a PublicTransport entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicTransportForm {

    /**
     * Number of kilometers travelled.
     */
    private double kilometers;

    /**
     * Transport type that was replaced by public transport.
     */
    private String transportTypeInstead;

    /**
     * Transport type that was actually used.
     */
    private String transportTypeActual;
}
