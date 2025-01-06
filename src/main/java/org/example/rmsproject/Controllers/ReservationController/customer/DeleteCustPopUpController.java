package org.example.rmsproject.Controllers.ReservationController.customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.interfaces.Table.customerDOA;
import org.example.rmsproject.models.services.Table.DAOfactoryCustomer.DAOFactoryCustomer;

public class DeleteCustPopUpController {
    private Customer selectedCustomer;
    private CustomerManagementController parentController;


    public void closePopUp(ActionEvent btn_event) {
        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
        stage.close();
    }



private Customer customer;
    private CustomerManagementController controller;


    public void initializeData(Customer customer, CustomerManagementController parentController) {
        this.customer = customer;
        this.parentController = parentController;
    }

    @FXML
    private void confirmDelete(ActionEvent event) {

        customerDOA  customerDAO = DAOFactoryCustomer.getCustomerDAO();
        customerDAO.delete(customer);


        if (parentController != null) {
            parentController.removeCustomerFromTable(customer);
        }


        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
