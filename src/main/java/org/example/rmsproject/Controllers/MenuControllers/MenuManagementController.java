package org.example.rmsproject.Controllers.MenuControllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.example.rmsproject.models.entity.Category;
import org.example.rmsproject.models.entity.MenuItem;
import org.example.rmsproject.models.services.Menu.categoryDAOImp;
import org.example.rmsproject.models.services.Menu.menuItemDAOImp;

import java.io.IOException;

public class MenuManagementController {

    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TableView<MenuItem> menuItemTable;
    @FXML
    private TableColumn<MenuItem, String> menuItemNameColumn;
    @FXML
    private TableColumn<MenuItem, String> descriptionColumn;
    @FXML
    private TableColumn<MenuItem, Double> priceColumn;
    @FXML
    private TableColumn<MenuItem, Boolean> availabilityColumn;

    @FXML
    private TableColumn<Category, Void> categoryActionsColumn;

    private categoryDAOImp categoryDOA = new categoryDAOImp();
    private menuItemDAOImp menuItemDOA = new menuItemDAOImp();

    @FXML
    public void initialize() {
        categoryNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        menuItemNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        availabilityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isAvailable()));

        categoryTable.setEditable(true);
        menuItemTable.setEditable(true);

        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        menuItemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.DoubleStringConverter()));
        availabilityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new javafx.util.converter.BooleanStringConverter()));

        categoryNameColumn.setOnEditCommit(event -> {
            Category category = event.getRowValue();
            category.setName(event.getNewValue());
            categoryDOA.update(category);
        });

        menuItemNameColumn.setOnEditCommit(event -> {
            MenuItem item = event.getRowValue();
            item.setName(event.getNewValue());
            menuItemDOA.update(item);
        });

        descriptionColumn.setOnEditCommit(event -> {
            MenuItem item = event.getRowValue();
            item.setDescription(event.getNewValue());
            menuItemDOA.update(item);
        });

        priceColumn.setOnEditCommit(event -> {
            MenuItem item = event.getRowValue();
            item.setPrice(Double.parseDouble(String.valueOf(event.getNewValue())));
            menuItemDOA.update(item);
        });

        availabilityColumn.setOnEditCommit(event -> {
            MenuItem item = event.getRowValue();
            item.setAvailable(Boolean.parseBoolean(String.valueOf(event.getNewValue())));
            menuItemDOA.update(item);
        });

        loadCategoryData();
        loadMenuItemData();

        categoryActionsColumn.setCellFactory(col -> {
            TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                private final Button btn = new Button("Show Items");

                {
                    btn.setOnAction(event -> handleCategorySelection(getTableView().getItems().get(getIndex())));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        });
    }

    public void loadCategoryData() {
        ObservableList<Category> categories = FXCollections.observableArrayList(categoryDOA.getAllCategories());
        categoryTable.setItems(categories);
    }

    public void loadMenuItemData() {
        ObservableList<MenuItem> menuItem = FXCollections.observableArrayList(menuItemDOA.getAllMenuItems());
        menuItemTable.setItems(menuItem);
    }

    public void handleAddCategory(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/AddCatagory.fxml"));
        Parent root = loader.load();

        AddCategoryController addCategoryController = loader.getController();
        addCategoryController.setMenuManagementController(this);

        loadCategoryData();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleCategorySelection(Category selectedCategory) {
        if (selectedCategory != null) {
            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(menuItemDOA.getMenuItemsByCategory(selectedCategory));
            menuItemTable.setItems(menuItems);
        }
    }

    public void handleAddItem(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/MenuView/AddItem.fxml"));
        Parent root = loader.load();

        AddItemController addItemController = loader.getController();
        addItemController.setMenuManagementController(this);
        addItemController.loadCategorieData();

        loadMenuItemData();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleDeleteItem(ActionEvent event) {
        MenuItem selectedItem = menuItemTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to delete this menu item?");
            alert.setContentText("This action cannot be undone.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    menuItemDOA.delete(selectedItem);
                    menuItemTable.getItems().remove(selectedItem);
                }
            });
        }
    }

    @FXML
    public void handleDeleteCategory(ActionEvent event) {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to delete this category?");
            alert.setContentText("This action will also delete all menu items associated with this category. This action cannot be undone.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ObservableList<MenuItem> relatedMenuItems = FXCollections.observableArrayList(menuItemDOA.getMenuItemsByCategory(selectedCategory));
                    for (MenuItem menuItem : relatedMenuItems) {
                        menuItemDOA.delete(menuItem);
                    }

                    categoryDOA.delete(selectedCategory);
                    categoryTable.getItems().remove(selectedCategory);

                    loadMenuItemData();
                }
            });
        }
    }


    public void handleBackToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/rmsproject/HomePage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
