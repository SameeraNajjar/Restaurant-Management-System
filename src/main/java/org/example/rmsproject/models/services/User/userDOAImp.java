
package org.example.rmsproject.models.services.User;

import org.example.rmsproject.models.Users;
import org.example.rmsproject.models.interfaces.Users.UserDOA;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class userDOAImp implements UserDOA {
    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    public userDOAImp() {
        hibernateUtil=HibernateUtil.getInstance();
        sessionFactory= HibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Users user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // تحقق من وجود البريد الإلكتروني مسبقًا
        if (isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // تشفير كلمة المرور
        // user.setPassword(hashPassword(user.getPassword()));

        // حفظ المستخدم
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    // دالة تشفير كلمة المرور
    //private String hashPassword(String password) {
    //   return Base64.getEncoder().encodeToString(password.getBytes());
    //}

    @Override
    public void updete(Users user) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.update(user );
        session.getTransaction().commit();
        session.close();


    }
    @Override
    public void delete(Users user) {
        Session session=sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user );
        session.getTransaction().commit();
        session.close();


    }
    @Override
    public List<Users > getAll(Users user) {
        return List.of();
    }
    @Override
    public Users  findEmployee(Users user) {
        return null;
    }

    @Override
    public Users findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Users WHERE email = :email AND password = :password", Users.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Users> result = query.getResultList();
        session.close();
        return result.isEmpty() ? null : result.get(0);  // إذا كانت النتيجة فارغة، الرجوع null
    }

    @Override
    public boolean isEmailExists(String email) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT COUNT(*) FROM Users WHERE email = :email");
        query.setParameter("email", email);
        long count = (long) query.getSingleResult();
        session.close();
        return count > 0; // إذا كان البريد الإلكتروني موجودًا
    }
    @Override
    public List<Users> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<Users> users = session.createQuery("FROM Users", Users.class).list();
        session.close();
        return users;
    }
}