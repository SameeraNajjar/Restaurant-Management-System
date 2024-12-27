package org.example.rmsproject.RegisterationControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.rmsproject.models.Role;
import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.interfaces.Users.UserDOA;
import org.example.rmsproject.models.interfaces.Users.roleDOA;
import org.example.rmsproject.models.services.User.roleDOAImp;
import org.example.rmsproject.models.services.User.userDOAImp;

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
    private CheckBox Chekin;

    @FXML
    private Label haveAccount;

    @FXML
    private Label roleLabel;

    UserDOA userDOA = new userDOAImp();


    @FXML
    public void initialize() {
        ComboList.getItems().addAll("Admin", "Staff");

        // إضافة العناصر إلى ComboBox
        ObservableList<String> list = FXCollections.observableArrayList("admin", "staff");
        ComboList.setItems(list);
        lblpassword1.setVisible(false);
        lblpassword2.setVisible(false);

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
        // التحقق من الحقول الفارغة
        if (!isFieldsValid()) {
            return;
        }
        // غشان يتاكذ من تطابق كلمة السر
        if (!registerConfirmPassword()) {
            System.out.println("Passwords do not match.");
            return;
        }

        Save_registration_information_database();

        // الانتقال إلى الصفحة التالية
        try {
            // Load the FXML file for the HomePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/HomePage.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IllegalArgumentException ex) {
            // خطأ عشان البريد الإلكتروني موجود من قبل بالداتا بيس
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Label lblpassword1;
    @FXML
    private Label lblpassword2;

    public void ConfirmPassword(ActionEvent actionEvent) {
        registerConfirmPassword();
    }

    public boolean registerConfirmPassword() {
        String newPassword0 = Password.getText();
        String ConfirmPassword0 = ConfirmPassword.getText();


        if (newPassword0.equals(ConfirmPassword0)) {
            setBorderColor("transparent");
            lblpassword1.setVisible(false);
            lblpassword2.setVisible(false);
            return true;
        } else {
            setBorderColor("red");
            lblpassword1.setVisible(true);
            lblpassword2.setVisible(true);
            return false;
        }
    }

    public void setBorderColor(String color) {
        Password.setStyle("-fx-border-color: " + color);
        ConfirmPassword.setStyle("-fx-border-color: " + color);
    }

    public void Save_registration_information_database() {
        // قراءة البيانات من الحقول
        String name = fullName.getText();
        String email = Email.getText();
        String password = Password.getText();
        String roleName = ComboList.getValue();
        int roleId = roleName.equals("Admin") ? 1 : 2;
// التحقق إذا لم يتم تحديد دور
        if (ComboList == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a role.");
            alert.show();
            return;
        }
        Users newUser0 = new Users();
        newUser0.setName(name);
        newUser0.setEmail(email);
        newUser0.setPassword(password);

        Role role = new Role();
        role.setId(roleId);
        newUser0.setRole(role);

        userDOA.save(newUser0);



    }
    public boolean isFieldsValid() {
        String name = fullName.getText();
        String email = Email.getText();
        String password = Password.getText();
        String roleName = ComboList.getValue();
        // تحقق إذا كانت الحقول فارغة
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ComboList.getValue()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return false;
        }

        // التحقق من قبول الشروط
        if (!Chekin.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Terms must be agreed to.");
            alert.show();
            return false;
        }

        return true;
    }

}
