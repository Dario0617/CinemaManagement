package com.example.cinemamanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddManagementTabController extends CinemaManagementController {

    @FXML
    private TextField filmNameField;

    @FXML
    private TextField yearField;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField detailsField;

    @FXML
    private TextField durationField;

    @FXML
    private TextField genderNameField;

    @FXML
    private TextField roomNameField;

    @FXML
    private TextField capacityField;

    @FXML
    private void initialize() {
        // Chargez les genres à partir de la base de données ou d'une liste prédéfinie
        genderComboBox.getItems().addAll("Action", "Comédie", "Drame", "Horreur", "Science-fiction", "Thriller");
    }

    @FXML
    private void addFilm() {
        String filmName = filmNameField.getText();
        String year = yearField.getText();
        String gender = genderComboBox.getValue();
        String details = detailsField.getText();
        String duration = durationField.getText();

        // Insérer la logique pour ajouter le film à la base de données ici
        // Vous pouvez utiliser JDBC, Hibernate ou tout autre framework d'accès aux données

        showAlert("Film ajouté", "Le film \"" + filmName + "\" a été ajouté avec succès !",
                Alert.AlertType.INFORMATION);
        filmNameField.clear();
        yearField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        detailsField.clear();
        durationField.clear();
    }

    @FXML
    private void addGender() {
        String genderName = genderNameField.getText();
        // Insérer la logique pour ajouter le genre à la base de données
        showAlert("Genre ajouté", "Le genre \"" + genderName + "\" a été ajouté avec succès !",
                Alert.AlertType.INFORMATION);
        genderNameField.clear();
    }

    @FXML
    private void addRoom() {
        String roomName = roomNameField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        // Insérer la logique pour ajouter la salle à la base de données
        showAlert("Salle ajoutée", "La salle \"" + roomName + "\" a été ajoutée avec succès !",
                Alert.AlertType.INFORMATION);
        roomNameField.clear();
        capacityField.clear();
    }
}
