package server.entity.habit.transport;

import lombok.Getter;
import lombok.Setter;
import server.entity.habit.Habit;

import javax.persistence.MappedSuperclass;

/**
 * Abstract class for all transport habits, with the attributes
 * they have in common.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Transport extends Habit {

    /**
     * Number of kilometers travelled.
     */
    private double kilometers;
}
