package org.example.rmsproject.models.services.User;

import org.example.rmsproject.models.Role;
import org.example.rmsproject.models.interfaces.Users.roleDOA;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class roleDOAImp  implements roleDOA {


    SessionFactory sessionFactory;
    public roleDOAImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Role> getAllRoles() {
        Session session = sessionFactory.openSession();
        List<Role> roles = session.createQuery("FROM Role", Role.class).list();
        session.close();
        return roles;
    }

}
