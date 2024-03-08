package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.stage.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaManagementController {
    static ObservableMap<String, Integer> genderIds = FXCollections.observableHashMap();
    static List<String>  genderNames = new ArrayList<>();

    public static void loadGender(){
        //List<String> genders = GenderSql.GetGenders();
        //genderNames.addAll(genders);
        genderIds.put("Action", 1);
        genderIds.put("Comédie", 2);
        genderIds.put("Drame", 3);
        genderIds.put("Horreur", 4);
        genderIds.put("Science-fiction", 5);
        genderIds.put("Thriller", 6);
    }

    protected void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showDefaultErrorAlert(String detail, SQLException exception) {
        showAlert("Erreur", "Une erreur est survenue" + detail + ".Message d'erreur : " + exception.getMessage(),
                Alert.AlertType.ERROR);
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