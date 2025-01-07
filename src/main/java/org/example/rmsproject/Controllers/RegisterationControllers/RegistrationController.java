package org.example.rmsproject.Controllers.RegisterationControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.rmsproject.models.entity.Role;
import org.example.rmsproject.models.entity.Users;
import org.example.rmsproject.models.interfaces.Users.UserDAO;
import org.example.rmsproject.models.services.User.userDAOImp;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField fullName;

    @FXML
    private TextField Email;

    @FXML
    private PasswordField Password;

    @FXML
    private PasswordField ConfirmPassword;

    @FXML
    private ComboBox<String> ComboList;

    @FXML
    private Button SignUp;

    @FXML
    private CheckBox Chekin;

    @FXML
    private Label haveAccount;

    @FXML
    private Label roleLabel;

    @FXML
    private Label lblpassword1;

    @FXML
    private Label lblpassword2;

    private final UserDAO userDOA = new userDAOImp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate role combo box
        ObservableList<String> list = FXCollections.observableArrayList("Admin", "Staff");
        ComboList.setItems(list);

        // Check if labels are properly linked
        if (lblpassword1 != null) lblpassword1.setVisible(false);
        if (lblpassword2 != null) lblpassword2.setVisible(false);
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        String selectedRole = ComboList.getValue();
        roleLabel.setText("Selected Role: " + selectedRole);
    }

    @FXML
    public void goToLogin(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Registration/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) haveAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSignUpButtonClick(ActionEvent event) {
        if (!isFieldsValid() || !registerConfirmPassword()) {
            return;
        }

        Save_registration_information_database();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Registration/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerConfirmPassword() {
        String newPassword = Password.getText();
        String confirmPassword = ConfirmPassword.getText();

        if (newPassword.equals(confirmPassword)) {
            setBorderColor("transparent");
            if (lblpassword1 != null) lblpassword1.setVisible(false);
            if (lblpassword2 != null) lblpassword2.setVisible(false);
            return true;
        } else {
            setBorderColor("red");
            if (lblpassword1 != null) lblpassword1.setVisible(true);
            if (lblpassword2 != null) lblpassword2.setVisible(true);
            return false;
        }
    }

    public void setBorderColor(String color) {
        Password.setStyle("-fx-border-color: " + color);
        ConfirmPassword.setStyle("-fx-border-color: " + color);
    }

    public void Save_registration_information_database() {
        String name = fullName.getText();
        String email = Email.getText();
        String password = Password.getText();
        String roleName = ComboList.getValue();

        if (roleName == null) {
            showAlert(Alert.AlertType.ERROR, "Please select a role.");
            return;
        }

        int roleId = roleName.equals("Admin") ? 1 : 2;

        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        Role role = new Role();
        role.setId(roleId);
        newUser.setRole(roleName);

        userDOA.save(newUser);
    }

    public boolean isFieldsValid() {
        String name = fullName.getText();
        String email = Email.getText();
        String password = Password.getText();
        String roleName = ComboList.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || roleName == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return false;
        }

        if (!Chekin.isSelected()) {
            showAlert(Alert.AlertType.ERROR, "You must agree to the terms.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
}
