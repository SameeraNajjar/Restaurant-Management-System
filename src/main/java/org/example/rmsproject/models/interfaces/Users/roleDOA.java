package org.example.rmsproject.models.interfaces.Users;

import org.example.rmsproject.models.Role;

import java.util.List;

public interface roleDOA {
    List<Role> getAllRoles();

    public interface RoleDAO {
        List<Role> getAllRoles();
    }
}
