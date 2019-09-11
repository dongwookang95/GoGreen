package backend.controller.tab;

import backend.controller.HomeController;
import backend.entity.user.Friend;
import backend.entity.user.Me;
import backend.entity.user.User;
import backend.service.UserRequestsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController extends HomeController implements Initializable {
    @FXML
    private HBox horizontalBox;

    //Left side fields
    @FXML
    private TextField userSearchBar;
    @FXML
    private Button userSearchButton;
    @FXML
    private TableView<User> userView;
    @FXML
    private TableColumn<User, String> userUsernameCol;
    @FXML
    private Label userLabel;
    @FXML
    private Button userInviteButton;

    //Mid fields
    @FXML
    private TextField friendSearchBar;
    @FXML
    private Button friendSearchButton;
    @FXML
    private TableView<Friend> friendView;
    @FXML
    private TableColumn<Friend, String> friendUsernameCol;
    @FXML
    private Label friendLabel;
    @FXML
    private Button friendDeleteButton;

    //Right side fields
    @FXML
    private AnchorPane rightSide;

    private UserRequestsHandler userHandler = UserRequestsHandler.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        horizontalBox.setSpacing(25);
        userInviteButton.setVisible(false);
        friendDeleteButton.setVisible(false);
        // Left section event handlers
        userSearchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> searchUser());
        userSearchBar.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
            focusChangedClearOrFill(loseFocus, gainFocus, userSearchBar, "Search user here..."));
        userView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> injectUserLabel());
        userView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userInviteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> sendFriendRequest());

        // Mid section event handlers
        friendSearchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> searchFriend());
        friendSearchBar.focusedProperty().addListener((obsValue, loseFocus, gainFocus) ->
            focusChangedClearOrFill(loseFocus, gainFocus, friendSearchBar,"Search friend here..."));
        friendView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> injectFriendLabel());
        friendView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        friendDeleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> deleteFriend());

        // Populating elements in UI
        setUserTableElements(userHandler.getUsers());
        setFriendTableElements(UserRequestsHandler.getInstance().getMe().getFriends());
        setDynamicElements();

    }

    // Left side methods and event handlers.

    /**
     * Set table elements from a specified list.
     * First use with general user list: friends and requests removed automatically.
     * @param list List of users to be set in the table
     */
    private void setUserTableElements(ArrayList<User> list) {
        Me currentUser = UserRequestsHandler.getInstance().getMe();
        list.removeAll(currentUser.getFriendsAsUsers());
        list.remove(new User(currentUser.getUsername()));

        userUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userView.getItems().setAll(list);
    }

    /**
     * Retrieve text from the TextField and search inside the list.
     * Then update the table with the search results.
     */
    private void searchUser() {
        String defaultString = "Search user here...";
        String name = userSearchBar.getCharacters().toString();
        name = name.equals(defaultString) ? "" : name;
        ArrayList<User> searchUser = userHandler.getUsers();
        ArrayList<User> foundUser = new ArrayList<>();
        for (User user : searchUser) {
            if (user.getUsername().contains(name)) {
                foundUser.add(user);
            }
        }
        setUserTableElements(foundUser);
    }

    /**
     * Helper method that fills a label with the table selected value.
     */
    private void injectUserLabel() {
        if (!userView.getSelectionModel().isEmpty()) {
            User user = userView.getSelectionModel().getSelectedItem();
            userLabel.setText("Invite " + user.getUsername() + "?");
            userInviteButton.setVisible(true);
        }
    }

    /**
     * Event listener for invite button.
     * When pressed, retrieve User object from the table and send invite.
     */
    private void sendFriendRequest() {
        User user = userView.getSelectionModel().getSelectedItem();
        userHandler.sendRequest(user.getUsername());
    }


    // Mid methods and event handlers.
    /**
     * Set table elements from a specified list.
     * Fills the table with the current user friends
     * @param list List of friends to be set in the table
     */
    private void setFriendTableElements(ArrayList<Friend> list) {
        friendUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        friendView.getItems().setAll(list);
    }

    /**
     * Retrieve text from the TextField and search inside the list.
     * Then update the table with the search results.
     */
    private void searchFriend() {
        String defaultString = "Search friend here...";
        String name = friendSearchBar.getCharacters().toString();
        name = name.equals(defaultString) ? "" : name;
        ArrayList<Friend> searchFriend = UserRequestsHandler.getInstance().getMe().getFriends();
        ArrayList<Friend> foundFriend = new ArrayList<>();
        for (Friend friend : searchFriend) {
            if (friend.getUsername().contains(name)) {
                foundFriend.add(friend);
            }
        }
        setFriendTableElements(foundFriend);
    }

    /**
     * Helper method that fills a label with the table selected value.
     */
    private void injectFriendLabel() {
        if (!friendView.getSelectionModel().isEmpty()) {
            Friend friend = friendView.getSelectionModel().getSelectedItem();
            friendLabel.setText("Delete " + friend.getUsername() + "?");
            friendDeleteButton.setVisible(true);
        }
    }

    /**
     * Event listener for delete button.
     * When pressed, retrieve Friend object from the table and send delete request.
     */
    private void deleteFriend() {
        Friend friend = friendView.getSelectionModel().getSelectedItem();
        userHandler.deleteFriend(friend);
    }

    // Right side methods and event handlers.
    /**
     * Method to fill the right section of the UI.
     * Dynamically set friend request panes.
     */
    private void setDynamicElements() {
        final double paneHeight = 50.0;
        final double yOffset = 10.0;

        Me currentUser = UserRequestsHandler.getInstance().getMe();
        for (int i = 0; i < currentUser.getInvites().size(); i++) {
            Pane pane = new Pane();
            pane.setId("two-search-right-pane");

            Friend friend = currentUser.getInvites().get(i);
            Label name = new Label(friend.getUsername() + " request");
            name.setId("two-search-right-label");
            pane.getChildren().add(name);

            Button accept = new Button("Accept");
            accept.setId("two-search-right-accept");
            accept.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                    acceptRequestBundle(friend));
            pane.getChildren().add(accept);

            Button decline = new Button("Decline");
            decline.setId("two-search-right-decline");
            decline.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                    declineRequestBundle(friend));
            pane.getChildren().add(decline);

            pane.setLayoutY(paneHeight * i + yOffset * i + yOffset);

            rightSide.getChildren().add(pane);
        }
    }

    /**
     * Helper method to simplify lambda expression in event handler.
     * Calls the service method to accept a request
     * and redraw the dynamic elements in the GUI.
     * @param friend The friend to be managed.
     */
    private void acceptRequestBundle(Friend friend) {
        userHandler.acceptRequest(friend);
        clearDynamicElements();
        setDynamicElements();
    }

    /**
     * Helper method to simplify lambda expression in event handler.
     * Calls the service method to decline a request
     * and redraw the dynamic elements in the GUI.
     * @param friend The friend to be managed.
     */
    private void declineRequestBundle(Friend friend) {
        userHandler.declineRequest(friend);
        clearDynamicElements();
        setDynamicElements();
    }

    private void clearDynamicElements() {
        rightSide.getChildren().clear();
    }
}
