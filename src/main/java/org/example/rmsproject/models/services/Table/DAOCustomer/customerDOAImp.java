package org.example.rmsproject.models.services.Table.DAOCustomer;

import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.interfaces.Table.customerDOA;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class customerDOAImp implements customerDOA {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;
    public customerDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    @Override
    public void save(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }
    @Override
    public void delete(Customer customer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(customer);
        session.getTransaction().commit();
        System.out.println("Reservation saved successfully: " + customer);
        session.close();
    }

    @Override
    public boolean saveOrUpdateCustomer(Customer customer) {
    try (Session session = sessionFactory.openSession()) {
        session.beginTransaction();
        session.saveOrUpdate(customer); // This will insert or update based on the customer's ID
        session.getTransaction().commit();
        return true;  // Indicate success
    } catch (Exception e) {
        e.printStackTrace();
        return false;  // Indicate failure
    }
}

    @Override
    public List<Customer> getAllCustomers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



}