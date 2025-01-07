package org.example.rmsproject.Controllers.ReservationController.customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.rmsproject.models.entity.Customer;
import org.example.rmsproject.models.services.Table.DAOfactoryCustomer.DAOFactoryCustomer;

public class EditCustPopUpController {

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField customerPhoneField;

    private Customer customer;
    private org.example.rmsproject.models.interfaces.Table.customerDAO customerDAO = DAOFactoryCustomer.getCustomerDAO();

    private CustomerManagementController customerManagementController;

    public void initializeData(Customer customer ,CustomerManagementController parentController) {
        this.customer = customer;
        customerNameField.setText(customer.getCustomerName());
        customerPhoneField.setText(customer.getCustomerPhone());
        this.customerManagementController = parentController;
    }

    @FXML
    public void handleSaveCustomer(ActionEvent event) {
        String updatedName = customerNameField.getText();
        String updatedPhone = customerPhoneField.getText();
        if (updatedName.isEmpty() || updatedPhone.isEmpty()) {
            showErrorMessage("Please fill in all fields.");
            return;
        }
        if (updatedPhone.length() != 10) { showErrorMessage("Phone number must be exactly 10 digits."); return; }

        customer.setCustomerName(updatedName);
        customer.setCustomerPhone(updatedPhone);


        boolean updateSuccessful = customerDAO.saveOrUpdateCustomer(customer);


        if (updateSuccessful) {
            showSuccessMessage("Customer details updated successfully.");
            customerManagementController.refreshCustomerTable();
            closePopUp(event);
        } else {
            showErrorMessage("Error updating customer details. Please try again.");
        }
    }


    @FXML
    public void handleCancel() {

    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
    }


    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle("Success");
        alert.showAndWait();
    }


    public void closePopUp(ActionEvent btn_event) {
        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
        stage.close();
    }


}
