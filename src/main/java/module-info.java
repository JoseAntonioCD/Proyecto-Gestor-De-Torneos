module org.example.proyectogestordetorneos {
    requires javafx.controls;
    requires javafx.fxml;
    requires  javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.xml.bind;

    exports app;
    exports controller;
    exports model;

    opens controller to javafx.fxml;
    opens org.example.proyectogestordetorneos to javafx.fxml;
    exports org.example.proyectogestordetorneos;
}