package com.example.cinemamanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CinemaManagementApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CinemaManagement.fxml"));
        primaryStage.setTitle("Gestion de cinéma");

        // Obtenir les dimensions de l'écran principal
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Calculer la taille de la fenêtre en laissant un espace en haut pour la barre de titre
        double windowHeight = screenHeight * 0.9;

        // Définir la taille de la fenêtre
        primaryStage.setScene(new Scene(root, screenWidth, windowHeight));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}