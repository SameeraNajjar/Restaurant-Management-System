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
    requires java.management;

    // Combine both accesses into a single opens statement
    opens org.example.rmsproject.models to javafx.base, org.hibernate.orm.core;

    // Export and open the necessary controllers and packages
    opens org.example.rmsproject to javafx.fxml;
    opens org.example.rmsproject.Controllers.ReservationController to javafx.fxml;
    exports org.example.rmsproject.Controllers.ReservationController;
    exports org.example.rmsproject;

    exports org.example.rmsproject.Controllers.RegisterationControllers;
    opens org.example.rmsproject.Controllers.RegisterationControllers to javafx.fxml;

    exports org.example.rmsproject.Controllers.UserManagement;
    opens org.example.rmsproject.Controllers.UserManagement to javafx.fxml;

    exports org.example.rmsproject.Controllers.OrderController to javafx.fxml;
    opens org.example.rmsproject.Controllers.OrderController to javafx.fxml;

    exports org.example.rmsproject.Controllers.MenuControllers;
    opens org.example.rmsproject.Controllers.MenuControllers to javafx.fxml;
}
