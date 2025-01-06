package org.example.rmsproject.Controllers.RegisterationControllers;

import javafx.fxml.FXML;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javafx.scene.control.PasswordField;
import org.example.rmsproject.util.HibernateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class resetPassword {

    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    private  String userEmail;

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @FXML
    void changePassword() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }


        if (!newPassword.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Error", "Passwords do not match!");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("update Users set password = :password  WHERE email = :email");
            query.setParameter("password",newPassword);
            query.setParameter("email",userEmail);
            int result = query.executeUpdate();
            session.getTransaction().commit();

            newPasswordField.clear();
            confirmPasswordField.clear();
            if (result > 0) {
                showAlert(AlertType.INFORMATION, "Success", "Password updated successfully!");
            } else {
                showAlert(AlertType.WARNING, "Warning", "Failed to update password. User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
