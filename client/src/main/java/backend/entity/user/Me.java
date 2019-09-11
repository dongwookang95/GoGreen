package backend.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Class defining the user currently logged in.
 */
@NoArgsConstructor
@Getter
@Setter
public final class Me extends Friend {
    private String role;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    private ArrayList<Friend> friends = new ArrayList<>();
    private ArrayList<Friend> invites = new ArrayList<>();
    private ArrayList<Friend> requests = new ArrayList<>();

    /**
     * Method to convert friend list into user list (GUI overview).
     * @return List of users
     */
    public ArrayList<User> getFriendsAsUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (Friend friend : friends) {
            users.add(new User(friend.getUsername()));
        }
        return users;
    }
}
