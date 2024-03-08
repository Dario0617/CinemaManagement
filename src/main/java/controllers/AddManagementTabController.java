package controllers;

import classes.Room;
import dataBaseSQL.MovieSQL;
import dataBaseSQL.RoomSQL;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddManagementTabController extends CinemaManagementController {

    @FXML
    private TextField filmNameField;

    @FXML
    private DatePicker movieReleaseDatePicker;

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
        genderNames.add("Action");
        genderComboBox.setItems(FXCollections.observableArrayList(CinemaManagementController.genderNames));
    }

    @FXML
    private void addFilm() {
        String name = filmNameField.getText();
        if (name.isBlank()){
            showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
            return;
        }

        LocalDate date = movieReleaseDatePicker.getValue();
        if (date == null){
            showAlert("Erreur", "La date est obligatoire", Alert.AlertType.ERROR);
            return;
        }
        Date releaseDate = Date.valueOf(movieReleaseDatePicker.getValue());

        int genderId;
        try {
            genderId = genderIds.get(genderComboBox.getValue());
        } catch (NullPointerException e){
            showAlert("Erreur", "Le genre est obligatoire", Alert.AlertType.ERROR);
            return;
        }

        String details = detailsField.getText();

        int duration;
        try {
            duration = Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e){
            showAlert("Erreur", "La durée est obligatoire, il faut qu'elle soit au format numérique",
                    Alert.AlertType.ERROR);
            return;
        }

        SQLException exception = MovieSQL.AddMovie(name, details, releaseDate, duration, genderId);
        if (exception == null){
            showAlert("Film ajouté", "Le film \"" + name + "\" a été ajouté avec succès !",
                    Alert.AlertType.INFORMATION);
        } else{
            showDefaultErrorAlert(" lors de la création du film", exception);
        }

        filmNameField.clear();
        movieReleaseDatePicker.setValue(null);
        genderComboBox.getSelectionModel().clearSelection();
        detailsField.clear();
        durationField.clear();
    }

    @FXML
    private void addGender() {
        String name = genderNameField.getText();
        if (name.isBlank()){
            showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
            return;
        }

//        SQLException exception = GenderSQL.AddGender(name);
//        if (exception == null){
//            showAlert("Genre ajouté", "Le genre \"" + name + "\" a été ajouté avec succès !",
//                    Alert.AlertType.INFORMATION);
//        } else{
//            showDefaultErrorAlert(" lors de la création du genre", exception);
//        }
        genderNameField.clear();
    }

    @FXML
    private void addRoom() {
        String name = roomNameField.getText();
        if (name.isBlank()){
            showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
            return;
        }
        int capacity;
        try {
            capacity = Integer.parseInt(capacityField.getText());
        } catch (NumberFormatException e){
            showAlert("Erreur", "La capacité est obligatoire, il faut qu'elle soit au format numérique",
                    Alert.AlertType.ERROR);
            return;
        }

        SQLException exception = RoomSQL.AddRoom(new Room(name, capacity));
        if (exception == null){
            showAlert("Salle ajoutée", "La salle \"" + name + "\" a été ajoutée avec succès !",
                    Alert.AlertType.INFORMATION);
        } else{
            showDefaultErrorAlert(" lors de la création de la salle", exception);
        }

        roomNameField.clear();
        capacityField.clear();
    }
}
