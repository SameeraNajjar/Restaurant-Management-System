module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.rmsproject to javafx.fxml;
    exports org.example.rmsproject;
    exports org.example.rmsproject.MenuControllers;
    opens org.example.rmsproject.MenuControllers to javafx.fxml;
}