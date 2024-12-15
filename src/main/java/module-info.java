module org.example.rmsproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;

    opens org.example.rmsproject to javafx.fxml;
    exports org.example.rmsproject;
}