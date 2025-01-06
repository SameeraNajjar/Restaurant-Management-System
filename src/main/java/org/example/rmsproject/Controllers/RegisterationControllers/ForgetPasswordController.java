package org.example.rmsproject.Controllers.RegisterationControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.services.User.userDAOImp;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ForgetPasswordController {
    @FXML private TextField emailField;
    @FXML private TextField verificationCodeField;
    @FXML private Button verifyCodeButton;

    private String sentCode;
    private final userDAOImp userDAOimp;

    public ForgetPasswordController() {userDAOimp = new userDAOImp();}

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        String recipientEmail = emailField.getText();
        Users users = userDAOimp.findByEmail(recipientEmail);

        try {
            String randomCode =generateRandomCode();
            sentCode = randomCode;
            sendEmail(recipientEmail, randomCode);
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void verifyCode(ActionEvent event) {
        String enteredCode = verificationCodeField.getText();
        if(enteredCode.equals(sentCode)){
            try {
                Stage stage = (Stage) verifyCodeButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Registration/resetPassword.fxml"));
                Parent root = loader.load();
                resetPassword resetPassword = loader.getController();
                resetPassword.setUserEmail(emailField.getText());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Reset Password");
                stage.show();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEmail(String recipientEmail, String randomCode) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "sameera.najjar14@gmail.com";
        String password = "cmck phnd ymsf zquo ";

        Session emailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected  PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Your Verification Code");
        message.setText("Your verification code is: " + randomCode);

        Transport.send(message);
    }

    private String generateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }



}

