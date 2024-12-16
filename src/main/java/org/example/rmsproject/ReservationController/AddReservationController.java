package org.example.rmsproject.ReservationController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.IOException;


public class AddReservationController {

    @FXML
        private Text noTablesAvailable;

    public void Find_Table(ActionEvent actionE) throws IOException {
        noTablesAvailable.setText("No tables are available yet.");
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Failed! Message");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public void closeMessageF(ActionEvent action) {
        Stage stage = (Stage) ((Button) action.getSource()).getScene().getWindow();
        stage.close();
    }
}

