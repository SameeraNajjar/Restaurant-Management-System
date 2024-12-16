package org.example.rmsproject.RegisterationControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class LoginController {
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

    public void handleHomeButton(ActionEvent actionEvent) {
        try {
            // Loading the FXML file for the HomePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/HomePage.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
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
    }

