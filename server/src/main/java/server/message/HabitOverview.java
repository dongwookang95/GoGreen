package server.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Object used to give the client an overview per habit.
 */
@Getter
@AllArgsConstructor
public class HabitOverview {

    /**
     * Description of the habit.
     */
    private String description;

    /**
     * Total amount of CO2 saved by this habit.
     */
    private double total;
}
