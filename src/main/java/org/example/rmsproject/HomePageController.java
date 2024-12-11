package org.example.rmsproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class HomePageController {

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
        String imgPath = getClass().getResource("/data/images/logo.png").toExternalForm();
        mainRegion.setStyle(
                "-fx-background-image: url('" + imgPath + "'); " +
                        "-fx-background-size: 150% 80%;" +
                        "-fx-background-position:  center 60% ; " +
                        "-fx-background-repeat: no-repeat;"
        );
    }

    @FXML
    public void handleHomeButton() {
        System.out.println("Home button clicked");
        // Add logic to switch to the Home page
    }

    @FXML
    public void handleMenuButton() {
        System.out.println("Menu button clicked");
        // Add logic to switch to the Menu page
    }

    @FXML
    public void handleOrdersButton() {
        System.out.println("Orders button clicked");
        // Add logic to switch to the Orders page
    }

    @FXML
    public void handleUsersButton() {
        System.out.println("Users button clicked");
        // Add logic to switch to the Users page
    }

    @FXML
    public void handleTableButton() {
        System.out.println("Table button clicked");
        // Add logic to switch to the Table page
    }

    @FXML
    public void handleSettingsButton() {
        System.out.println("Settings button clicked");
        // Add logic for settings functionality
    }

    @FXML
    public void handleProfileButton() {
        System.out.println("Profile button clicked");
        // Add logic for profile functionality
    }

    @FXML
    public void handleNotificationsButton() {
        System.out.println("Notifications button clicked");
        // Add logic for notifications functionality
    }
}
