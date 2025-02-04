package org.example.rmsproject.Controllers.ReservationController.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.rmsproject.models.entity.Reservation;
import org.example.rmsproject.models.entity.ResturantTable;
import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.models.services.Table.DAOfactoryReservation.DAOFactoryReservation;
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

        private Reservation currentReservation;
        private ReservationsManagementController parentController;

        public void initialize() {

            statusGroup = new ToggleGroup();
            doneRadio.setToggleGroup(statusGroup);
            notYetRadio.setToggleGroup(statusGroup);

            EditProcessEditbtn.setOnAction(this::handleEditProcess);
        }

        public void refreshData(ReservationsManagementController parentController) {
            this.parentController = parentController;
        }

        public void initializeData(Reservation reservation, ReservationsManagementController parentController) {
            currentReservation = reservation;
            this.parentController = parentController;


            customerNameField.setText(reservation.getCustomer().getCustomerName());
            TablePreferenceCol.setText(reservation.getTablePreference());


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


            if (reservation.getTimeReservation() != null) {
                reservationTimeField.setText(reservation.getTimeReservation().toString());
            }

            reservationPeopleNumField.setText(String.valueOf(reservation.getNumberOfPeople()));

            reservationTiablleNumField.setText(String.valueOf(reservation.getRestaurantTable().getTableId()));

            if (reservation.isDone()) {
                doneRadio.setSelected(true);
            } else {
                notYetRadio.setSelected(true);
            }
        }


        @FXML
        private void handleEditProcess(ActionEvent event) {

            String updatedCustomerName = customerNameField.getText();
            LocalDate updatedReservationDate = reservationDateField.getValue();
            String updatedReservationTime = reservationTimeField.getText();
            String updatedPeopleNum = reservationPeopleNumField.getText();
            String updatedTableNum = reservationTiablleNumField.getText();
            boolean isDone = doneRadio.isSelected();

            if (updatedCustomerName.isEmpty() || updatedReservationDate == null || updatedReservationTime.isEmpty()
                    || updatedPeopleNum.isEmpty() || updatedTableNum.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please fill in all fields before saving.");
                return;
            }

            try {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                java.sql.Time updatedTime;
                try {
                    LocalTime parsedTime = LocalTime.parse(updatedReservationTime, timeFormatter);
                    updatedTime = java.sql.Time.valueOf(parsedTime);
                } catch (DateTimeParseException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Time Format", null, "Please enter a valid time in HH:mm format.");
                    return;
                }
                int peopleNum, tableNum;
                try {
                    peopleNum = Integer.parseInt(updatedPeopleNum);
                    tableNum = Integer.parseInt(updatedTableNum);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Please enter valid numbers for people and table fields.");
                    return;
                }
                if (currentReservation == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "No reservation selected for updating.");
                    return;
                }

                java.sql.Date sqlDate = java.sql.Date.valueOf(updatedReservationDate);

                reservationDAO reservationDao = DAOFactoryReservation.getCustomerDAO();

                // Check for double booking
                List<Reservation> existingReservations = reservationDao.getReservationsByTableAndTime(tableNum, sqlDate, updatedTime);
                if (!existingReservations.isEmpty() && existingReservations.stream().anyMatch(res -> res.getReservationId() != currentReservation.getReservationId())) {
                    showAlert(Alert.AlertType.WARNING, "Double Booking", null, "The selected table is already booked at the chosen time.");
                    return;
                }

                List<ResturantTable> availableTables = reservationDao.getAvailableTables(sqlDate, updatedTime);

                boolean isTableAvailable = currentReservation.getRestaurantTable().getTableId() == tableNum ||
                        availableTables.stream().anyMatch(table -> table.getTableId() == tableNum);

                if (!isTableAvailable) {
                    String availableTableIds = availableTables.stream()
                            .map(table -> String.valueOf(table.getTableId()))
                            .collect(Collectors.joining(", "));

                    showAlert(Alert.AlertType.WARNING, "Selected Table Unavailable", null,
                            "The selected table is not available at the chosen time. Available tables are: " + availableTableIds);
                    return;
                }

                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    ResturantTable restaurantTable = session.get(ResturantTable.class, tableNum);

                    if (restaurantTable == null) {
                        showAlert(Alert.AlertType.ERROR, "Error", null, "The selected table does not exist.");
                        return;
                    }

                    currentReservation.getCustomer().setCustomerName(updatedCustomerName);
                    currentReservation.setDateReservation(java.util.Date.from(updatedReservationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    currentReservation.setTimeReservation(updatedTime);
                    currentReservation.setNumberOfPeople(peopleNum);
                    currentReservation.setRestaurantTable(restaurantTable);
                    currentReservation.setDone(isDone);

                    reservationDao.edit(currentReservation);

                    if (parentController != null) {
                        parentController.refreshTableAfterEdit(currentReservation);
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Success", null, "The reservation has been successfully updated.");

                    closePopUp(event);
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Update Failed", null, "An error occurred while updating the reservation.");
            }
        }

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