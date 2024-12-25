package org.example.rmsproject.UserManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class AddUserController {

    @FXML
    private AnchorPane addUserPane;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ratingField;

    @FXML
    private Label imageLabel; // Label to display the selected image name

    @FXML
    private Button selectImageButton;

    @FXML
    private Button saveUserButton;

    @FXML
    private Button cancelButton;

    private File selectedImage; // Store the selected image file

    public void initialize() {
        // Initialization logic if needed
    }

    @FXML
    public void handleSelectImageButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            selectedImage = file;
            imageLabel.setText("Selected: " + file.getName());
        } else {
            imageLabel.setText("No file selected");
        }
    }

    @FXML
    public void handleSaveUserButton(ActionEvent actionEvent) {
        try {
            // Loading the FXML file for the HomePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/UserManagement/UserManagement.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String name = nameField.getText();
//        String rating = ratingField.getText();
//
//        if (name.isEmpty() || rating.isEmpty() || selectedImage == null) {
//            System.out.println("All fields must be filled out, and an image must be selected.");
//            return;
//        }
//
//        try {
//            // Verify if the resource can be loaded
//            URL fxmlUrl = getClass().getResource("/org/example/rmsproject/UserManagement/UserManagement.fxml");
//            if (fxmlUrl == null) {
//                throw new RuntimeException("UserManagement.fxml resource not found!");
//            }
//
//            FXMLLoader loader = new FXMLLoader(fxmlUrl);
//            Parent root = loader.load();
//
//            // Add the user to the list
//            UserManagementController userManagementController = loader.getController();
//            userManagementController.addUserToList(name, rating, selectedImage.getAbsolutePath());
//
//            // Switch to UserManagement scene
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//        } catch (Exception e) {
//            System.out.println("Error loading UserManagement.fxml: " + e.getMessage());
//            e.printStackTrace();
//        }
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        try {
            // Verify if the resource can be loaded
            URL fxmlUrl = getClass().getResource("/org/example/rmsproject/UserManagement/UserManagement.fxml");
            if (fxmlUrl == null) {
                throw new RuntimeException("UserManagement.fxml resource not found!");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            // Switch to UserManagement scene
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Error loading UserManagement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
