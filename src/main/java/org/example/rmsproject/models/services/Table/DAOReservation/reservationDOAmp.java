package org.example.rmsproject.models.services.Table.DAOReservation;

import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.ResturantTable;
import org.example.rmsproject.models.interfaces.Table.customerDOA;
import org.example.rmsproject.models.interfaces.Table.reservationDOA;
import org.example.rmsproject.models.services.Table.DAOCustomer.customerDOAImp;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class reservationDOAmp implements reservationDOA {

    HibernateUtil hibUtil;
    SessionFactory sessionFactory;
    private final customerDOA customerService = new customerDOAImp();
    public reservationDOAmp() {
        hibUtil=HibernateUtil.getInstance();
        sessionFactory=hibUtil.getSessionFactory();
    }
    @Override
    public List<Customer> getAllCustomers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public void addCustomer(String name, String phone) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(name);
            newCustomer.setCustomerPhone(phone);
            session.save(newCustomer);
            session.getTransaction().commit();
            System.out.println("Customer ID: " + newCustomer.getCustomerId());

        } catch (Exception e) {
            System.out.println("Error while adding customer");
        }
    }
    @Override
    public Customer getCustomerByPhone(String phone) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Customer c WHERE c.customerPhone = :phone";
            org.hibernate.query.Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("phone", phone);
            return query.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public ResturantTable getTableById(int tableId) {
        try (Session session = sessionFactory.openSession()) {
            ResturantTable table = session.get(ResturantTable.class, tableId);
            if (table == null) {
                throw new EntityNotFoundException("Table with ID " + tableId + " not found.");
            }
            return table;
        } catch (Exception e) {
            System.err.println("Error while fetching table with ID " + tableId);
            throw new RuntimeException("Unable to fetch table with ID " + tableId, e);
        }
    }
    @Override
    public List<Integer> findAvailableTableByDataAndTime(Date date, Time time) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Executing query for date: " + date + ", time: " + time);

            return session.createQuery(
                            "SELECT t.table_id " +
                                    "FROM ResturantTable t " +
                                    "WHERE t.tableStatus = 'available' " +
                                    "AND NOT EXISTS (" +
                                    "    SELECT 1 FROM Reservation r " +
                                    "    WHERE r.restaurantTable.table_id = t.table_id " +
                                    "    AND r.dateReservation = :date " +
                                    "    AND r.timeReservation = :time " +
                                    ") ", Integer.class)
                    .setParameter("date", date)
                    .setParameter("time", time)
                    .list();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    @Override
    public void addReservation(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Customer existingCustomer = session.createQuery("FROM Customer WHERE customerPhone = :phone", Customer.class)
                    .setParameter("phone", reservation.getCustomer().getCustomerPhone())
                    .uniqueResult();

            if (existingCustomer == null) {
                session.save(reservation.getCustomer());
            } else {
                reservation.setCustomer(existingCustomer);
            }
            session.save(reservation);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error while adding reservation");
        }
    }
    @Override
    public void save(Reservation reservation) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
            System.out.println("Reservation saved successfully: " + reservation);
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Reservation> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String jpql = "SELECT r FROM Reservation r";

            System.out.println("Executing simplified JPQL query: " + jpql);
            List<Reservation> reservations = session.createQuery(jpql, Reservation.class).list();
            session.getTransaction().commit();
            if (reservations == null) {
                System.err.println("The query returned null.");
                return new ArrayList<>();
            } else if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                System.out.println("Number of reservations: " + reservations.size());
                for (Reservation reservation : reservations) {
                    System.out.println("Reservation: " + reservation);
                }
            }

            return reservations;
        } catch (Exception e) {
            System.err.println("Error while fetching reservations: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public void delete(Reservation reservation) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(reservation);
        session.getTransaction().commit();
        System.out.println("Reservation saved successfully: " + reservation);
        session.close();
    }

@Override
public void edit(Reservation reservation) {
    Session session = sessionFactory.openSession();
    try {
        session.beginTransaction();
        Reservation existingReservation = session.get(Reservation.class, reservation.getReservationId());
        if (existingReservation != null) {
            existingReservation.setDateReservation(reservation.getDateReservation());
            existingReservation.setTimeReservation(reservation.getTimeReservation());
            existingReservation.setNumberOfPeople(reservation.getNumberOfPeople());
            existingReservation.setTablePreference(reservation.getTablePreference());
            existingReservation.setReservationStatus(reservation.getReservationStatus());
            existingReservation.setRestaurantTable(reservation.getRestaurantTable());
            Customer existingCustomer = existingReservation.getCustomer();
            if (existingCustomer != null) {
                existingCustomer.setCustomerName(reservation.getCustomer().getCustomerName());
                session.update(existingCustomer);
            }

            session.update(existingReservation);
            session.getTransaction().commit();
            System.out.println("Reservation updated successfully: " + existingReservation);
        } else {
            System.out.println("Reservation with ID " + reservation.getReservationId() + " not found.");
            session.getTransaction().rollback();
        }
    } catch (Exception e) {
        if (session.getTransaction() != null) {
            session.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        session.close();
    }
}


    @Override
    public List<ResturantTable> getAvailableTables(java.sql.Date date, java.sql.Time time) {
        Session session = sessionFactory.openSession();
        List<ResturantTable> availableTables = new ArrayList<>();
        try {
            String hql = "FROM ResturantTable t WHERE t.table_id NOT IN (SELECT r.restaurantTable.table_id FROM Reservation r WHERE r.dateReservation = :date AND r.timeReservation = :time)";
            Query query = session.createQuery(hql);
            query.setParameter("date", date);
            query.setParameter("time", time);

            availableTables = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return availableTables;
    }




}
