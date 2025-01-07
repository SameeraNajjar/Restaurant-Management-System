package org.example.rmsproject.Controllers.OrderControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.rmsproject.models.Order;
import org.example.rmsproject.models.OrderItem;
import org.example.rmsproject.models.services.Order.orderDOAImp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrderController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private ComboBox<String> itemsBox;

    private String[] items = {"pizza", "pasta", "burger"};

    @FXML
    private TextField customerName;

    @FXML
    private TextField SpecialInstructions;

    @FXML
    private VBox vbox;

    @FXML
    private Button saveOrder;

    @FXML
    private Spinner<Integer> QunatitySpinner;

    private orderDOAImp orderDao = new orderDOAImp();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        QunatitySpinner.setValueFactory(valueFactory);

        itemsBox.getItems().addAll(items);

        itemsBox.setOnAction(event -> handleAddItem());
    }

    @FXML
    private void handleAddItem() {
        String selectedItem = itemsBox.getValue();
        if (selectedItem != null && !selectedItem.isEmpty()) {
            HBox itemHBox = new HBox(10);

            TextField itemTextField = new TextField(selectedItem);
            itemTextField.setEditable(false);
            itemTextField.setPrefWidth(165);

            Spinner<Integer> quantitySpinner = new Spinner<>();
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
            quantitySpinner.setValueFactory(spinnerValueFactory);
            quantitySpinner.setPrefWidth(160);

            Button deleteButton = new Button("X");
            deleteButton.setStyle("-fx-background-color: #9A2727;");
            deleteButton.setOnAction(this::handleDeleteAction);

            itemHBox.getChildren().addAll(itemTextField, quantitySpinner, deleteButton);

            vbox.getChildren().add(itemHBox);
        }
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        Button deleteButton = (Button) event.getSource();
        HBox parentHBox = (HBox) deleteButton.getParent();
        vbox.getChildren().remove(parentHBox);
    }

    @FXML
    private void handleSubmitOrder(ActionEvent event) {

        String name = customerName.getText();
        String instructions = SpecialInstructions.getText();

        List<OrderItem> orderItems = new ArrayList<>();


        for (javafx.scene.Node node : vbox.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;

                TextField itemTextField = (TextField) hbox.getChildren().get(0);
                Spinner<Integer> quantitySpinner = (Spinner<Integer>) hbox.getChildren().get(1);

                String itemName = itemTextField.getText();
                int quantity = quantitySpinner.getValue();


                OrderItem orderItem = new OrderItem();
                orderItem.setItemName(itemName);
                orderItem.setQuantity(quantity);

                orderItems.add(orderItem);
            }
        }


        Order order = new Order();
        order.setCustomerName(name);
        order.setSpecialInstructions(instructions);
        order.setStatus("Pending");
        order.setTotalPrice(calculateTotalPrice(orderItems));
        order.setItems(orderItems);


        orderDao.addOrder(order);


        System.out.println("Order has been successfully added!");
    }

    private double calculateTotalPrice(List<OrderItem> orderItems) {
        double total = 0.0;
        for (OrderItem item : orderItems) {
            total += item.getQuantity() * 10.0;
        }
        return total;
    }

    @FXML
    public void CancelOrder(ActionEvent event) {
        System.out.println("The order has been cancelled successfully");
    }
}
