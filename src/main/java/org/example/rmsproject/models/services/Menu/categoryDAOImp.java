package org.example.rmsproject.models.services.Menu;

import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.interfaces.Menu.categoryDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.rmsproject.util.HibernateUtil;

import java.util.List;

public class categoryDAOImp implements categoryDAO {

    @Override
    public void save(Category category) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void update(Category category) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void delete(Category category) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Category findById(int id) {
        Session session = null;
        Category category = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            category = session.get(Category.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        Session session = null;
        List<Category> categories = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            categories = session.createQuery("FROM Category", Category.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return categories;
    }
}
