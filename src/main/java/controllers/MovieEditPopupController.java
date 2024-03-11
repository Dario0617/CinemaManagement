package controllers;

import classes.Gender;
import classes.Movie;
import dataBaseSQL.GenderSQL;
import dataBaseSQL.MovieSQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieEditPopupController extends CinemaManagementController {

    @FXML
    private TextField movieNameField;

    @FXML
    private TextField movieDetailsField;

    @FXML
    private ComboBox<String> movieGenderComboBox;

    @FXML
    private DatePicker movieYearDatePicker;

    @FXML
    private TextField movieDurationField;

    private Stage stage;

    private Movie movieToEdit;

    static List<String>  genderNames = new ArrayList<>();

    static ObservableMap<String, Integer> genderIds = FXCollections.observableHashMap();

    public void setMovieEditPopup(Movie movie, Parent root) {
        this.movieToEdit = movie;
        movieNameField.setText(movie.getName());
        movieDetailsField.setText(movie.getDetails());

        genderNames = new ArrayList<>();
        List<Gender> genders = GenderSQL.GetGenders();
        for (Gender gender: genders) {
            genderNames.add(gender.getName());
            genderIds.put(gender.getName(), gender.getId());
        }
        movieGenderComboBox.setItems(FXCollections.observableArrayList(genderNames));
        movieGenderComboBox.setValue(movie.getGender());

        movieYearDatePicker.setValue(movie.getReleaseDate().toLocalDate());
        movieDurationField.setText(Integer.toString(movie.getDuration()));
        double[] screenSize = CinemaManagementController.setScreenSize(0.4, 0.4);
        stage = new Stage();
        stage.setScene(new Scene(root, screenSize[1], screenSize[0]));
        stage.setTitle("Modifier le film");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void updateMovie() {
        movieToEdit.setName(movieNameField.getText());
        movieToEdit.setDetails(movieDetailsField.getText());
        movieToEdit.setGender(movieGenderComboBox.getValue());
        movieToEdit.setReleaseDate(Date.valueOf(movieYearDatePicker.getValue()));
        movieToEdit.setDuration(Integer.parseInt(movieDurationField.getText()));

        stage.close();
        List<Gender> genders = GenderSQL.GetGenders();
        for (Gender gender: genders) {
            genderIds.put(gender.getName(), gender.getId());
        }
        SQLException exception =
                MovieSQL.UpdateMovie(movieToEdit, genderIds.get(movieToEdit.getGender()));
        if (exception != null){
            showDefaultErrorAlert(" lors de la modification du film", exception);
        } else {
            showAlert("Film modifié", "Le film \"" + movieToEdit.getName() + "\" a été modifié avec succès !",
                    Alert.AlertType.INFORMATION);
        }
    }
}