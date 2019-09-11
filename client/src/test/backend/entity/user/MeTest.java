package backend.entity.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeTest {

    private Me test;

    @BeforeEach
    void setUp() {
        test = new Me();
    }

    @Test
    void getSetRole() {
        test.setRole("User");
        assertEquals("User", test.getRole());
    }

    @Test
    void enabled() {
        test.setEnabled(true);
        assertTrue(test.isEnabled());
    }

    @Test
    void accountNonExpired() {
        test.setAccountNonExpired(true);
        assertTrue(test.isAccountNonExpired());
    }

    @Test
    void accountNonLocked() {
        test.setAccountNonLocked(true);
        assertTrue(test.isAccountNonLocked());
    }

    @Test
    void credentialsNonExpired() {
        test.setCredentialsNonExpired(true);
        assertTrue(test.isCredentialsNonExpired());
    }

    @Test
    void getFriends() {
        assertEquals(new ArrayList<Friend>(), test.getFriends());
    }

    @Test
    void setFriends() {
        ArrayList<Friend> diff = new ArrayList<>();
        Friend f = new Friend();
        diff.add(f);
        test.setFriends(diff);
        assertEquals(diff, test.getFriends());
    }

    @Test
    void getSetInvites() {
        ArrayList<Friend> diff = new ArrayList<>();
        Friend f = new Friend();
        diff.add(f);
        test.setInvites(diff);
        assertEquals(diff, test.getInvites());
    }

    @Test
    void getSetRequests() {
        ArrayList<Friend> diff = new ArrayList<>();
        Friend f = new Friend();
        diff.add(f);
        test.setRequests(diff);
        assertEquals(diff, test.getRequests());
    }

    @Test
    void getFriendsAsUsers() {
        ArrayList<Friend> friends = new ArrayList<>();
        Friend f1 = new Friend("andy");
        Friend f2 = new Friend("jan");
        friends.add(f1);
        friends.add(f2);

        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("andy");
        User u2 = new User("jan");
        users.add(u1);
        users.add(u2);

        test.setFriends(friends);
        assertEquals(users, test.getFriendsAsUsers());
    }

}