package org.example.rmsproject.models.interfaces.Order;



import org.example.rmsproject.models.Order;

import java.util.List;

public interface orderDOA {


    public List<Order> getAll(Order order);
    public void addOrder(Order order);


}