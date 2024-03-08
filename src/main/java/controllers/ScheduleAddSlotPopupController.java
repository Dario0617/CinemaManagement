package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScheduleAddSlotPopupController extends CinemaManagementController {

    @FXML
    private Label labelForRoomAndTime;

    @FXML
    private Label labelForMovieDuration;

    @FXML
    private ComboBox<String> movieComboBox;

    private Stage stage;

    public void setScheduleAddSlotPopup(String roomName, int time, Parent root) {
        //Get movies
//        for (Movie movie: movies) {
//            movieComboBox.setValue(movie.getName());
//        }
        movieComboBox.setValue("Dune 2");
        labelForRoomAndTime.setText("Pour la salle '" + roomName +"', à partir de " + (time > 9 ? time : "0" + time) + "h");
        //movie.getDuration();
        int movieDuration = 121;
        int slotBlocked = movieDuration / 60;
        if (movieDuration % 60 != 0) {
            slotBlocked++;
        }
        labelForMovieDuration.setText("Durée du film : " + movieDuration + " minutes, donc " + slotBlocked + " créneau(x) seront bloqué");
        labelForMovieDuration.setWrapText(true);
        double[] screenSize = CinemaManagementController.setScreenSize(0.2, 0.2);
        stage = new Stage();
        stage.setScene(new Scene(root, screenSize[1], screenSize[0]));
        stage.setTitle("Ajouter le film planning");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void saveSlotSchedule() {
        movieComboBox.getValue();
        stage.close();

        //Update BDD

        showAlert("Film ajouté au planning", "Le film \"" + movieComboBox.getValue() + "\" a été ajouté avec succès au planning !",
                Alert.AlertType.INFORMATION);
    }
}