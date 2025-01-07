package org.example.rmsproject.Controllers.ReservationController.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.rmsproject.Controllers.ReservationController.SuccessFailedMessageManagmantController;
import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.interfaces.Table.customerDOA;
import org.example.rmsproject.models.services.Table.DAOCustomer.customerDOAImp;
import org.example.rmsproject.models.services.Table.DAOfactoryCustomer.DAOFactoryCustomer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerManagementController implements Initializable {
    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> idC;

    @FXML
    private TableColumn<Customer, String> nameC;

    @FXML
    private TableColumn<Customer, String> phoneC;
    @FXML
    private TableColumn<Customer, String> actionCol;
    @FXML
    private TextField searchField;
    @FXML
    private TextField customerNameF;
    @FXML
    private TextField customerPhoneF;
    private ObservableList<Customer> originalCustomer = FXCollections.observableArrayList();
    //Initializes the customer table with data from the database(This method is called when the controller is initialized)
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idC.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneC.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        actionCol.setCellFactory(new Callback<TableColumn<Customer, String>, TableCell<Customer, String>>() {
            @Override
            public TableCell<Customer, String> call(TableColumn<Customer, String> param) {
                return new TableCell<Customer, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5);
                            hbox.setAlignment(Pos.CENTER);


                            Button reserveButton = new Button();
                            ImageView reserveButtonIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/Edit.png")));
                            reserveButtonIcon.setFitWidth(15);
                            reserveButtonIcon.setFitHeight(15);
                            reserveButton.setGraphic(reserveButtonIcon);
                            reserveButton.setPrefSize(30, 30);
                            reserveButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");


                            Button cancelButton = new Button();
                            ImageView cancelButtonIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/delete.png")));
                            cancelButtonIcon.setFitWidth(15);
                            cancelButtonIcon.setFitHeight(15);
                            cancelButton.setGraphic(cancelButtonIcon);
                            cancelButton.setPrefSize(30, 30);
                            cancelButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");


                           reserveButton.setOnAction(e -> openEditCust(getTableRow().getItem()));
                            cancelButton.setOnAction(e -> openDeleteCust(getTableRow().getItem()));


                            hbox.getChildren().addAll(reserveButton, cancelButton);


                            setGraphic(hbox);
                        }
                    }
                };
            }})
;
        customerDOA  customerDAO = DAOFactoryCustomer.getCustomerDAO();
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customerDAO.getAllCustomers());
        customerTableView.setItems(customerData);

    }
    //Adds a new customer to the table and database after validation
    @FXML
    public void handleAddCustomer() {
        String customerName = customerNameF.getText();
        String customerPhone = customerPhoneF.getText();
        if (customerName.isEmpty() || customerPhone.isEmpty()) {
            showErrorMessage2("Please fill data in all fields.");
            return;
        }
        if (!customerPhone.matches("\\d{10}")) {
            showErrorMessage1();
            return;
        }
        customerDOAImp customerDOA = new customerDOAImp();
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customerDOA.getAllCustomers());
        Customer existingCustomer = customerData.stream()
                .filter(customer -> customer.getCustomerPhone().equals(customerPhone))
                .findFirst()
                .orElse(null);
        if (existingCustomer != null) {
            if (!existingCustomer.getCustomerName().equals(customerName)) {
                showErrorMessage2("This phone number is already registered with a different customer.");
                return;
            } else {
                showErrorMessage2("Customer with this name and phone number already exists!");
                return;
            }
        }
        Customer newCustomer = new Customer(customerName, customerPhone);
        customerTableView.getItems().add(newCustomer);
        customerDOA.saveCustomer(newCustomer);
        customerNameF.clear();
        customerPhoneF.clear();
        showSuccessMessage1();
    }
    //Displays a success message when a customer is found
    private void showSuccessMessage2(String message) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Text titleText = new Text("Success");
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: green;");
        Text contentText = new Text(message);
        contentText.setStyle("-fx-font-size: 14px;");
        Button okButton = new Button("OK");
        okButton.setOnAction(actionEvent -> alertStage.close());
        okButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-background-color: #B19251; -fx-text-fill: white; -fx-border-radius: 5px;");
        vbox.getChildren().addAll(titleText, contentText, okButton);
        vbox.setStyle("-fx-border-color: #B19251; -fx-border-width: 3px; -fx-padding: 10px; -fx-background-color: white;");
        Scene scene = new Scene(vbox, 600, 250);
        scene.setFill(Color.WHITE);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }
    //Displays an error message when the client is not found or enters an incorrect value.
    private void showErrorMessage2(String message) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Text titleText = new Text("Error");
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: red;");
        Text contentText = new Text(message);
        contentText.setStyle("-fx-font-size: 14px;");
        Button okButton = new Button("OK");
        okButton.setOnAction(actionEvent -> alertStage.close());
        okButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-background-color: #B19251; -fx-text-fill: white; -fx-border-radius: 5px;");
        vbox.getChildren().addAll(titleText, contentText, okButton);
        vbox.setStyle("-fx-border-color: #B19251; -fx-border-width: 3px; -fx-padding: 10px; -fx-background-color: white;");
        Scene scene = new Scene(vbox, 600, 250);
        scene.setFill(Color.WHITE);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }
    //Displays an error message when the phone number is invalid
    private void showErrorMessage1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/Customer_Failed_message.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            SuccessFailedMessageManagmantController FailedMessageController = loader.getController();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading the message window!");
        }
    }
    //This to find a customer by phone number
    @FXML
    public void handleSearch() {
        String searchQuery = searchField.getText();
        if (searchQuery.isEmpty()) {
            showErrorMessage2("Please enter a phone number to search.");
            return;
        }
        customerDOAImp customerDOA = new customerDOAImp();
        Customer foundCustomer = customerDOA.findCustomerByPhone(searchQuery);

        if (foundCustomer != null) {
            showSuccessMessage2("Customer found: " + foundCustomer.getCustomerName());
        } else {
            showErrorMessage2("Customer not found!");
        }
    }
    //Displays a success message after adding a new customer
    private void showSuccessMessage1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/Customer_success_message.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            SuccessFailedMessageManagmantController successMessageController = loader.getController();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while loading the message window!");
        }
    }
    @FXML
    public void removeCustomerFromTable(Customer customer) {

        ObservableList<Customer> items = customerTableView.getItems();
        items.remove(customer);


        customerTableView.setItems(items);
    }
    public void refreshCustomerTable() {
        customerDOA  customerDAO = DAOFactoryCustomer.getCustomerDAO();
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customerDAO.getAllCustomers());
        customerTableView.setItems(customerData);
        customerTableView.refresh();
    }



    @FXML
    public void openEditCust(Customer customer ) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/EditCustPop_Up.fxml"));
            Parent root = loader.load();
            EditCustPopUpController controller = loader.getController();
            controller.initializeData(customer,this);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException Exception) {
            Exception.printStackTrace();
        }
    }
    @FXML
    public void openDeleteCust(Customer customer) {
        if (customer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/deleteCustPop_Up.fxml"));
                Parent root = loader.load();
                DeleteCustPopUpController controller = loader.getController();
                controller.initializeData(customer, this);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }}
    }
}