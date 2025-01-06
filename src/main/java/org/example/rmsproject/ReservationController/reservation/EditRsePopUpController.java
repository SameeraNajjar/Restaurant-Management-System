//package org.example.rmsproject.ReservationController.reservation;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import org.example.rmsproject.models.Reservation;
//import org.example.rmsproject.models.ResturantTable;
//import org.example.rmsproject.models.services.Table.DAOfactoryReservation.DAOFactoryReservation;
//import org.example.rmsproject.models.interfaces.Table.reservationDAO;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class EditRsePopUpController {
//
//    @FXML
//    private TextField customerNameField;
//
//    @FXML
//    private TextField TablePreferenceCol;
//
//    @FXML
//    private DatePicker reservationDateField;
//
//    @FXML
//    private TextField reservationTimeField;
//
//    @FXML
//    private TextField reservationPeopleNumField;
//
//    @FXML
//    private TextField reservationTiablleNumField;
//
//    @FXML
//    private RadioButton doneRadio;
//
//    @FXML
//    private RadioButton notYetRadio;
//
//    @FXML
//    private Button EditProcessEditbtn;
//
//    @FXML
//    private ToggleGroup statusGroup;
//
//    private Reservation currentReservation; // To hold the current reservation being edited
//    private ReservationsManagementController parentController;
//
//    public void initialize() {
//        // Set up the ToggleGroup for the radio buttons
//        statusGroup = new ToggleGroup();
//        doneRadio.setToggleGroup(statusGroup);
//        notYetRadio.setToggleGroup(statusGroup);
//        // Add event listener to the Edit button
//        EditProcessEditbtn.setOnAction(this::handleEditProcess);
//    }
//
//    public void refreshData(ReservationsManagementController parentController) {
//        this.parentController = parentController;
//    }
//
//    public void initializeData(Reservation reservation, ReservationsManagementController parentController) {
//        currentReservation = reservation;
//        this.parentController = parentController;
//
//        // Populate the fields with reservation data
//        customerNameField.setText(reservation.getCustomer().getCustomerName());
//        TablePreferenceCol.setText(reservation.getTablePreference().toString());
//
//        // Convert Date to LocalDate and set it to the DatePicker
//        if (reservation.getDateReservation() != null) {
//            reservationDateField.setValue(reservation.getDateReservation().toInstant()
//                    .atZone(ZoneId.systemDefault()).toLocalDate());
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Missing Data");
//            alert.setHeaderText("Date Missing");
//            alert.setContentText("The reservation date is missing. Please check the reservation details.");
//            alert.showAndWait();
//        }
//
//        // Convert Time to a string and set it to reservationTimeField
//        if (reservation.getTimeReservation() != null) {
//            reservationTimeField.setText(reservation.getTimeReservation().toString());
//        }
//
//        // Set the number of people to the corresponding field
//        reservationPeopleNumField.setText(String.valueOf(reservation.getNumberOfPeople()));
//
//        // Set the table number to the corresponding field
//        reservationTiablleNumField.setText(String.valueOf(reservation.getRestaurantTable().getTableId()));
//
//        // Set the status radio button
//        if (reservation.isDone()) {
//            doneRadio.setSelected(true);
//        } else {
//            notYetRadio.setSelected(true);
//        }
//    }
//
////    @FXML
////    private void handleEditProcess(ActionEvent event) {
////        // Retrieve updated information
////        String updatedCustomerName = customerNameField.getText();
////        LocalDate updatedReservationDate = reservationDateField.getValue();
////        String updatedReservationTime = reservationTimeField.getText();
////        String updatedPeopleNum = reservationPeopleNumField.getText();
////        String updatedTableNum = reservationTiablleNumField.getText(); // Fixed variable name
////        boolean isDone = doneRadio.isSelected();
////
////        // Validate input
////        if (updatedCustomerName.isEmpty() || updatedReservationDate == null || updatedReservationTime.isEmpty()
////                || updatedPeopleNum.isEmpty() || updatedTableNum.isEmpty()) {
////            showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please fill in all fields before saving.");
////            return;
////        }
////
////        try {
////            // Validate and parse time
////            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
////            java.sql.Time updatedTime;
////            try {
////                LocalTime parsedTime = LocalTime.parse(updatedReservationTime, timeFormatter);
////                updatedTime = java.sql.Time.valueOf(parsedTime.toString() + ":00");
////            } catch (DateTimeParseException e) {
////                showAlert(Alert.AlertType.ERROR, "Invalid Time Format", null, "Please enter a valid time in HH:mm format.");
////                return;
////            }
////
////            // Validate and parse numbers
////            int peopleNum, tableNum;
////            try {
////                peopleNum = Integer.parseInt(updatedPeopleNum);
////                tableNum = Integer.parseInt(updatedTableNum);
////            } catch (NumberFormatException e) {
////                showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please enter valid numbers for people and table fields.");
////                return;
////            }
////
////            // Validate currentReservation object
////            if (currentReservation == null) {
////                showAlert(Alert.AlertType.ERROR, "Error", null, "No reservation selected for updating.");
////                return;
////            }
////
////            // Convert LocalDate to java.sql.Date
////            java.sql.Date sqlDate = java.sql.Date.valueOf(updatedReservationDate);
////
////            // Fetch available tables for the updated date and time
////            reservationDAO reservationDao = DAOFactoryReservation.getCustomerDAO();
////            List<ResturantTable> availableTables = reservationDao.getAvailableTables(sqlDate, updatedTime);
////
////            // Check if the entered table number is available
////            boolean isTableAvailable = availableTables.stream().anyMatch(table -> table.getTableId() == tableNum);
////
////            if (!isTableAvailable) {
////                // Show alert with available tables
////                String availableTableIds = availableTables.stream()
////                        .map(table -> String.valueOf(table.getTableId()))
////                        .collect(Collectors.joining(", "));
////
////                showAlert(Alert.AlertType.WARNING, "Selected Table Unavailable", null,
////                        "The selected table is not available at the chosen time. Available tables are: " + availableTableIds);
////                return;
////            }
////
////            // Update the Reservation object
////            currentReservation.getCustomer().setCustomerName(updatedCustomerName);
////            currentReservation.setDateReservation(java.util.Date.from(updatedReservationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
////            currentReservation.setTimeReservation(updatedTime);
////            currentReservation.setNumberOfPeople(peopleNum);
////            currentReservation.getRestaurantTable().setTableId(tableNum);
////            currentReservation.setDone(isDone);
////
////            // Save updated reservation to the database
////            reservationDao.edit(currentReservation);
////
////            // Show success message
////            showAlert(Alert.AlertType.INFORMATION, "Success", null, "The reservation has been successfully updated.");
////
////            // Close the popup
////            closePopUp(event);
////        } catch (Exception e) {
////            // Handle unexpected errors
////            showAlert(Alert.AlertType.ERROR, "Update Failed", null, "An error occurred while updating the reservation.");
////        }
////    }
//
//    @FXML
//    private void handleEditProcess(ActionEvent event) {
//        // Retrieve updated information
//        String updatedCustomerName = customerNameField.getText();
//        LocalDate updatedReservationDate = reservationDateField.getValue();
//        String updatedReservationTime = reservationTimeField.getText();
//        String updatedPeopleNum = reservationPeopleNumField.getText();
//        String updatedTableNum = reservationTiablleNumField.getText(); // Fixed variable name
//        boolean isDone = doneRadio.isSelected();
//
//        // Validate input
//        if (updatedCustomerName.isEmpty() || updatedReservationDate == null || updatedReservationTime.isEmpty()
//                || updatedPeopleNum.isEmpty() || updatedTableNum.isEmpty()) {
//            showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please fill in all fields before saving.");
//            return;
//        }
//
//        try {
//            // Validate and parse time
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//            java.sql.Time updatedTime;
//            try {
//                LocalTime parsedTime = LocalTime.parse(updatedReservationTime, timeFormatter);
//                updatedTime = java.sql.Time.valueOf(parsedTime.toString() + ":00");
//            } catch (DateTimeParseException e) {
//                showAlert(Alert.AlertType.ERROR, "Invalid Time Format", null, "Please enter a valid time in HH:mm format.");
//                return;
//            }
//
//            // Validate and parse numbers
//            int peopleNum, tableNum;
//            try {
//                peopleNum = Integer.parseInt(updatedPeopleNum);
//                tableNum = Integer.parseInt(updatedTableNum);
//            } catch (NumberFormatException e) {
//                showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please enter valid numbers for people and table fields.");
//                return;
//            }
//
//            // Validate currentReservation object
//            if (currentReservation == null) {
//                showAlert(Alert.AlertType.ERROR, "Error", null, "No reservation selected for updating.");
//                return;
//            }
//
//            // Convert LocalDate to java.sql.Date
//            java.sql.Date sqlDate = java.sql.Date.valueOf(updatedReservationDate);
//
//            // Fetch available tables for the updated date and time
//            reservationDAO reservationDao = DAOFactoryReservation.getCustomerDAO();
//            List<ResturantTable> availableTables = reservationDao.getAvailableTables(sqlDate, updatedTime);
//
//            // Check if the entered table number is available or if it's the same as the current reservation's table number
//            boolean isTableAvailable = currentReservation.getRestaurantTable().getTableId() == tableNum ||
//                    availableTables.stream().anyMatch(table -> table.getTableId() == tableNum);
//
//            if (!isTableAvailable) {
//                // Show alert with available tables
//                String availableTableIds = availableTables.stream()
//                        .map(table -> String.valueOf(table.getTableId()))
//                        .collect(Collectors.joining(", "));
//
//                showAlert(Alert.AlertType.WARNING, "Selected Table Unavailable", null,
//                        "The selected table is not available at the chosen time. Available tables are: " + availableTableIds);
//                return;
//            }
//
//            // Update the Reservation object
//            currentReservation.getCustomer().setCustomerName(updatedCustomerName);
//            currentReservation.setDateReservation(java.util.Date.from(updatedReservationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//            currentReservation.setTimeReservation(updatedTime);
//            currentReservation.setNumberOfPeople(peopleNum);
//            currentReservation.getRestaurantTable().setTableId(tableNum);
//            currentReservation.setDone(isDone);
//
//            // Save updated reservation to the database
//            reservationDao.edit(currentReservation);
//
//            // Show success message
//            showAlert(Alert.AlertType.INFORMATION, "Success", null, "The reservation has been successfully updated.");
//
//            // Refresh the table in the ReservationsManagementController
//            if (parentController != null) {
//                parentController.refreshTableAfterEdit(currentReservation);
//            }
//
//            // Close the popup
//            closePopUp(event);
//        } catch (Exception e) {
//            // Handle unexpected errors
//            showAlert(Alert.AlertType.ERROR, "Update Failed", null, "An error occurred while updating the reservation.");
//        }
//    }
//
//
//    /**
//     * Utility method to show alerts.
//     */
//    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(header);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    public void closePopUp(ActionEvent btn_event) {
//        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
//        stage.close();
//    }
//}

