package org.example.rmsproject.models.interfaces.Users;

import org.example.rmsproject.models.entity.Role;

import java.util.List;

public interface roleDAO {
    List<Role> getAllRoles();

    public interface RoleDAO {
        List<Role> getAllRoles();
    }
}
