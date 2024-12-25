package org.example.rmsproject.ReservationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
public class ReservationsManagementController {
    @FXML

    public void openEditRes(ActionEvent btn_event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/EditRsePop_Up.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException Exception) {
            Exception.printStackTrace();
        }
    }
    public void openDeleteRes(ActionEvent btn_event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/DeleteRsePop_Up.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException Exception) {
            Exception.printStackTrace();
        }
    }

    public void openEditCust(ActionEvent btn_event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/EditCustPop_Up.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException Exception) {
            Exception.printStackTrace();
        }
    }
    public void openDeleteCust(ActionEvent btn_event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/deleteCustPop_Up.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException Exception) {
            Exception.printStackTrace();
        }
    }
    public void closePopUp(ActionEvent btn_event) {

        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
        stage.close();
    }



}
