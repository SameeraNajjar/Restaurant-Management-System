module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    requires java.logging;
    requires java.desktop;


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
}