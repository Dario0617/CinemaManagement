module com.example.cinemamanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.cinemamanagement to javafx.fxml;
    exports com.example.cinemamanagement;
    exports classes;
    opens classes to javafx.fxml;
    exports controllers;
    opens controllers to javafx.fxml;
}