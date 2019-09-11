package backend.entity;

import backend.entity.habit.structure.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Badge {
    private String name;
    private String path;

    public Badge(String name) {
        this.name = name;
    }

    public void setPath(Category category) {
        String categoryName = category.getDescription().toLowerCase();
        path = String.format("%s_%s", categoryName, name);
    }
}
