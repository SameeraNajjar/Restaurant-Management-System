package org.example.rmsproject.Controllers.OrderControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.rmsproject.models.Order;
import org.example.rmsproject.models.OrderItem;
import org.example.rmsproject.models.interfaces.Order.orderDOA;
import org.example.rmsproject.models.services.Order.orderDOAImp;

import java.io.IOException;
import java.util.List;

public class ViewOrderController {

    @FXML
    private TableColumn<Order, String> orderIdColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, String> totalPriceColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private TableView<Order> ordersTable;
@FXML
private Button addOrderButton;

    @FXML
    private void handleViewDetailsButtonAction(ActionEvent event) {
        System.out.println("Button clicked!");

        Order selectedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an order to view details.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetails.fxml"));
            Parent orderDetailsRoot = loader.load();

            OrderDetailsController controller = loader.getController();
            controller.setOrderDetails(selectedOrder);

            Stage stage = (Stage) viewDetailsButton.getScene().getWindow();
            stage.setScene(new Scene(orderDetailsRoot));
            stage.setTitle("Order Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Order Details.", ButtonType.OK);
            alert.showAndWait();
        }
    }








    public void initialize() {

        orderDOA ordersDOA = new orderDOAImp();
        List<Order> orders = ordersDOA.getAll(null);
        System.out.println("Orders fetched: " + orders.size());

        ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
        ordersTable.setItems(observableOrders);

        // Table column mappings
        orderIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOrderId())));
        itemsColumn.setCellValueFactory(cellData -> {
            List<OrderItem> items = cellData.getValue().getItems();
            if (items == null || items.isEmpty()) {
                return new SimpleStringProperty("No items");
            }
            StringBuilder itemsString = new StringBuilder();
            for (OrderItem item : items) {
                itemsString.append(item.getItemName())
                        .append(" (x")
                        .append(item.getQuantity())
                        .append(")\n");
            }
            return new SimpleStringProperty(itemsString.toString().trim());
        });


        totalPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%.2f", cellData.getValue().getTotalPrice())));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        addButtonsToOrderIdColumn();
    }
    @FXML
    private void handleAddOrderButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Order_Management/AddOrder.fxml"));
            Parent addOrderRoot = loader.load();

            Stage stage = (Stage) addOrderButton.getScene().getWindow();
            stage.setScene(new Scene(addOrderRoot));
            stage.setTitle("Add Order");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Add Order page.", ButtonType.OK);
            alert.showAndWait();
        }
    }





    private void addButtonsToOrderIdColumn() {
        orderIdColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> param) {
                return new TableCell<>() {
                    private final HBox hBox = new HBox(10);
                    private final Text orderIdText = new Text();
                    private final Button detailsButton = new Button("📄");

                    {
                        orderIdText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #b89555;");
                        orderIdText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

                        detailsButton.setFont(Font.font(12));
                        detailsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        detailsButton.setPrefSize(28, 25);

                        detailsButton.setOnAction(event -> {
                            Order selectedOrder = getTableView().getItems().get(getIndex());
                            openOrderDetails(selectedOrder);
                        });

                        hBox.getChildren().addAll(orderIdText, detailsButton);
                        hBox.setAlignment(Pos.CENTER);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            orderIdText.setText(item);
                            setGraphic(hBox);
                        }
                    }

                    private void openOrderDetails(Order order) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Order_Management/OrderDetails.fxml"));
                            Parent orderDetailsRoot = loader.load();

                            OrderDetailsController controller = loader.getController();
                            controller.setOrderDetails(order);

                            Stage stage = (Stage) detailsButton.getScene().getWindow();
                            stage.setScene(new Scene(orderDetailsRoot));
                            stage.setTitle("Order Details");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Order Details.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                };
            }
        });

    }

}

