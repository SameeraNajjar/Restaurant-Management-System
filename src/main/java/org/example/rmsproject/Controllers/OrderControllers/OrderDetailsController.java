package org.example.rmsproject.Controllers.OrderControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.rmsproject.models.entity.Order;
import org.example.rmsproject.models.entity.OrderItem;

import java.io.IOException;

public class OrderDetailsController {

    @FXML
    private Text orderIdText;
    @FXML
    private Text customerNameText;
    @FXML
    private Text itemsText;
    @FXML
    private Text specialInstructionsText;
    @FXML
    private Text statusText;
    @FXML
    private Text totalPriceText;
    @FXML
    private Button backButton;
    @FXML
    private GridPane gridPane;
    @FXML
    private Line verticalLine;
    private Order order;

    @FXML
    private void initialize() {
        verticalLine.endYProperty().bind(gridPane.heightProperty());
        backButton.setOnAction(event -> navigateBack());
    }

    public void setOrderDetails(Order order) {
        this.order = order;

        orderIdText.setText(String.valueOf(order.getOrderId()));
        customerNameText.setText(order.getCustomerName());
        specialInstructionsText.setText(order.getSpecialInstructions());
        statusText.setText(order.getStatus());
        totalPriceText.setText(String.format("$%.2f", order.getTotalPrice()));

        StringBuilder itemsString = new StringBuilder();
        for (OrderItem item : order.getItems()) {
            itemsString.append(item.getItemName())
                    .append(" (x").append(item.getQuantity())
                    .append(")\n");
        }
        itemsText.setText(itemsString.toString());
    }

    private void navigateBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Order_Management/ViewOrder.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("View Orders");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

