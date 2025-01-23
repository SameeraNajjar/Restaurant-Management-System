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



    opens org.example.rmsproject.models to org.hibernate.orm.core;

    opens org.example.rmsproject to javafx.fxml;

    exports org.example.rmsproject;
    exports org.example.rmsproject.MenuControllers;
    opens org.example.rmsproject.MenuControllers to javafx.fxml;

}