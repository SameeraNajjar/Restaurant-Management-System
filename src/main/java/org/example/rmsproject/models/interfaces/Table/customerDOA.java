package org.example.rmsproject.models.interfaces.Table;

import org.example.rmsproject.models.Customer;

import java.util.List;

public interface customerDOA {
    void save(Customer customer);
    void delete(Customer customer);
    boolean saveOrUpdateCustomer(Customer customer);
    List<Customer> getAllCustomers();

}