package org.example.rmsproject.ReservationController.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.rmsproject.models.Reservation;
import org.example.rmsproject.models.ResturantTable;
import org.example.rmsproject.models.services.Table.DAOfactoryReservation.DAOFactoryReservation;
import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class EditRsePopUpController {

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField TablePreferenceCol;

    @FXML
    private DatePicker reservationDateField;

    @FXML
    private TextField reservationTimeField;

    @FXML
    private TextField reservationPeopleNumField;

    @FXML
    private TextField reservationTiablleNumField;

    @FXML
    private RadioButton doneRadio;

    @FXML
    private RadioButton notYetRadio;

    @FXML
    private Button EditProcessEditbtn;

    @FXML
    private ToggleGroup statusGroup;

    private Reservation currentReservation; // To hold the current reservation being edited
    private ReservationsManagementController parentController;

    public void initialize() {
        // Set up the ToggleGroup for the radio buttons
        statusGroup = new ToggleGroup();
        doneRadio.setToggleGroup(statusGroup);
        notYetRadio.setToggleGroup(statusGroup);
        // Add event listener to the Edit button
        EditProcessEditbtn.setOnAction(this::handleEditProcess);
    }

    public void refreshData(ReservationsManagementController parentController) {
        this.parentController = parentController;
    }

    public void initializeData(Reservation reservation, ReservationsManagementController parentController) {
        currentReservation = reservation;
        this.parentController = parentController;

        // Populate the fields with reservation data
        customerNameField.setText(reservation.getCustomer().getCustomerName());
        TablePreferenceCol.setText(reservation.getTablePreference());

        // Convert Date to LocalDate and set it to the DatePicker
        if (reservation.getDateReservation() != null) {
            reservationDateField.setValue(reservation.getDateReservation().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Data");
            alert.setHeaderText("Date Missing");
            alert.setContentText("The reservation date is missing. Please check the reservation details.");
            alert.showAndWait();
        }

        // Convert Time to a string and set it to reservationTimeField
        if (reservation.getTimeReservation() != null) {
            reservationTimeField.setText(reservation.getTimeReservation().toString());
        }

        // Set the number of people to the corresponding field
        reservationPeopleNumField.setText(String.valueOf(reservation.getNumberOfPeople()));

        // Set the table number to the corresponding field
        reservationTiablleNumField.setText(String.valueOf(reservation.getRestaurantTable().getTableId()));

        // Set the status radio button
        if (reservation.isDone()) {
            doneRadio.setSelected(true);
        } else {
            notYetRadio.setSelected(true);
        }
    }



    @FXML
    private void handleEditProcess(ActionEvent event) {
        // Retrieve updated information
        String updatedCustomerName = customerNameField.getText();
        LocalDate updatedReservationDate = reservationDateField.getValue();
        String updatedReservationTime = reservationTimeField.getText();
        String updatedPeopleNum = reservationPeopleNumField.getText();
        String updatedTableNum = reservationTiablleNumField.getText();
        boolean isDone = doneRadio.isSelected();

        // Validate input
        if (updatedCustomerName.isEmpty() || updatedReservationDate == null || updatedReservationTime.isEmpty()
                || updatedPeopleNum.isEmpty() || updatedTableNum.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please fill in all fields before saving.");
            return;
        }

        try {
            // Validate and parse time
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            java.sql.Time updatedTime;
            try {
                LocalTime parsedTime = LocalTime.parse(updatedReservationTime, timeFormatter);
                updatedTime = java.sql.Time.valueOf(parsedTime);
            } catch (DateTimeParseException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Time Format", null, "Please enter a valid time in HH:mm format.");
                return;
            }

            // Validate and parse numbers
            int peopleNum, tableNum;
            try {
                peopleNum = Integer.parseInt(updatedPeopleNum);
                tableNum = Integer.parseInt(updatedTableNum);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please enter valid numbers for people and table fields.");
                return;
            }

            // Validate currentReservation object
            if (currentReservation == null) {
                showAlert(Alert.AlertType.ERROR, "Error", null, "No reservation selected for updating.");
                return;
            }

            // Convert LocalDate to java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(updatedReservationDate);

            // Fetch available tables for the updated date and time
            reservationDAO reservationDao = DAOFactoryReservation.getCustomerDAO();
            List<ResturantTable> availableTables = reservationDao.getAvailableTables(sqlDate, updatedTime);

            // Check if the entered table number is available or if it's the same as the current reservation's table number
            boolean isTableAvailable = currentReservation.getRestaurantTable().getTableId() == tableNum ||
                    availableTables.stream().anyMatch(table -> table.getTableId() == tableNum);

            if (!isTableAvailable) {
                // Show alert with available tables
                String availableTableIds = availableTables.stream()
                        .map(table -> String.valueOf(table.getTableId()))
                        .collect(Collectors.joining(", "));

                showAlert(Alert.AlertType.WARNING, "Selected Table Unavailable", null,
                        "The selected table is not available at the chosen time. Available tables are: " + availableTableIds);
                return;
            }

            // Fetch the existing table from the database
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                ResturantTable restaurantTable = session.get(ResturantTable.class, tableNum);

                if (restaurantTable == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "The selected table does not exist.");
                    return;
                }

                // Update the Reservation object
                currentReservation.getCustomer().setCustomerName(updatedCustomerName);
                currentReservation.setDateReservation(java.util.Date.from(updatedReservationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                currentReservation.setTimeReservation(updatedTime);
                currentReservation.setNumberOfPeople(peopleNum);
                currentReservation.setRestaurantTable(restaurantTable);
                currentReservation.setDone(isDone);

                // Save updated reservation to the database
                reservationDao.edit(currentReservation);

                // Refresh the table in the ReservationsManagementController
                if (parentController != null) {
                    parentController.refreshTableAfterEdit(currentReservation);
                }

                // Show success message
                showAlert(Alert.AlertType.INFORMATION, "Success", null, "The reservation has been successfully updated.");

                // Close the popup
                closePopUp(event);
            }
        } catch (Exception e) {
            // Handle unexpected errors
            showAlert(Alert.AlertType.ERROR, "Update Failed", null, "An error occurred while updating the reservation.");
        }
    }



    /**
     * Utility method to show alerts.
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void closePopUp(ActionEvent btn_event) {
        Stage stage = (Stage) ((Button) btn_event.getSource()).getScene().getWindow();
        stage.close();
    }
}
