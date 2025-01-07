package org.example.rmsproject.Controllers.ReservationController;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import org.example.rmsproject.models.entity.Customer;
import org.example.rmsproject.models.entity.Reservation;
import org.example.rmsproject.models.entity.ResturantTable;
import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.models.services.Table.DAOReservation.reservationDAOmp;
import javafx.scene.paint.Color;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddReservationController {
    @FXML
    private Spinner<Integer> hoursSpinner;
    @FXML
    private ListView<String> availableTablesList;
    @FXML
    private DatePicker dateId;
    @FXML
    private Spinner<Integer> minsSpinner;
    @FXML
    private Text noTablesAvailable;
    @FXML
    private AnchorPane anchorPaneTable;
    @FXML
    private AnchorPane anchorPaneCustomer;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField numberOfPeopleField;
    @FXML
    private Button confirmReservationButton;
    @FXML
    private ComboBox<String> tablePreferenceComboBox;
    private String selectedTableId;
    private Date selectedDate;
    private Time selectedTime;
    private String tablePreference;
    private final reservationDAO reservationService = new reservationDAOmp();

    //Set initial values for UI elements such as spinners, combobox, etc.
    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12);
        hoursSpinner.setValueFactory(hoursFactory);
        SpinnerValueFactory<Integer> minsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minsSpinner.setValueFactory(minsFactory);
        noTablesAvailable.setText("");
        availableTablesList.getItems().clear();
        anchorPaneCustomer.setVisible(false);
        tablePreferenceComboBox.getItems().addAll("Near Window", "Inside", "Outside");
        tablePreferenceComboBox.setPromptText("Select Table Preference");
        availableTablesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<String> selectedItems = availableTablesList.getSelectionModel().getSelectedItems()
                    .stream()
                    .filter(item -> item != null && !item.isEmpty())
                    .collect(Collectors.toList());

            if (!selectedItems.isEmpty()) {
                selectedTableId = selectedItems.get(0).split(": ")[1];
                confirmReservationButton.setDisable(false);
                anchorPaneTable.setVisible(false);
                anchorPaneCustomer.setVisible(true);
            }
        });
        confirmReservationButton.setDisable(true);
    }

    //Searches for available tables based on the selected date and time.
    @FXML
    public void Find_Table() {
        try {
            if (Stream.of(dateId.getValue(), hoursSpinner.getValue(), minsSpinner.getValue())
                    .anyMatch(value -> value == null)) {
                showAlertMessage("Error Input", "Please make sure all date and time fields are selected !");
                return;
            }
            java.sql.Date date = java.sql.Date.valueOf(dateId.getValue());
            java.sql.Date today = java.sql.Date.valueOf(java.time.LocalDate.now());
            if (date.before(today)) {
                showAlertMessage("Error Select", "You cannot select a past date!, Please just choose today's date or a future date.");
                return;
            }
            int hours = hoursSpinner.getValue();
            int minutes = minsSpinner.getValue();
            Time time = Time.valueOf(String.format("%02d:%02d:00", hours, minutes));
            List<Integer> availableTableIds = reservationService.findAvailableTableByDataAndTime(date, time);

            if (availableTableIds.isEmpty()) {
                showAlertMessage("No Tables Available",
                        "Unfortunately, no tables are available for the selected date and time! ,Please try another time.");
                availableTablesList.getItems().clear();
                confirmReservationButton.setDisable(true);
            } else {
                noTablesAvailable.setText("");
                availableTablesList.getItems().clear();
                availableTableIds.stream()
                        .map(tableId -> "Table Number: " + tableId)
                        .forEach(availableTablesList.getItems()::add);
                confirmReservationButton.setDisable(true);
            }
        } catch (Exception e) {
            showAlertMessage("Error", "Please check your inputs and try again.");
        }
    }

    //Validates inputs, confirms the reservation, and saves the data in DB.
    @FXML
    public void confirmReservation() {
        try {
            boolean hasEmptyFields = Stream.of(
                            dateId.getValue(),
                            hoursSpinner.getValue(),
                            minsSpinner.getValue(),
                            selectedTableId,
                            customerNameField.getText(),
                            customerPhoneField.getText(),
                            numberOfPeopleField.getText())
                    .anyMatch(value -> value == null || value.toString().trim().isEmpty());

            if (hasEmptyFields) {
                showAlertMessage("Error Input", "Please ensure all fields are filled before confirming.");
                return;
            }
            if (tablePreferenceComboBox.getValue() == null || tablePreferenceComboBox.getValue().trim().isEmpty()) {
                showAlertMessage("Error select", "Please select a table preference before confirming the reservation.");
                return;
            }
            String customerPhone = customerPhoneField.getText().trim();
            if (!customerPhone.matches("\\d{10}")) {
                showAlertMessage("Error Input", "Phone number must be exactly 10 digits.");
                return;
            }
            List<Customer> customers = reservationService.getAllCustomers();
            boolean isPhoneExist = customers.stream()
                    .anyMatch(customer -> customer.getCustomerPhone().equals(customerPhone));

            if (isPhoneExist) {
                Customer customerWithSamePhone = customers.stream()
                        .filter(customer -> customer.getCustomerPhone().equals(customerPhone))
                        .findFirst()
                        .orElse(null);
                if (customerWithSamePhone != null && customerWithSamePhone.getCustomerName().equals(customerNameField.getText().trim())) {
                    System.out.println("The customer with the same phone number exists with the same name.");

                } else {
                    showAlertMessage("Error Reservation", "This phone number is already associated with another customer,The reservation cannot be made!");
                    return;
                }
            }
            Customer customer;
            if (!isPhoneExist) {
                customer = new Customer(customerNameField.getText().trim(), customerPhone);
                reservationService.addCustomer(customerNameField.getText().trim(), customerPhone);
            } else {
                customer = reservationService.getCustomerByPhone(customerPhone);
            }
            int numberOfPeople;
            try {
                numberOfPeople = Integer.parseInt(numberOfPeopleField.getText().trim());
            } catch (NumberFormatException e) {
                showAlertMessage("Error Input", "Please enter a valid number for the number of people.");
                return;
            }
            selectedDate = java.sql.Date.valueOf(dateId.getValue());
            selectedTime = Time.valueOf(String.format("%02d:%02d:00", hoursSpinner.getValue(), minsSpinner.getValue()));
            tablePreference = tablePreferenceComboBox.getValue();
            Reservation reservation = new Reservation();
            reservation.setDateReservation(selectedDate);
            reservation.setTimeReservation(selectedTime);
            reservation.setTablePreference(tablePreference);
            reservation.setNumberOfPeople(numberOfPeople);
            reservation.setCustomer(customer);

            ResturantTable table = reservationService.getTableById(Integer.parseInt(selectedTableId));
            reservation.setRestaurantTable(table);

            reservationService.addReservation(reservation);
            showAlertMessage("Reservation Confirmed", "Your reservation has been confirmed successfully.");
            clearFields();
            anchorPaneCustomer.setVisible(false);
            anchorPaneTable.setVisible(true);
        } catch (Exception e) {
            showAlertMessage("Error", "An error occurred while saving the reservation.");
        }
    }

    //Clears all input fields after confirming the reservation.
    private void clearFields() {
        customerNameField.clear();
        customerPhoneField.clear();
        numberOfPeopleField.clear();
        tablePreferenceComboBox.getSelectionModel().clearSelection();
        dateId.setValue(null);
        hoursSpinner.getValueFactory().setValue(12);
        minsSpinner.getValueFactory().setValue(0);
        availableTablesList.getItems().clear();
    }

    //Displays an alert message with a specific design to the user.
    private void showAlertMessage(String title, String content) {
        Stage alertStage = new Stage();
        alertStage.initStyle(StageStyle.UNDECORATED);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Text contentText = new Text(content);
        contentText.setStyle("-fx-font-size: 14px;");
        Button okButton = new Button("OK");
        okButton.setOnAction(actionEvent -> alertStage.close());
        okButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-background-color: #B19251; -fx-text-fill: white; -fx-border-radius: 5px;");
        vbox.getChildren().addAll(titleText, contentText, okButton);
        vbox.setStyle("-fx-border-color: #B19251; -fx-border-width: 3px; -fx-padding: 10px; -fx-background-color: white; ");
        Scene scene = new Scene(vbox, 650, 250);
        scene.setFill(Color.WHITE);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }


}
