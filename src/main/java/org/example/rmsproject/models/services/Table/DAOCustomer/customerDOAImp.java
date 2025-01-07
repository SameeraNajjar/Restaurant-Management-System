package org.example.rmsproject.models.services.Table.DAOCustomer;

import org.example.rmsproject.models.entity.Customer;
import org.example.rmsproject.models.interfaces.Table.customerDAO;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class customerDOAImp implements customerDAO {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;
    public customerDOAImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    @Override
    public void saveCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error while saving customer");
        }

    }

        @Override
        public Customer findCustomerByPhone(String phone) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                return session.createQuery("FROM Customer WHERE customerPhone = :phone", Customer.class)
                        .setParameter("phone", phone)
                        .uniqueResult();
            } catch (Exception e) {
                return null;
            }
        }

        public boolean isCustomerExists(String phoneNumber) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                List<Customer> customers = session.createQuery("FROM Customer", Customer.class).list();
                Customer existingCustomer = customers.stream()
                        .filter(customer -> customer.getCustomerPhone().equals(phoneNumber))
                        .findFirst()
                        .orElse(null);
                return existingCustomer != null;
            } catch (Exception e) {
                return false;
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



}