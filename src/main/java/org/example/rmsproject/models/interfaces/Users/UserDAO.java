package org.example.rmsproject.models.interfaces.Users;

import org.example.rmsproject.models.Users;

import java.util.List;

public interface UserDAO {
    void save(Users user);

    List<Users> getAllUsers();

    void delete(int id);

    void update(Users user);
    public List<Users> getAll(Users user);
    public Users   findEmployee(Users user);

    Users findByEmailAndPassword(String email, String password);

    boolean isEmailExists(String email);

    void saveNewUser(Users user);
}
