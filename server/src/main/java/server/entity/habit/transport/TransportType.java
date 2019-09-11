package server.entity.habit.transport;

import com.fasterxml.jackson.annotation.JsonValue;
import server.config.ConstantsConfig;
import server.exception.habit.transport.TransportTypeNotFoundException;

public enum TransportType {
    CAR("Car", ConstantsConfig.TRANSPORT_CAR),
    TRAIN("Train", ConstantsConfig.TRANSPORT_TRAIN),
    BUS("Bus", ConstantsConfig.TRANSPORT_BUS),
    TRAM("Tram", ConstantsConfig.TRANSPORT_TRAM),
    METRO("Metro", ConstantsConfig.TRANSPORT_METRO);

    private String name;
    private double emission;


    TransportType(String name, double emission) {
        this.name = name;
        this.emission = emission;
    }

    /**
     * Get the transport type enumeration from a String.
     * @param text String referring to transport type
     * @return TransportType or throws exception
     */
    public static TransportType fromString(String text) {
        for (TransportType transportType : TransportType.values()) {
            if (transportType.name.equalsIgnoreCase(text)) {
                return transportType;
            }
        }
        throw new TransportTypeNotFoundException(text);
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public double getEmission() {
        return emission;
    }
}
