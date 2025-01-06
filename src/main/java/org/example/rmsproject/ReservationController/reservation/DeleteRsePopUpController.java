package org.example.rmsproject.ReservationController.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.services.Table.DAOReservation.reservationDAOImp;

import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.models.services.Table.DAOfactoryReservation.DAOFactoryReservation;

public class DeleteRsePopUpController {

    private Reservation reservation;
    private ReservationsManagementController parentController;


    public void initializeData(Reservation reservation, ReservationsManagementController parentController) {
        this.reservation = reservation;
        this.parentController = parentController;
    }

    @FXML
    private void confirmDelete(ActionEvent event) {

        reservationDAO reservationDao= DAOFactoryReservation.getCustomerDAO();

        reservationDao.delete(reservation);


        if (parentController != null) {
            parentController.removeReservationFromTable(reservation);
        }


        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void closePopUp(ActionEvent btn_event) {
        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
        stage.close();
    }
}
