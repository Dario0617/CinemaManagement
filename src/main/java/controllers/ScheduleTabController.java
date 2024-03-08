package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleTabController {
    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private Label dateTimeNowLabel;

    private final int numberOfRooms = 2;

    @FXML
    private void initialize() {
        dateTimeNowLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dateTimeNowLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15");

        for (int i = 0; i < numberOfRooms; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            scheduleGridPane.getColumnConstraints().add(column);

            for (int o = 0; o < 25; o++){
                if (o == 0){
                    Label roomLabel = new Label("Salle " + (i+1));
                    roomLabel.setStyle("-fx-font-weight: bold");
                    VBox vBoxRoom = new VBox(roomLabel);
                    vBoxRoom.setAlignment(Pos.CENTER);
                    vBoxRoom.setStyle("-fx-background-color: darkgray;");
                    scheduleGridPane.getChildren().add(o,vBoxRoom);
                    GridPane.setRowIndex(vBoxRoom, o);
                    GridPane.setColumnIndex(vBoxRoom, i);
                }
                Label label = new Label();
                VBox vBox = new VBox(label);
                vBox.setAlignment(Pos.CENTER);
                scheduleGridPane.getChildren().add(o,vBox);
                GridPane.setRowIndex(vBox, o);
                GridPane.setColumnIndex(vBox, i);
                vBox.setOnMouseClicked(mouseEvent -> {
                    var vBoxColumn = GridPane.getColumnIndex((Node) mouseEvent.getTarget());
                    var vBoxRow = GridPane.getRowIndex((Node) mouseEvent.getTarget());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleAddSlotPopup.fxml"));
                        Parent root = loader.load();
                        ScheduleAddSlotPopupController controller = loader.getController();
                        controller.setScheduleAddSlotPopup("Salle " + vBoxColumn, vBoxRow-1 , root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                if (o == 3 && i == 1){
                    vBox.setStyle("-fx-background-color: lightgray;");
                }
            }
        }
    }
}