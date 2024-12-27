package org.example.rmsproject.Controllers.UserManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.interfaces.Users.UserDOA;
import org.example.rmsproject.models.services.User.userDOAImp;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveUserButton;

    @FXML
    private Button cancelButton;

    private UserDOA userDOA;

    private UserManagementController userManagementController;

    public void setUserManagementController(UserManagementController userManagementController) {
        this.userManagementController = userManagementController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDOA = new userDOAImp();
        saveUserButton.setOnAction(event -> handleSaveUserButton());
    }

    @FXML
    private void handleSaveUserButton() {
        if (!validateInput()) {
            return;
        }
        try {
            Users users = new Users();

            users.setName(nameField.getText().trim());
            users.setRate(ratingField.getText().trim());
            users.setPhone(phoneField.getText().trim());
            users.setEmail(emailField.getText().trim());

            userDOA.save(users);
            System.out.println("User added successfully!");

//            if (userManagementController != null) {
//                userManagementController.reloadUserData();
//            }

            clearFields();
        } catch (Exception e) {
            System.out.println("Error in adding user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        try {
            URL fxmlUrl = getClass().getResource("/org/example/rmsproject/UserManagement/UserManagement.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("UserManagement.fxml resource not found!");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error loading UserManagement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        ratingField.clear();
        phoneField.clear();
        emailField.clear();
    }

    private boolean validateInput() {
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User name is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (emailField.getText() == null || !emailField.getText().contains("@")) {
            showAlert("Validation Error", "A valid email is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneField.getText() == null || phoneField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User phone number is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (ratingField.getText() == null || ratingField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User rating is required!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
