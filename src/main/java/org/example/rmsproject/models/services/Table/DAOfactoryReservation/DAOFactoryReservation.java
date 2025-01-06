package org.example.rmsproject.models.services.Table.DAOfactoryReservation;
import org.example.rmsproject.models.interfaces.Table.reservationDOA;
import org.example.rmsproject.models.services.Table.DAOReservation.reservationDOAmp;

public class DAOFactoryReservation {
    public static reservationDOA getCustomerDAO() {
        return new reservationDOAmp();
    }
}
