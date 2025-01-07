package org.example.rmsproject.models.interfaces.Table;

import org.example.rmsproject.models.entity.Customer;
import org.example.rmsproject.models.entity.Reservation;
import org.example.rmsproject.models.entity.ResturantTable;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface reservationDAO {
    public void save(Reservation reservation);
    public List<Reservation> getAll();
    public void delete(Reservation reservation);
    public void edit(Reservation reservation);
    List<ResturantTable> getAvailableTables(java.sql.Date date, java.sql.Time time);
    void addReservation(Reservation reservation);
    List<Integer> findAvailableTableByDataAndTime(Date date, Time time);
    ResturantTable getTableById(int tableId);
    void addCustomer(String name, String phone);
    Customer getCustomerByPhone(String phone);
    List<Customer> getAllCustomers();
}
