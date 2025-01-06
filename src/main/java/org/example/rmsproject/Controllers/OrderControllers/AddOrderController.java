package org.example.rmsproject.Controllers.OrderControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
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

    private String[] items = {"item1", "item2", "item3"};

    @FXML
    private VBox vbox;

    @FXML
    private Spinner<Integer> QunatitySpinner;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        QunatitySpinner.setValueFactory(valueFactory);

        itemsBox.getItems().addAll(items);

        itemsBox.setOnAction(event -> handleAddItem());
    }

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
            deleteButton.setStyle("-fx-background-color:  #9A2727 ");
            deleteButton.setOnAction(event -> vbox.getChildren().remove(itemHBox));

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

    public void CancelOrder(ActionEvent event) {
        System.out.println("The order has been cancelled successfully");
    }
}
