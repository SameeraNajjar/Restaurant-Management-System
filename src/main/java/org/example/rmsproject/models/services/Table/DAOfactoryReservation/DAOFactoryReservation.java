package org.example.rmsproject.models.services.Table.DAOfactoryReservation;
import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.models.services.Table.DAOReservation.reservationDAOImp;

public class DAOFactoryReservation {
    public static reservationDAO getCustomerDAO() {
        return new reservationDAOImp();
    }
}
