package org.example.rmsproject.Controllers.MenuControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.entity.MenuItem;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.example.rmsproject.models.services.Menu.categoryDAOImp;

import java.util.List;
import java.util.Optional;

public class AddItemController {


    @FXML
    public TextField itemNameField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField priceField;
    @FXML
    public CheckBox availabilityToggle;
    @FXML
    public ComboBox<String> categoryComboBox;

    private MenuManagementController menuManagementController;
    private categoryDAOImp categoryDOAImp = new categoryDAOImp();
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @FXML
    public void initialize() {
        if (session == null) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        loadCategorieData();
    }

    public void loadCategorieData() {
        List<Category> categories = categoryDOAImp.getAllCategories();
        categoryComboBox.getItems().clear();
        for (Category category : categories) {
            categoryComboBox.getItems().add(category.getName());
        }
    }

    private void navigateToMenuManagement(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/MenuMangment.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleSaveButton(ActionEvent event) throws Exception {
        if (session == null) {
            showAlert("Error", "Session is not initialized!");
            return;
        }

        String itemName = itemNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        String selectedCategoryName = categoryComboBox.getSelectionModel().getSelectedItem();

        if (itemName.isEmpty() || description.isEmpty() || priceText.isEmpty() || selectedCategoryName == null) {
            showAlert("Missing Information", "Please fill in all fields before saving.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for the price.");
            return;
        }

        boolean isAvailable = availabilityToggle.isSelected();

        Query<Category> query = session.createQuery("FROM Category c WHERE c.name = :name", Category.class);
        query.setParameter("name", selectedCategoryName);
        Category selectedCategory;
        try {
            selectedCategory = query.getSingleResult();
        } catch (Exception e) {
            showAlert("Error", "Selected category not found in the database.");
            return;
        }

        if (price <= 0) {
            showAlert("Invalid Price", "Price must be greater than 0.");
            return;
        }

        MenuItem menuItem = new MenuItem(itemName, description, price, isAvailable, selectedCategory);

        Transaction transaction = session.beginTransaction();
        session.save(menuItem);
        transaction.commit();

        showAlert("Success", "Item saved successfully!");

        if (menuManagementController != null) {
            menuManagementController.loadMenuItemData();
        }

        navigateToMenuManagement(event);
    }

    @FXML
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

    public void setMenuManagementController(MenuManagementController controller) {
        this.menuManagementController = controller;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setCategories(List<String> categories) {
        categoryComboBox.getItems().setAll(categories);
    }
}
