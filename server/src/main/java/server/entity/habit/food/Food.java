package server.entity.habit.food;

import lombok.Getter;
import lombok.Setter;
import server.entity.habit.Habit;

import javax.persistence.MappedSuperclass;

/**
 * Abstract class for all food habits, with the attributes they have in common.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Food extends Habit {

}
