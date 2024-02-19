package com.example.cinemamanagement;

import javafx.scene.control.Alert;
import javafx.stage.Screen;

public class CinemaManagementController {

    protected void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static double[] setScreenSize(double screenHeightPercentage, double screenWidthPercentage) {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        double windowHeight = screenHeight * screenHeightPercentage;
        double windowWidth = screenWidth * screenWidthPercentage;
        double[] screenSize = new double[2];
        screenSize[0] = windowHeight;
        screenSize[1] = windowWidth;
        return screenSize;
    }
}