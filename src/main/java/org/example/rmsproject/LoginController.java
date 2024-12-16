package org.example.rmsproject;

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


     public void print(){

        System.out.println(email.getText());
    }




        @FXML
        private void goToRegistration(MouseEvent event) {
            try {
                // تحميل ملف FXML لصفحة Registration
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Registration.fxml"));
                Parent root = loader.load();

                // الحصول على Stage الحالي
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

