package org.example.rmsproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegistrationController {

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
    private CheckBox  Chekin;

    @FXML
    private Label haveAccount;

    @FXML
    private Label roleLabel;

    @FXML
    public void initialize() {
        // إضافة العناصر إلى ComboBox
        ObservableList<String> list = FXCollections.observableArrayList("admin", "staff");
        ComboList.setItems(list);
    }

    // فنكشن لقراءة الخيار المحدد وبطبع
    @FXML
    public void handleSignUp(ActionEvent event) {
        String selectedRole = ComboList.getValue();

        System.out.println("Selected Role: " + selectedRole);

        roleLabel.setText("Selected Role: " + selectedRole);

    }

    // فنكشن للانتقال إلى صفحة تسجيل الدخول
    @FXML
    public void goToLogin(MouseEvent event) {
        try {
            // تحميل ملف FXML لصفحة الـ Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) haveAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSignUpButtonClick(ActionEvent event) {
        handleSignUp(event);
    }
}
