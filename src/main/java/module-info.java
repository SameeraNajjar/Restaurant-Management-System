module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires java.logging;
    requires java.desktop;
    requires javafx.base;
    requires java.naming;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires mysql.connector.java;

    opens org.example.rmsproject.models to org.hibernate.orm.core, javafx.base;
    opens org.example.rmsproject to javafx.fxml;
    opens org.example.rmsproject.ReservationController to javafx.fxml;
    exports org.example.rmsproject.ReservationController;
    exports org.example.rmsproject;
    exports org.example.rmsproject.MenuControllers;
    opens org.example.rmsproject.MenuControllers to javafx.fxml;
    exports org.example.rmsproject.RegisterationControllers;
    opens org.example.rmsproject.RegisterationControllers to javafx.fxml;
    exports org.example.rmsproject.UserManagement;
    opens org.example.rmsproject.UserManagement to javafx.fxml;
    exports org.example.rmsproject.OrderController to javafx.fxml;
    opens org.example.rmsproject.OrderController to javafx.fxml;
    exports org.example.rmsproject.ReservationController.customer;
    opens org.example.rmsproject.ReservationController.customer to javafx.fxml;
    exports org.example.rmsproject.ReservationController.reservation;
    opens org.example.rmsproject.ReservationController.reservation to javafx.fxml;
}