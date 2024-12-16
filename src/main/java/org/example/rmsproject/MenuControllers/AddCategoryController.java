package org.example.rmsproject.MenuControllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddCategoryController {


    public void handleSaveButton(ActionEvent event) throws Exception {

        navigateToMenuManagement(event);
    }


    public void handleCancelButton(ActionEvent event) throws Exception {

        navigateToMenuManagement(event);
    }


    private void navigateToMenuManagement(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/MenuMangment.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        stage.setScene(new Scene(root));
        stage.show();
    }
}
