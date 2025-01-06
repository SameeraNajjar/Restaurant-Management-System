package org.example.rmsproject.ReservationController.customer;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.rmsproject.models.Customer;
import org.example.rmsproject.models.services.Table.DAOCustomer.customerDAOImp;
import org.example.rmsproject.models.services.Table.DAOfactoryCustomer.DAOFactoryCustomer;
import org.example.rmsproject.models.interfaces.Table.customerDAO;

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
        customerDAO  customerDAO = DAOFactoryCustomer.getCustomerDAO();
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customerDAO.getAllCustomers());
        customerTableView.setItems(customerData);

    }




    @FXML
    public void removeCustomerFromTable(Customer customer) {

        ObservableList<Customer> items = customerTableView.getItems();
        items.remove(customer);


        customerTableView.setItems(items);
    }
    public void refreshCustomerTable() {
        customerDAO  customerDAO = DAOFactoryCustomer.getCustomerDAO();
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