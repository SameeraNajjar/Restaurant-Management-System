package org.example.rmsproject.Controllers.ReservationController;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddReservationController {

    @FXML
    private Text noTablesAvailable;


    @FXML
    public void Find_Table() throws IOException {
        noTablesAvailable.setText("No tables are available yet.");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/Reservation_Failed_message.fxml"));
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

    @FXML
    private AnchorPane IdPane;
    private static final Logger LOGGER = Logger.getLogger(AddReservationController.class.getName());

    @FXML
    public void goToCustomerInfo() {
        loadFXMLToContentPane("/org/example/rmsproject/Table/CustomerInfo.fxml");
    }

    private void loadFXMLToContentPane(String NameOfFxmlFile) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(NameOfFxmlFile));
            if (IdPane != null) {
                IdPane.getChildren().clear();
                IdPane.getChildren().add(fxml);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML file :(" + NameOfFxmlFile+ ")", ex);
        }
    }

}

