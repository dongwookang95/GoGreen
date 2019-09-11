package backend.entity;

import backend.entity.habit.structure.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {
    private Category category;
    private Badge badge;
}
