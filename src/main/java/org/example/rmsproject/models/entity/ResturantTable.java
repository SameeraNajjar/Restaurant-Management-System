package org.example.rmsproject.models.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resturant_table")
public class ResturantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private int table_id;

    @Column(name = "table_status", nullable = false, length = 20)
    private String tableStatus;

    @OneToMany(mappedBy = "restaurantTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    public ResturantTable() {
    }
    public ResturantTable(int tableId) {
        this.table_id = tableId;
    }
    public int getTableId() {
        return table_id;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void setTableId(int tableId) {

    }
}