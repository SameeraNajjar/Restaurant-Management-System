package org.example.rmsproject.Controllers.UserManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.rmsproject.Controllers.AbsController;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.services.User.userDOAImp;
import org.example.rmsproject.models.interfaces.Users.UserDOA;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class UserManagementController extends AbsController implements Initializable {


    @FXML
    private TextField searchField;

    @FXML
    private VBox userListContainer;


    @FXML
    private TableView<Users> usersTable;
    @FXML
    private TableColumn<Users, Integer> userIdColumn;
    @FXML
    private TableColumn<Users, String> usernameColumn;
    @FXML
    private TableColumn<Users, String> emailColumn;
    @FXML
    private TableColumn<Users, String> rateColumn;
    @FXML
    private TableColumn<Users, String> phoneColumn;

    private UserDOA userDOA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDOA = new userDOAImp();

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadUsers();
    }
    private void loadUsers() {
            List<Users> usersList = userDOA.getAllUsers();
            ObservableList<Users> usersObservableList = FXCollections.observableArrayList(usersList);
            usersTable.setItems(usersObservableList);
    }


    public void handleHomeButton(ActionEvent actionEvent) {

        loadScene(actionEvent, "/org/example/rmsproject/HomePage.fxml",loader -> {
            System.out.println("heloo");
       });
    }

    public void handleOrdersButton(ActionEvent actionEvent) {
        System.out.println("Orders button clicked");
   }

   public void handleMenuButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/MenuView/MenuMangment.fxml",null);
    }

    public void handleUsersButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/UserManagement/UserManagement.fxml",null);
    }

    public void handleTableButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/Table/TableManagementDashboard.fxml",null);
    }

    public void handleAddUserButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/UserManagement/AddUser.fxml",null);

    }


    public void handleUpdateUserInfoButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/UserManagement/AddUser.fxml",null);
    }

    public void handleDeleteUserButton(ActionEvent actionEvent) {
        // Display the confirmation popup
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/UserManagement/UserDeletedPopup.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Remove the user from the list after showing the alert
            Node sourceButton = (Node) actionEvent.getSource();
            HBox userCard = (HBox) sourceButton.getParent(); // Get the user card that contains the button
            userListContainer.getChildren().remove(userCard);

        } catch (Exception e) {
            System.out.println("Error loading UserDeletedPopup.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleCloseAlert(ActionEvent actionEvent) {
       // Close the popup alert
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleBackButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/HomePage.fxml",null);
    }




}
