package org.example.rmsproject.models.services.Menu;

import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.entity.MenuItem;
import org.example.rmsproject.models.interfaces.Menu.menuItemDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class menuItemDAOImp implements menuItemDAO {

    @Override
    public void save(MenuItem menuItem) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(menuItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void update(MenuItem menuItem) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(menuItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void delete(MenuItem menuItem) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(menuItem);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public MenuItem findById(int id) {
        Session session = null;
        MenuItem menuItem = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            menuItem = session.get(MenuItem.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return menuItem;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        Session session = null;
        List<MenuItem> menuItems = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            menuItems = session.createQuery("FROM MenuItem", MenuItem.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return menuItems;
    }

    public List<MenuItem> getMenuItemsByCategory(Category category) {
        Session session = null;
        List<MenuItem> menuItems = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM MenuItem WHERE category = :category";
            Query<MenuItem> query = session.createQuery(hql, MenuItem.class);
            query.setParameter("category", category);
            menuItems = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return menuItems;
    }
}
