package org.example.rmsproject.Controllers.ReservationController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class SuccessFailedMessageManagmantController {
    @FXML
    private Button closeButton;
    @FXML
    public void close_Form() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
