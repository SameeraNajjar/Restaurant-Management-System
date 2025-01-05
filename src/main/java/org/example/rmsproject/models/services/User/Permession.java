package org.example.rmsproject.models.services.User;

import javax.persistence.*;

@Entity
@Table(name = "Permissions")
public class Permession {
    @Id
    @GeneratedValue()
    private int id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    public Permession() {}

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getPermissionName() {

        return permissionName;
    }

    public void setPermissionName(String permissionName) {

        this.permissionName = permissionName;
    }
}
