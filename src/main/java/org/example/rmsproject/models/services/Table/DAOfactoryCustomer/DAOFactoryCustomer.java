package org.example.rmsproject.models.services.Table.DAOfactoryCustomer;
import org.example.rmsproject.models.interfaces.Table.customerDAO;
import org.example.rmsproject.models.services.Table.DAOCustomer.customerDOAImp;

public class DAOFactoryCustomer {
    public static customerDAO getCustomerDAO() {
        return new customerDOAImp();
    }
}
