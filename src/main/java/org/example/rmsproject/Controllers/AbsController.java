package org.example.rmsproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.function.Consumer;

public abstract class AbsController {
    public void loadScene(ActionEvent actionEvent, String fxmlPath, Consumer<FXMLLoader> additionalFunctionality) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Apply additional functionality (if provided)
            if (additionalFunctionality != null) {
                additionalFunctionality.accept(loader);
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
