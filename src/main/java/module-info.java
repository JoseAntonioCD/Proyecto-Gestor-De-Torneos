module org.example.proyectogestordetorneos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.proyectogestordetorneos to javafx.fxml;
    exports org.example.proyectogestordetorneos;
}