package server.entity.habit.energy.subcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import server.entity.habit.energy.Energy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LowerTemperature entity which corresponds to
 * 'lower_temperatures' table in DB.
 */
@Entity
@Table(name = "lower_temperatures")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LowerTemperature extends Energy {

    /**
     * How many degrees the temperature is lowered.
     */
    private double degrees;
}
