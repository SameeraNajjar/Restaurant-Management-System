package org.example.rmsproject.models.interfaces.Users;

import org.example.rmsproject.models.Users;

import java.util.List;

public interface UserDOA {
    void save(Users user);
    List<Users> getAllUsers();

    void delete(int id);

    void update(Users user);
}
