package com.example.cinemamanagement;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public void setMovieEditPopup(Movie movie, Parent root) {
        this.movieToEdit = movie;
        movieNameField.setText(movie.getName());
        movieDetailsField.setText(movie.getDetails());
        movieGenderComboBox.setValue(movie.getGender());
        movieYearDatePicker.setValue(movie.getReleaseDate());
        movieDurationField.setText(Integer.toString(movie.getDuration()));
        double[] screenSize = CinemaManagementController.setScreenSize(0.4, 0.4);
        stage = new Stage();
        stage.setScene(new Scene(root, screenSize[1], screenSize[0]));
        stage.setTitle("Modifier le film");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void saveMovie() {
        movieToEdit.setName(movieNameField.getText());
        movieToEdit.setDetails(movieDetailsField.getText());
        movieToEdit.setGender(movieGenderComboBox.getValue());
        movieToEdit.setReleaseDate(movieYearDatePicker.getValue());
        movieToEdit.setDuration(Integer.parseInt(movieDurationField.getText()));

        stage.close();

        //Update BDD

        showAlert("Film modifié", "Le film \"" + movieToEdit.getName() + "\" a été modifié avec succès !",
                Alert.AlertType.INFORMATION);
    }
}