package org.example.rmsproject.Controllers.RegisterationControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.services.User.userDAOImp;
import org.example.rmsproject.util.SessionManager;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class LoginController {
    // userDOAImp userDOAImp0=new userDOAImp();
    @FXML
    private Label welcomeText;
    @FXML
    private TextField email ;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button forgotYourPassword;

    @FXML
    private CheckBox Check;

    @FXML
    private Label haveA;

    @FXML
    private Label goRegistration;

    @FXML
    private ImageView image;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    userDAOImp userDOAImp0=new userDAOImp();

    @FXML
    public void handleHomeButton(ActionEvent actionEvent) {

        String emailInput = email.getText();
        String passwordInput = password.getText();

        if (isValidUser(emailInput, passwordInput)) {
            Users user = userDOAImp0.findByEmailAndPassword(emailInput, passwordInput);
            SessionManager.setLoggedInUser(user);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/HomePage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }


    } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }

    }




    @FXML
    private void goToRegistration(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Registration/Registration.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loDB(){

        Users users= new Users();
        users.setEmail("ameed@gmail.com");
        users.setPassword("123");
        userDOAImp0.save(users);
        System.out.println("DataBase is created successfully");

    }
    public boolean isValidUser(String email, String password) {
        Users user = userDOAImp0.findByEmailAndPassword(email, password);

        return user != null;
    }

}
