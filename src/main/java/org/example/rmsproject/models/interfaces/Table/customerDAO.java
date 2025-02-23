package org.example.rmsproject.models.interfaces.Table;

import org.example.rmsproject.models.entity.Customer;

import java.util.List;

public interface customerDAO {
    void save(Customer customer);
    void saveCustomer(Customer customer);
    void delete(Customer customer);
    boolean saveOrUpdateCustomer(Customer customer);
    List<Customer> getAllCustomers();
    boolean isCustomerExists(String phoneNumber);
    Customer findCustomerByPhone(String phone);
}