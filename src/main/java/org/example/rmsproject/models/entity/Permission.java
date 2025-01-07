package org.example.rmsproject.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "Permissions")
public class Permission {
    @Id
    @GeneratedValue()
    private int id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

    public Permission() {}

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
