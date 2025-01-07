package org.example.rmsproject.util;

import org.example.rmsproject.models.entity.Users;
import org.example.rmsproject.models.entity.Role;
import org.example.rmsproject.models.entity.ResturantTable;
import org.example.rmsproject.models.entity.Reservation;
import org.example.rmsproject.models.entity.OrderItem;
import org.example.rmsproject.models.entity.Permission;
import org.example.rmsproject.models.entity.Menu;
import org.example.rmsproject.models.entity.MenuItem;
import org.example.rmsproject.models.entity.Customer;
import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.entity.Order;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance = null;

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    private HibernateUtil(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Users.class);
         configuration.addAnnotatedClass(Menu.class);
        configuration.addAnnotatedClass(MenuItem.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(OrderItem.class);
        configuration.addAnnotatedClass(Permission.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Reservation.class);
        configuration.addAnnotatedClass(ResturantTable.class);
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateUtil getInstance(){
        if(instance == null){
            instance  = new HibernateUtil();
        }
        return instance;
    }

    public synchronized static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}