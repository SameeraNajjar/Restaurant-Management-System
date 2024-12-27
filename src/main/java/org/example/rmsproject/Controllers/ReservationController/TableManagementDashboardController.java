package org.example.rmsproject.Controllers.ReservationController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableManagementDashboardController {
    @FXML
    private AnchorPane contentPane ;
    @FXML
    private Button  reservationsId, customerId, addId;
    private static final Logger LOGGER = Logger.getLogger(TableManagementDashboardController.class.getName());
    private Button activeButton = null;

//    @FXML
//    private void initialize() {
//        loadFXMLToContentPane("VeiwTableReservationInfo.fxml");
//        updateButtonStyle(viewId);
//    }
//
//    @FXML
//    public void loadView(ActionEvent actionEvent) {
//        loadFXMLToContentPane("VeiwTableReservationInfo.fxml");
//        updateButtonStyle(viewId);
//    }

    @FXML
    public void loadReservations(ActionEvent btn_event) {
        loadFXMLToContentPane("/org/example/rmsproject/Table/Reservations.fxml");
        updateButtonStyle(reservationsId);
    }

    @FXML
    public void loadHome(MouseEvent btn_event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/HomePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) btn_event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void loadCustomer(ActionEvent btn_event) {
        loadFXMLToContentPane("/org/example/rmsproject/Table/CustomerInfo.fxml");
        updateButtonStyle(customerId);
    }

    @FXML
    public void loadAdd(ActionEvent actionEvent) {
        loadFXMLToContentPane("/org/example/rmsproject/Table/Add_Reservation(Table_Check).fxml");
        updateButtonStyle(addId);
    }

    private void loadFXMLToContentPane(String fxmlFileName) {
        try {
            Parent fxml = FXMLLoader.load(TableManagementDashboardController.class.getResource(fxmlFileName));
            if (contentPane != null) {
                contentPane.getChildren().clear();
                contentPane.getChildren().add(fxml);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to load FXML file: " + fxmlFileName, ex);
        }
    }

    private void updateButtonStyle(Button clickedButton) {

        if (activeButton != null) {
            activeButton.setStyle("-fx-background-color: #F1F0ED; -fx-background-radius: 11; -fx-border-color: #B19251; -fx-border-radius: 10;");
        }

        clickedButton.setStyle("-fx-background-color: #630C2F; -fx-background-radius: 11; -fx-border-color: #B19251; -fx-border-radius: 10;");
        activeButton = clickedButton;
    }
}
