package org.example.rmsproject.models.services.Table.DAOReservation;

import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.ResturantTable;
import org.example.rmsproject.models.interfaces.Table.reservationDOA;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class reservationDOAmp implements reservationDOA {

    HibernateUtil hibUtil;
    SessionFactory sessionFactory;
    public reservationDOAmp() {
        hibUtil=HibernateUtil.getInstance();
        sessionFactory=hibUtil.getSessionFactory();
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
