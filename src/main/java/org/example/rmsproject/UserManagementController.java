package org.example.rmsproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class UserManagementController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox userListContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    private Region userImageRegion;

    @FXML
    private Label yearsOfWorkLabel;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private Label workingHoursLabel;

    @FXML
    private Region logoRegion;

    @FXML
    public void initialize() {

        String logoPath = getClass().getResource("/data/images/logo.png").toExternalForm();
        logoRegion.setStyle(
                "-fx-background-image: url('" + logoPath + "');" +
                        "-fx-background-size: 150% 150%;" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;"
        );


        addUserToList("Marcus Horizon", "4.7", "/data/images/person1.png");
        addUserToList("Maria Elena", "4.9", "/data/images/person2.png");
        addUserToList("Stefi Jessi", "3.5", "/data/images/person3.png");
        addUserToList("Gerty Cori", "2.5", "/data/images/person4.png");
        addUserToList(" Diandra ", "4.0", "/data/images/person5.png");
    }

    private void addUserToList(String name, String rating, String imagePath) {
        HBox userCard = new HBox();
        userCard.setSpacing(10);
        userCard.setStyle("-fx-background-color: #630C2F; -fx-border-radius: 5; -fx-padding: 10;");

        Region userImage = new Region();
        userImage.setPrefSize(50, 50);
        userImage.setStyle(
                "-fx-background-image: url('" + getClass().getResource(imagePath).toString() + "');" +
                        "-fx-background-size: cover;" +
                        "-fx-border-radius: 25;" +
                        "-fx-background-radius: 25;"
        );

        VBox userDetails = new VBox();
        userDetails.setSpacing(5);

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-text-fill: #B89555;");
        Label ratingLabel = new Label("Rating: " + rating);
        ratingLabel.setStyle("-fx-text-fill: #B89555;");

        userDetails.getChildren().addAll(nameLabel, ratingLabel);
        userCard.getChildren().addAll(userImage, userDetails);


        userCard.setOnMouseClicked(event -> loadUserDetails(name, rating, imagePath));

        userListContainer.getChildren().add(userCard);
    }

    private void loadUserDetails(String name, String rating, String imagePath) {

        usernameLabel.setText(name);
        yearsOfWorkLabel.setText("Years of Work: " + (int) (Double.parseDouble(rating) * 2));
        salaryLabel.setText("Salary: " + "$" + (int) (Double.parseDouble(rating) * 1000));
        ageLabel.setText("Age: " + "30");
        positionLabel.setText("Position: garson");
        workingHoursLabel.setText("Working Hours: " + "40h/week");


        userImageRegion.setStyle(
                "-fx-background-image: url('" + getClass().getResource(imagePath).toString() + "');" +
                        "-fx-background-size: cover;" +
                        "-fx-border-radius: 75;" +
                        "-fx-background-radius: 75;"
        );

        System.out.println("Details loaded for " + name);
    }
}
