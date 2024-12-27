package org.example.rmsproject.models.interfaces.Users;

import org.example.rmsproject.models.Users;

import java.util.List;

public interface UserDOA {
    public void save(Users user);
    public void updete(Users user);
    public void delete(Users user);
    public List<Users> getAll(Users user);
    public Users   findEmployee(Users user);

    Users findByEmailAndPassword(String email, String password);

    boolean isEmailExists(String email);

    List<Users> getAllUsers();
}
