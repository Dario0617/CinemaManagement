package com.example.cinemamanagement;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoomEditPopupController extends CinemaManagementController {

    @FXML
    private TextField roomNameField;

    @FXML
    private TextField capacityField;

    private Stage stage;

    private Room roomToEdit;

    public void setRoomEditPopup(Room room, Parent root) {
        this.roomToEdit = room;
        roomNameField.setText(room.getName());
        capacityField.setText(Integer.toString(room.getCapacity()));
        double[] screenSize = CinemaManagementController.setScreenSize(0.2, 0.2);
        stage = new Stage();
        stage.setScene(new Scene(root, screenSize[1], screenSize[0]));
        stage.setTitle("Modifier la salle");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void saveRoom() {
        roomToEdit.setName(roomNameField.getText());
        roomToEdit.setCapacity(Integer.parseInt(capacityField.getText()));
        stage.close();

        //Update BDD

        showAlert("Salle modifié", "La salle \"" + roomToEdit.getName() + "\" a été modifié avec succès !",
                Alert.AlertType.INFORMATION);
    }
}