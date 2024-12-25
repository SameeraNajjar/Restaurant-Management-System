module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires java.logging;
    requires java.desktop;

    requires java.naming;
    requires javafx.graphics;

    opens org.example.rmsproject.models to org.hibernate.orm.core;

    opens org.example.rmsproject to javafx.fxml;
    opens org.example.rmsproject.Controllers.ReservationController to javafx.fxml;
    exports org.example.rmsproject.ReservationController;
    exports org.example.rmsproject;
    exports org.example.rmsproject.MenuControllers;
    opens org.example.rmsproject.Controllers.MenuControllers to javafx.fxml;
    exports org.example.rmsproject.RegisterationControllers;
    opens org.example.rmsproject.Controllers.RegisterationControllers to javafx.fxml;
    exports org.example.rmsproject.UserManagement;
    opens org.example.rmsproject.Controllers.UserManagement to javafx.fxml;
}