package backend.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class defining a user.
 */
@NoArgsConstructor
@Getter
@Setter
public class User {
    protected String username;

    /**
     * Constructor for the user.
     *
     * @param username Username of the user
     */
    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User that = (User) other;
            if (this.getUsername() == null && that.getUsername() == null) {
                return true;
            }
            return this.getUsername().equals(that.getUsername());
        }
        return false;
    }

}
