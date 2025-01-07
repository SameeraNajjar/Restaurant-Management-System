package org.example.rmsproject.models.entity;

import javax.persistence.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservation_id;

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus = "PENDING";

    @Column(name = "date_reservation", nullable = false)
    private Date dateReservation;

    @Column(name = "time_reservation", nullable = false)
    private Time timeReservation;

    @Column(name = "number_of_people", nullable = false)
    private int numberOfPeople;

    @Column(name = "table_preference", length = 400)
    private String tablePreference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", nullable = false, referencedColumnName = "table_id")
    private ResturantTable restaurantTable;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "customer_id")
    private Customer customer;


    public Reservation() {}

    public Reservation(int reservationId, Date dateReservation, Time timeReservation, int numberOfPeople,
                       String tablePreference, String reservationStatus, int tableId, int customerId) {
        System.out.println(reservationId);
        this.reservation_id = reservationId;
        this.dateReservation = dateReservation;
        this.timeReservation = timeReservation;
        this.numberOfPeople = numberOfPeople;
        this.tablePreference = tablePreference;
        this.reservationStatus = reservationStatus;

        this.restaurantTable = new ResturantTable();
        this.restaurantTable.setTableId(tableId);

        this.customer = new Customer();
        this.customer.setCustomerId(customerId);
    }

    public Reservation(int reservationId, Date dateReservation, Time timeReservation, int numberOfPeople,
                       String tablePreference, String reservationStatus) {
        this.reservation_id = reservationId;
        this.dateReservation = dateReservation;
        this.timeReservation = timeReservation;
        this.numberOfPeople = numberOfPeople;
        this.tablePreference = tablePreference;
        this.reservationStatus = reservationStatus;
    }

    // Getters and Setters

    public int getReservationId() {
        return reservation_id;
    }

    public void setReservationId(int reservationId) {
        this.reservation_id = reservationId;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Time getTimeReservation() {
        return timeReservation;
    }

    public void setTimeReservation(Time timeReservation) {
        this.timeReservation = timeReservation;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getTablePreference() {
        return tablePreference;
    }

    public void setTablePreference(String tablePreference) {
        this.tablePreference = tablePreference;
    }

    public ResturantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(ResturantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Optional: Add a toString method for debugging

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservation_id +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", dateReservation=" + dateReservation +
                ", timeReservation=" + timeReservation +
                ", numberOfPeople=" + numberOfPeople +
                ", tablePreference='" + tablePreference + '\'' +
                ", restaurantTable=" + (restaurantTable != null ? restaurantTable.getTableId() : "null") +
                ", customer=" + (customer != null ? customer.getCustomerName() : "null") +
                '}';
    }
    public String getFormattedDate() {
        if (dateReservation == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateReservation);
    }
    public boolean isDone() {
        return "DONE".equalsIgnoreCase(reservationStatus);
    }

    public void setDone(boolean done) {
        this.reservationStatus = done ? "DONE" : "PENDING";
    }
}