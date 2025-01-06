package org.example.rmsproject.models.interfaces.Table;

import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.ResturantTable;

import java.util.List;

public interface reservationDAO {
    public void save(Reservation reservation);
    public List<Reservation> getAll();
    public void delete(Reservation reservation);
    public void edit(Reservation reservation);

    List<ResturantTable> getAvailableTables(java.sql.Date date, java.sql.Time time);
}
