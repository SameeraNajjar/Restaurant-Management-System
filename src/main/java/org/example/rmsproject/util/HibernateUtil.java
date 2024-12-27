package org.example.rmsproject.util;

import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.Role;
import org.example.rmsproject.models.ResturantTable;
import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.OrderItem;
import org.example.rmsproject.models.Permission;
import org.example.rmsproject.models.Menu;
import org.example.rmsproject.models.MenuItem;
import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.Category;
import org.example.rmsproject.models.Order;
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