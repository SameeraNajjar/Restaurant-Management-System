package org.example.rmsproject.models;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="Reservation")

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reserve_Id;

    @Column(name="DateRresevation")
    private Date DateRresevation;
//     table_Id , customer_Id
    @Column(name="TimeReservation")
    private String TimeReservation;
    @Column(name="NumberOf_People")
    private int NumberOf_People;
    @Column(name="tablePreference")
    private String tablePreference;
    @Column(name="Reservation_Status")
    private String  Reservation_Status;

    public Date getDateRresevation() {
        return DateRresevation;
    }

    public void setDateRresevation(Date dateRresevation) {
        DateRresevation = dateRresevation;
    }

    public String getTimeReservation() {
        return TimeReservation;
    }

    public void setTimeReservation(String timeReservation) {
        TimeReservation = timeReservation;
    }

    public int getReserve_Id() {
        return reserve_Id;
    }

    public void setReserve_Id(int reserve_Id) {
        this.reserve_Id = reserve_Id;
    }

    public int getNumberOf_People() {
        return NumberOf_People;
    }

    public void setNumberOf_People(int numberOf_People) {
        NumberOf_People = numberOf_People;
    }

    public String getReservation_Status() {
        return Reservation_Status;
    }

    public void setReservation_Status(String reservation_Status) {
        Reservation_Status = reservation_Status;
    }

    public String getTablePreference() {
        return tablePreference;
    }

    public void setTablePreference(String tablePreference) {
        this.tablePreference = tablePreference;
    }
}
