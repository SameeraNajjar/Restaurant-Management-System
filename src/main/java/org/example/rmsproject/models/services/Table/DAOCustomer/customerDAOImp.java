package org.example.rmsproject.models.services.Table.DAOCustomer;

import org.example.rmsproject.models.interfaces.Table.customerDAO;
import org.example.rmsproject.util.HibernateUtil;
import org.example.rmsproject.models.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class customerDAOImp implements customerDAO {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;
    public customerDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    public void addCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(customer);  // Save or update the customer object
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    @Override
    public Customer findCustomerByPhone(String phone) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer WHERE customerPhone = :phone", Customer.class)
                    .setParameter("phone", phone)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isCustomerExists(String phoneNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.customerPhone = :phone")
                    .setParameter("phone", phoneNumber)
                    .uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}