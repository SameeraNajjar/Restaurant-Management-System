package org.example.rmsproject.models.services.User;

import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.interfaces.Users.UserDOA;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class userDOAImp implements UserDOA {
    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public userDOAImp() {
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

}
