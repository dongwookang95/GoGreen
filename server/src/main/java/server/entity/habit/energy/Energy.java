package server.entity.habit.energy;

import lombok.Getter;
import lombok.Setter;
import server.entity.habit.Habit;

import javax.persistence.MappedSuperclass;

/**
 * Abstract class for all energy habits, with the attributes
 * they have in common.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Energy extends Habit {

    /**
     * Number of hours active.
     */
    private double hours;
}
