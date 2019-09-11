package backend.controller.tab;

import backend.controller.HomeController;
import backend.entity.habit.structure.OverviewElement;
import backend.entity.user.Friend;
import backend.entity.user.Me;
import backend.service.UserRequestsHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendOverviewController extends HomeController implements Initializable {

    @FXML
    private ComboBox friendSearchCBox;
    @FXML
    private TableView<OverviewElement> view;
    @FXML
    private TableColumn<OverviewElement, String> col1;
    @FXML
    private TableColumn<OverviewElement, Double> col2;

    private Me currentUser = UserRequestsHandler.getInstance().getMe();

    /**
     * Sets elements in the table as the habits of the selected friend.
     * Same method implemented as in Overview.fxml
     */
    public void setTableElements() {


        if (friendSearchCBox.getValue() == null || friendSearchCBox.getValue().equals("Overview")) {
            ArrayList<OverviewElement> leaderBoardList = new ArrayList<>();
            for (Friend friend : currentUser.getFriends()) {
                OverviewElement element =
                        new OverviewElement(friend.getUsername(), friend.getTotal());
                leaderBoardList.add(element);
            }
            OverviewElement element =
                    new OverviewElement(currentUser.getUsername(), currentUser.getTotal());
            leaderBoardList.add(element);
            leaderBoardList.sort((userA,userB) -> userA.getTotal() < userB.getTotal()
                    ? 1 : userA.getTotal().equals(userB.getTotal())
                    ? 0 : -1);
            col1.setCellValueFactory(new PropertyValueFactory<>("description"));
            col2.setCellValueFactory(new PropertyValueFactory<>("total"));
            view.getItems().setAll(leaderBoardList);
        } else {
            String name = friendSearchCBox.getValue().toString();

            Friend found = new Friend();
            for (Friend friend : currentUser.getFriends()) {
                if (friend.getUsername().equals(name)) {
                    found = friend;
                }
            }
            col1.setCellValueFactory(new PropertyValueFactory<>("description"));
            col2.setCellValueFactory(new PropertyValueFactory<>("total"));
            view.getItems().setAll(found.getOverview());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        friendSearchCBox.getItems().add("Overview");
        for (Friend friend : currentUser.getFriends()) {
            friendSearchCBox.getItems().add(friend.getUsername());
        }
        setTableElements();
    }
}