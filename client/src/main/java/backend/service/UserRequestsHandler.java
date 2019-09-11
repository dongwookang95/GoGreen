package backend.service;

import backend.entity.user.Friend;
import backend.entity.user.Me;
import backend.entity.user.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class UserRequestsHandler {

    private String url = "https://ooppgogreen99.herokuapp.com/v1/user/";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthHeader auth = new AuthHeader();

    private Me me = retrieveMe();
    private ArrayList<User> users = retrieveAllUsers();

    private UserRequestsHandler() { }

    private static class UserRequestsHelper {
        private static final UserRequestsHandler INSTANCE = new UserRequestsHandler();
    }
    
    /**
     * Needed for testing, because WireMock will use localhost
     * and not the real website.
     *
     * @param url of localhost
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public static UserRequestsHandler getInstance() {
        return UserRequestsHelper.INSTANCE;
    }

    /**
     * This method retrieves all users and their habits from server and populate a list.
     */
    protected ArrayList<User> retrieveAllUsers() {
        //Request all users as JsonNode to server
        JsonNode allUsersJson = restTemplate.exchange(
                url + "all", HttpMethod.GET,
                auth.makeEntity(auth.makeHeader()), JsonNode.class).getBody();
        ArrayList<User> list = new ArrayList<>();

        // Loop through the JsonNode tree and create a list of users
        if (allUsersJson != null) {
            for (int i = 0; i < allUsersJson.size(); i++) {
                User user = new User();
                user.setUsername(allUsersJson.get(i).asText());
                list.add(user);
            }
        }
        return list;
    }

    /**
     * Helper method to reload the current user instance.
     */
    public void reloadMeInstance() {
        me = retrieveMe();
    }

    /**
     * Get information about current user that is logged in.
     *
     * @return Me object
     */
    protected Me retrieveMe() {

        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        JsonNode meJson = restTemplate
            .exchange(url + "me", HttpMethod.GET, entity, JsonNode.class).getBody();
        String meString = meJson != null ? meJson.toString() : "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Me me = new Me();
        try {
            me = objectMapper.readValue(meString, Me.class);
        } catch (IOException e) {
            System.out.println("IO Exception while creating current user");
        }

        me.calculateTotalAmounts();
        me.setAchievementPaths();
        ArrayList<Friend> friends = me.getFriends();
        for (Friend friend : friends) {
            friend.calculateTotalAmounts();
            friend.setAchievementPaths();
        }

        return me;
    }

    /**
     * Method sending a friend request from current user to the user with given username.
     * Adds the Friend to current user's requests list (blame Jan for the confusion).
     * @param username Friend to whom the friend request is sent from current user
     */
    public void sendRequest(String username) {
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        restTemplate
            .exchange(url + "invite/" + username, HttpMethod.PUT, entity, Void.class);
        me.getRequests().add(new Friend(username));
    }

    /**
     * Method accepting a friend request from specified user.
     * Removes the Friend from current user's list of invites
     * (aka people who sent the friend request to current user).
     * @param friend Friend to be accepted
     */
    public void acceptRequest(Friend friend) {
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode friendJson = restTemplate.exchange(url + "accept/" + friend.getUsername(),
                HttpMethod.PUT, entity, JsonNode.class).getBody();
        String friendString;
        if (friendJson != null) {
            friendString = friendJson.toString();
            try {
                friend = objectMapper.readValue(friendString, Friend.class);
            } catch (IOException e) {
                System.out.println("Could not accept invitation.");
            }
        }
        me.getInvites().remove(friend);
        me.getFriends().add(friend);
    }

    /**
     * Method declining a friend request from specified user.
     * Removes the Friend from current user's list of requests.
     * @param friend Friend whom request is to be declined.
     */
    public void declineRequest(Friend friend) {
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        restTemplate.exchange(url + "friend/" + friend.getUsername(),
                HttpMethod.DELETE, entity, Void.class);
        me.getInvites().remove(friend);
    }

    /**
     * Method deleting a friend from current user friend list.
     * @param friend The friend to be managed.
     */
    public void deleteFriend(Friend friend) {
        HttpEntity entity = auth.makeEntity(auth.makeHeader());
        restTemplate.exchange(url + "friend/" + friend.getUsername(),
                HttpMethod.DELETE, entity, Void.class);
        me.getFriends().remove(friend);
    }
}
