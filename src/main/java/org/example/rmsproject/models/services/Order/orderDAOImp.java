package org.example.rmsproject.models.services.Order;



import org.example.rmsproject.models.entity.Order;
import org.example.rmsproject.models.entity.OrderItem;

import org.example.rmsproject.models.interfaces.Order.orderDAO;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;


public class orderDAOImp implements orderDAO {


    @Override
    public List<Order> getAll(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Order> orders = session.createQuery("FROM Order o LEFT JOIN FETCH o.items", Order.class).list();

            for (Order o : orders) {
                System.out.println("Order ID: " + o.getOrderId());
                if (o.getItems() != null && !o.getItems().isEmpty()) {
                    for (OrderItem item : o.getItems()) {
                        System.out.println("  Item: " + item.getItemName());
                    }
                } else {
                    System.out.println("  No items found for this order.");
                }
            }

            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void addOrder(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.save(order);

            if (order.getItems() != null) {
                for (OrderItem item : order.getItems()) {
                    session.save(item);
                }
            }

            session.getTransaction().commit();
            System.out.println("Order added successfully with ID: " + order.getOrderId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add the order.");
        }
    }

}

