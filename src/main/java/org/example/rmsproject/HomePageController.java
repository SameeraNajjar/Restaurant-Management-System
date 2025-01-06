package org.example.rmsproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import org.example.rmsproject.Controllers.AbsController;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.util.SessionManager;

import java.util.Objects;
public class HomePageController extends AbsController {
    @FXML
    private Button homeButton;


    @FXML
    private Button menuButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button tableButton;

    @FXML
    private Region mainRegion;

    @FXML
    private Button settingsButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button notificationsButton;

    @FXML
    public void initialize() {
        String imgPath = getClass().getResource("/images/logo.png").toExternalForm();
        mainRegion.setStyle(
                "-fx-background-image: url('" + imgPath + "'); " +
                        "-fx-background-size: 150% 80%;" +
                        "-fx-background-position: center 60%;" +
                        "-fx-background-repeat: no-repeat;"
        );
    }

    @FXML
    public void handleHomeButton() {
        System.out.println("Home button clicked");
    }

    @FXML
    public void handleMenuButton(ActionEvent actionEvent) {
        Users user = SessionManager.getLoggedInUser();
        if (Objects.equals(user.getRole(), "Admin")) {
            loadScene(actionEvent, "/org/example/rmsproject/MenuView/MenuMangment.fxml", null);
        } else {
            showAlert("Permission Denied", "You do not have permission to access Menu Management.");
        }
    }

    @FXML
    public void handleOrdersButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/OrderView/AddOrder.fxml", null);
    }

    @FXML
    public void handleUsersButton(ActionEvent actionEvent) {
        Users user = SessionManager.getLoggedInUser();
        if (Objects.equals(user.getRole(), "Admin")) {
            loadScene(actionEvent, "/org/example/rmsproject/UserManagement/UserManagement.fxml", null);
        } else {
            showAlert("Permission Denied", "You do not have permission to access User Management.");
        }
    }

    @FXML
    public void handleTableButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/Table/TableManagementDashboard.fxml", null);
    }

    @FXML
    public void handleSettingsButton() {
        System.out.println("Settings button clicked");
    }

    @FXML
    public void handleProfileButton() {
        System.out.println("Profile button clicked");
    }

    @FXML
    public void handleNotificationsButton() {
        System.out.println("Notifications button clicked");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
