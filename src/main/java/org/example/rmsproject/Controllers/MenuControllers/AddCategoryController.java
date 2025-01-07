package org.example.rmsproject.Controllers.MenuControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.services.Menu.categoryDAOImp;
import java.util.Optional;

public class AddCategoryController {


    public TextField categoryNameField;
    private MenuManagementController menuManagementController;


    public void handleSaveButton(ActionEvent event) throws Exception {
        String categoryName = categoryNameField.getText().trim();

        if (!categoryName.isEmpty()) {
            Category category = new Category();
            category.setName(categoryName);

            categoryDAOImp categoryDAO = new categoryDAOImp();
            categoryDAO.save(category);

            showAlert("Success", "Category added successfully!", Alert.AlertType.INFORMATION);
            navigateToMenuManagement(event);
        } else {
            showAlert("Invalid Input", "Please enter a valid category name.", Alert.AlertType.WARNING);
        }
    }

    public void handleCancelButton(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel? Any unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            navigateToMenuManagement(event);
        }
    }

    private void navigateToMenuManagement(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/MenuMangment.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setMenuManagementController(MenuManagementController controller) {
        this.menuManagementController = controller;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
