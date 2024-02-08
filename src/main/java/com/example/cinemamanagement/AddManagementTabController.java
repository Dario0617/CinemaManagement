package com.example.cinemamanagement;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddManagementTabController extends CinemaManagementController {

    @FXML
    private TextField FilmNameField;

    @FXML
    private TextField YearField;

    @FXML
    private ComboBox<String> GenderComboBox;

    @FXML
    private TextField DetailsField;

    @FXML
    private TextField DurationField;

    @FXML
    private TextField GenderNameField;

    @FXML
    private TextField RoomNameField;

    @FXML
    private TextField CapacityField;

    @FXML
    private void initialize() {
        // Chargez les genres à partir de la base de données ou d'une liste prédéfinie
        GenderComboBox.getItems().addAll("Action", "Comédie", "Drame", "Horreur", "Science-fiction", "Thriller");
    }

    @FXML
    private void AddFilm() {
        String filmName = FilmNameField.getText();
        String year = YearField.getText();
        String gender = GenderComboBox.getValue();
        String details = DetailsField.getText();
        String duration = DurationField.getText();

        // Insérer la logique pour ajouter le film à la base de données ici
        // Vous pouvez utiliser JDBC, Hibernate ou tout autre framework d'accès aux données

        ShowAlert("Film ajouté", "Le film \"" + filmName + "\" a été ajouté avec succès !");
        // Réinitialiser les champs après l'ajout
        ClearFields();
    }

    private void ClearFields() {
        FilmNameField.clear();
        YearField.clear();
        GenderComboBox.getSelectionModel().clearSelection();
        DetailsField.clear();
        DurationField.clear();
    }

    @FXML
    private void AddGender() {
        String genderName = GenderNameField.getText();
        // Insérer la logique pour ajouter le genre à la base de données
        ShowAlert("Genre ajouté", "Le genre \"" + genderName + "\" a été ajouté avec succès !");
        // Réinitialiser les champs après l'ajout
        GenderNameField.clear();
    }

    @FXML
    private void AddRoom() {
        String roomName = RoomNameField.getText();
        int capacity = Integer.parseInt(CapacityField.getText());
        // Insérer la logique pour ajouter la salle à la base de données
        ShowAlert("Salle ajoutée", "La salle \"" + roomName + "\" a été ajoutée avec succès !");
        // Réinitialiser les champs après l'ajout
        RoomNameField.clear();
        CapacityField.clear();
    }
}
