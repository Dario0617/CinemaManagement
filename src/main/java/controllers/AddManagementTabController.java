package controllers;

import classes.Gender;
import classes.Movie;
import classes.Price;
import classes.Room;
import dataBaseSQL.GenderSQL;
import dataBaseSQL.MovieSQL;
import dataBaseSQL.PriceSQL;
import dataBaseSQL.RoomSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
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
    private TextField priceCostField;

    @FXML
    private TextField priceNameField;

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

    static ObservableMap<String, Integer> genderIds = FXCollections.observableHashMap();

    static List<String>  genderNames = new ArrayList<>();

    @FXML
    private void initialize() {
        genderComboBox.setOnMouseClicked(mouseEvent -> {
            genderNames = new ArrayList<>();
            List<Gender> genders = GenderSQL.GetGenders();
            for (Gender gender: genders) {
                genderNames.add(gender.getName());
                genderIds.put(gender.getName(), gender.getId());
            }
            genderComboBox.setItems(FXCollections.observableArrayList(genderNames));
        });
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

        List<Movie> movies = MovieSQL.GetMovies();
        MovieAndRoomListTabController.originalMovieList.clear();
        MovieAndRoomListTabController.originalMovieList.addAll(movies);

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

        SQLException exception = GenderSQL.AddGender(name);
        if (exception == null){
            showAlert("Genre ajouté", "Le genre \"" + name + "\" a été ajouté avec succès !",
                    Alert.AlertType.INFORMATION);
        } else{
            showDefaultErrorAlert(" lors de la création du genre", exception);
        }
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

        List<Room> rooms = RoomSQL.GetRooms();
        MovieAndRoomListTabController.originalRoomList.clear();
        MovieAndRoomListTabController.originalRoomList.addAll(rooms);

        roomNameField.clear();
        capacityField.clear();
    }

    @FXML
    public void addPricing(ActionEvent actionEvent) {
        String name = priceNameField.getText();
        if (name.isBlank()){
            showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
            return;
        }
        int cost;
        try {
            cost = Integer.parseInt(priceCostField.getText());
        } catch (NumberFormatException e){
            showAlert("Erreur", "Le prix est obligatoire, il faut qu'il soit au format numérique",
                    Alert.AlertType.ERROR);
            return;
        }

        SQLException exception = PriceSQL.AddPrice(new Price(0, name, cost));
        if (exception == null){
            showAlert("Tarif ajouté", "Le tarif a été ajoutée avec succès !",
                    Alert.AlertType.INFORMATION);
        } else{
            showDefaultErrorAlert(" lors de la création du tarif", exception);
        }
    }
}
