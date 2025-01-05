package org.example.rmsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.util.SessionManager;

import java.util.Objects;

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
        String imgPath = getClass().getResource("/images/logo.png").toExternalForm();
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
    public void handleMenuButton(ActionEvent actionEvent) {
        Users user= SessionManager.getLoggedInUser();

        try {
            if(Objects.equals(user.getRole(), "Admin")){
                // Loading the FXML file for the HomePage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/MenuMangment.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            }
            else {
                System.out.println("no permission");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOrdersButton(ActionEvent actionEvent) {
        try {
            // Correct path to the FXML file for orders
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/OrderView/AddOrder.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUsersButton(ActionEvent actionEvent) {
        Users user= SessionManager.getLoggedInUser();

            try {
                if (Objects.equals(user.getRole(), "Admin")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/UserManagement/UserManagement.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                }
                else {
                    System.out.println("no permission");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
        }


    }

    @FXML
    public void handleTableButton(ActionEvent actionEvent) {
            try {
                // Loading the FXML file for the HomePage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/TableManagementDashboard.fxml"));
                Parent root = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
