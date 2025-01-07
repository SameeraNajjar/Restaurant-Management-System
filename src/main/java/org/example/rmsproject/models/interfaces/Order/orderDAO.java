package org.example.rmsproject.models.interfaces.Order;



import org.example.rmsproject.models.entity.Order;

import java.util.List;

public interface orderDAO {


    public List<Order> getAll(Order order);
    public void addOrder(Order order);


}