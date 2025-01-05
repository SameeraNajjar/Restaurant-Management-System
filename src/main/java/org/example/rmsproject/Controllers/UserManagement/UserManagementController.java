package org.example.rmsproject.Controllers.UserManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.rmsproject.Controllers.AbsController;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.services.User.userDAOImp;
import org.example.rmsproject.models.interfaces.Users.UserDAO;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class UserManagementController extends AbsController implements Initializable {
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

    private UserDAO userDAO;
    @FXML
    private TableColumn<Users, Void> updateColumn;

    @FXML
    private TableColumn<Users, Void> deleteColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = new userDAOImp();

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        addButtonToUpdateColumn();
        addButtonToDeleteColumn();
        loadUsers();
    }

    private void loadUsers() {
        List<Users> usersList = userDAO.getAllUsers();
        ObservableList<Users> usersObservableList = FXCollections.observableArrayList(usersList);
        usersTable.setItems(usersObservableList);
    }


    public void handleHomeButton(ActionEvent actionEvent) {

        loadScene(actionEvent, "/org/example/rmsproject/HomePage.fxml", loader -> {
            System.out.println("heloo");
        });
    }

    public void handleOrdersButton(ActionEvent actionEvent) {
        System.out.println("Orders button clicked");
    }

    public void handleMenuButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/MenuView/MenuMangment.fxml", null);
    }

    public void handleUsersButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/UserManagement/UserManagement.fxml", null);
    }

    public void handleTableButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/Table/TableManagementDashboard.fxml", null);
    }

    public void handleAddUserButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/UserManagement/AddUser.fxml", null);

    }
    public void handleBackButton(ActionEvent actionEvent) {
        loadScene(actionEvent, "/org/example/rmsproject/HomePage.fxml", null);
    }

    private void addButtonToUpdateColumn() {
        updateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Users user = getTableView().getItems().get(getIndex());
                    openUpdateUserPage(user);
                });
                updateButton.setStyle("-fx-background-color: #630C2F; -fx-text-fill: #B89555;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });
    }

    private void addButtonToDeleteColumn() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Users user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
                deleteButton.setStyle("-fx-background-color: #630C2F; -fx-text-fill: #B89555;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void openUpdateUserPage(Users user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/UserManagement/UpdateUserInfo.fxml"));
            Parent root = loader.load();

            // Pass the user object to the UpdateUserController
            UpdateUserController controller = loader.getController();
            controller.setUser(user);

            // Get the current stage and set the new scene
            Stage stage = (Stage) usersTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error loading UpdateUserInfo.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void deleteUser(Users user) {
        try {
            userDAO.delete(user.getId());
            usersTable.getItems().remove(user);

            showUserDeletedPopup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUserDeletedPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/UserManagement/UserDeletedPopup.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.setScene(new Scene(root));
            popupStage.setTitle("User Deleted");

            popupStage.show();
        } catch (Exception e) {
            System.out.println("Error loading UserDeletedPopup.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}