module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.logging;
    requires java.desktop;

    requires java.naming;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires jdk.jfr;
    requires java.mail;
    requires java.activation;

    // Allow Hibernate and JavaFX to reflectively access the models package
    opens org.example.rmsproject.models to org.hibernate.orm.core, javafx.base;

    // JavaFX FXML reflections for other packages
    opens org.example.rmsproject to javafx.fxml;
    opens org.example.rmsproject.Controllers.ReservationController to javafx.fxml;
    opens org.example.rmsproject.Controllers.RegisterationControllers to javafx.fxml;
    opens org.example.rmsproject.Controllers.UserManagement to javafx.fxml;
    opens org.example.rmsproject.Controllers.OrderController to javafx.fxml;
    opens org.example.rmsproject.Controllers.MenuControllers to javafx.fxml;

    // Exporting packages
    exports org.example.rmsproject;
    exports org.example.rmsproject.Controllers.ReservationController;
    exports org.example.rmsproject.Controllers.RegisterationControllers;
    exports org.example.rmsproject.Controllers.UserManagement;
    exports org.example.rmsproject.Controllers.OrderController;
    exports org.example.rmsproject.Controllers.MenuControllers;
}
