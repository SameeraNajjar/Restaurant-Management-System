package org.example.rmsproject.models.services.Table.DAOfactoryCustomer;
import org.example.rmsproject.models.interfaces.Table.customerDOA;
import org.example.rmsproject.models.services.Table.DAOCustomer.customerDOAImp;

public class DAOFactoryCustomer {
    public static customerDOA getCustomerDAO() {
        return new customerDOAImp();
    }
}
