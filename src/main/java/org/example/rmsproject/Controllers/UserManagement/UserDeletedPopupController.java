package org.example.rmsproject.Controllers.UserManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserDeletedPopupController {

    @FXML
    private Label messageLabel;

    @FXML
    public void handleCloseAlert(ActionEvent actionEvent) {
        // Close the popup alert
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
