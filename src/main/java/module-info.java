module com.example.cinemamanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.cinemamanagement to javafx.fxml;
    exports com.example.cinemamanagement;
}