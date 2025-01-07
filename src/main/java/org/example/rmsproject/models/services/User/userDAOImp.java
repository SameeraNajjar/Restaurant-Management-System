package org.example.rmsproject.models.services.User;

import org.example.rmsproject.models.entity.Users;
import org.example.rmsproject.models.interfaces.Users.UserDAO;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class userDAOImp implements UserDAO {
    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public userDAOImp() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    @Override
    public void save(Users users) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(users);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error in saving Users",e);
        }finally {
            session.close();
        }
    }
    @Override
    public void saveNewUser(Users user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        if (isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public List<Users> getAllUsers() {
        Session session = sessionFactory.openSession();
        try {
            Query<Users> query = session.createQuery("from Users", Users.class);
            return query.list();
        }catch (Exception e) {
            throw new RuntimeException("Error retrieving users", e);
        }
        finally {
            session.close();
        }
    }
    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Users user = session.get(Users.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with ID: " + id, e);
        }
    }

    @Override
    public void update(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public List<Users> getAll(Users user) {
        return List.of();
    }

    @Override
    public Users findEmployee(Users user) {
        return null;
    }

    @Override
    public Users findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        javax.persistence.Query query = session.createQuery("FROM Users WHERE email = :email AND password = :password", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Users> result = query.getResultList();
        session.close();
        return result.isEmpty() ? null : result.get(0);

    }

    @Override
    public boolean isEmailExists(String email) {
        Session session = sessionFactory.openSession();
        javax.persistence.Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE email = :email");
        query.setParameter("email", email);
        long count = (long) query.getSingleResult();
        session.close();
        return count > 0;
    }

    public Users findByEmail(String email) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Users WHERE email = :email";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Librarian by email", e);
        } finally {
            session.close();
        }
    }
    @Override
    public List<Users> searchUsersByNameOrEmail(String keyword) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Users WHERE name LIKE :keyword OR email LIKE :keyword";
            Query<Users> query = session.createQuery(hql, Users.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching users", e);
        } finally {
            session.close();
        }
    }

}