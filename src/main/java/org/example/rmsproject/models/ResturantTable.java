package org.example.rmsproject.models;

import javax.persistence.*;
import javax.persistence.Table;
@Entity
@Table(name="ResturantTable")

public class ResturantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
