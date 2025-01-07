package org.example.rmsproject.Controllers.UserManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.rmsproject.Controllers.AbsController;
import org.example.rmsproject.models.entity.Users;
import org.example.rmsproject.models.interfaces.Users.UserDAO;
import org.example.rmsproject.models.services.User.userDAOImp;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserController extends AbsController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    private UserDAO userDAO;
    private Users user;

    public void setUser(Users user) {
        this.user = user;
        populateFields();
    }
    private void populateFields() {
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());
            ratingField.setText(user.getRate());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userDAO = new userDAOImp();

    }
    @FXML
    private void handleSaveUserButton(ActionEvent actionEvent) {
        if (!validateInput()) {
            return;
        }
        try {
            user.setName(nameField.getText().trim());
            user.setRate(ratingField.getText().trim());
            user.setPhone(phoneField.getText().trim());
            user.setEmail(emailField.getText().trim());

            userDAO.update(user);
            System.out.println("User info updated successfully!");
            loadScene(actionEvent, "/org/example/rmsproject/UserManagement/UserManagement.fxml", null);
        } catch (Exception e) {
            System.out.println("Error updating user info!");
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
    private boolean validateInput() {
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User name is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (nameField.getText().trim().length() < 3) {
            showAlert("Validation Error", "User name must be at least 3 characters long!", Alert.AlertType.ERROR);
            return false;
        }

        if (emailField.getText() == null || emailField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Email is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            showAlert("Validation Error", "A valid email is required!", Alert.AlertType.ERROR);
            return false;
        }

        if (phoneField.getText() == null || phoneField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User phone number is required!", Alert.AlertType.ERROR);
            return false;
        }
        if (!phoneField.getText().matches("\\d+")) {
            showAlert("Validation Error", "Phone number must contain only digits!", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneField.getText().trim().length() < 10 || phoneField.getText().trim().length() > 15) {
            showAlert("Validation Error", "Phone number must be between 10 and 15 digits!", Alert.AlertType.ERROR);
            return false;
        }

        if (ratingField.getText() == null || ratingField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "User rating is required!", Alert.AlertType.ERROR);
            return false;
        }
        try {
            int rating = Integer.parseInt(ratingField.getText().trim());
            if (rating < 1 || rating > 5) {
                showAlert("Validation Error", "Rating must be between 1 and 5!", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Rating must be a number!", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void showAlert(String title,String content,Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
