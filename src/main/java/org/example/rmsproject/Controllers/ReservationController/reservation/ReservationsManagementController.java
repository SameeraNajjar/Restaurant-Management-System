package org.example.rmsproject.Controllers.ReservationController.reservation;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.rmsproject.models.entity.Reservation;
import org.example.rmsproject.models.interfaces.Table.reservationDAO;
import org.example.rmsproject.models.services.Table.DAOfactoryReservation.DAOFactoryReservation;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationsManagementController {

    @FXML
    private TableColumn<Reservation, String> TablePreferenceCol;
    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Reservation, String> actionCol;

    @FXML
    private TableColumn<Reservation, String> customerNameCol;

    @FXML
    private TableColumn<Reservation, Date> dateOfBookingCol;

    @FXML
    private TableColumn<Reservation, Integer> numberOfPeopoleCol;
    @FXML
    private TableColumn<Reservation, String> statusCol;

    @FXML
    private TableColumn<Reservation, Integer> tableNumberCol;

    @FXML
    private TableView<Reservation> tableReservation;

    @FXML
    private TableColumn<Reservation, Time> timeOfBookingCol;

    private ObservableList<Reservation> originalReservations = FXCollections.observableArrayList();

    public void initialize() {

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String keySearch = newValue.trim().toLowerCase();
            ObservableList<Reservation> filteredReservations = FXCollections.observableArrayList();

            try {

                for (Reservation reservation : originalReservations) {

                    if (reservation.getCustomer().getCustomerName().toLowerCase().contains(keySearch)) {
                        filteredReservations.add(reservation);
                    }

                    else if (String.valueOf(reservation.getRestaurantTable().getTableId()).toLowerCase().contains(keySearch)) {
                        filteredReservations.add(reservation);
                    }
                }


                if (filteredReservations.isEmpty() && !keySearch.isEmpty()) {
                    tableReservation.setItems(FXCollections.observableArrayList());
                } else if (keySearch.isEmpty()) {
                    tableReservation.setItems(originalReservations);
                } else {
                    tableReservation.setItems(filteredReservations);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            tableReservation.refresh();
        });

        tableReservation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setCellValueFactory();
        loadData();
    }

    public void loadData() {
        try {
            reservationDAO reservationDOA = DAOFactoryReservation.getCustomerDAO();
            List<Reservation> listOfReservation = reservationDOA.getAll();
            if (listOfReservation != null) {
                originalReservations = FXCollections.observableArrayList(
                        listOfReservation.stream()
                                .filter(reservation -> reservation != null)
                                .collect(Collectors.toList())
                );
            } else {
                originalReservations.clear();
            }
            tableReservation.setItems(originalReservations);
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Failed to load reservation data.");
        }
        tableReservation.refresh();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setCellValueFactory() {
        TablePreferenceCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTablePreference()));
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        dateOfBookingCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateReservation()));
        numberOfPeopoleCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumberOfPeople()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReservationStatus()));
        tableNumberCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRestaurantTable().getTableId()));
        timeOfBookingCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTimeReservation()));
        dateOfBookingCol.setCellFactory(column -> new TableCell<Reservation, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    setText(sdf.format(item));
                }
            }
        });
        actionCol.setCellFactory(new Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>>() {
            @Override
            public TableCell<Reservation, String> call(TableColumn<Reservation, String> param) {
                return new TableCell<Reservation, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(5);
                            hbox.setAlignment(Pos.CENTER);
                            Button EditButton = new Button();
                            ImageView EditButtonIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/Edit.png")));
                            EditButtonIcon.setFitWidth(15);
                            EditButtonIcon.setFitHeight(15);
                            EditButton.setGraphic(EditButtonIcon);
                            EditButton.setPrefSize(30, 30);
                            EditButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                            Button cancelButton = new Button();
                            ImageView cancelButtonIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/delete.png")));
                            cancelButtonIcon.setFitWidth(15);
                            cancelButtonIcon.setFitHeight(15);
                            cancelButton.setGraphic(cancelButtonIcon);
                            cancelButton.setPrefSize(30, 30);
                            cancelButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                            EditButton.setOnAction(e -> openEditRes(getTableRow().getItem()));
                            cancelButton.setOnAction(e -> openDeleteRes(getTableRow().getItem()));
                            hbox.getChildren().addAll(EditButton, cancelButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    public void openEditRes(Reservation reservation) {
        if (reservation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/EditRsePop_Up.fxml"));
                Parent root = loader.load();
                EditRsePopUpController controller = loader.getController();
                controller.initializeData(reservation,this);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshTableAfterEdit(Reservation updatedReservation) {
        int index = originalReservations.indexOf(updatedReservation);
        if (index != -1) {
            originalReservations.set(index, updatedReservation);
        }
        tableReservation.refresh();
    }

    public void removeReservationFromTable(Reservation reservation) {
        originalReservations.remove(reservation);
        tableReservation.refresh();
    }

    public void openDeleteRes(Reservation reservation) {
        if (reservation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/Table/DeleteRsePop_Up.fxml"));
                Parent root = loader.load();
                DeleteRsePopUpController controller = loader.getController();
                controller.initializeData(reservation, this);
                Stage stage = new Stage(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
