package controllers;

import classes.Room;
import classes.Slot;
import dataBaseSQL.RoomSQL;
import dataBaseSQL.SlotSQL;
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
import java.util.List;

public class ScheduleTabController extends CinemaManagementController {
    @FXML
    private GridPane scheduleGridPane;

    @FXML
    private Label dateTimeNowLabel;

    private List<Room> rooms;

    @FXML
    private void initialize() {
        dateTimeNowLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dateTimeNowLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15");

        rooms = RoomSQL.GetRooms();

        for (int i = 0; i < rooms.size(); i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            scheduleGridPane.getColumnConstraints().add(column);

            for (int o = 0; o < 25; o++){
                if (o == 0){
                    Label roomLabel = new Label(rooms.get(i).getName());
                    roomLabel.setStyle("-fx-font-weight: bold");
                    VBox vBoxRoom = createVbox(roomLabel, "darkgray");
                    setVboxInGridPane(vBoxRoom, o, i);
                }
                VBox vBox = createVbox(new Label(), "");
                setVboxInGridPane(vBox, o, i);
                vBox.setOnMouseClicked(mouseEvent -> {
                    var vBoxColumn = GridPane.getColumnIndex((Node) mouseEvent.getTarget());
                    var vBoxRow = GridPane.getRowIndex((Node) mouseEvent.getTarget());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cinemamanagement/ScheduleAddSlotPopup.fxml"));
                        Parent root = loader.load();
                        ScheduleAddSlotPopupController controller = loader.getController();
                        controller.setScheduleAddSlotPopup(rooms.get(vBoxColumn), vBoxRow-1 , root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            List<Slot> todaySlots = SlotSQL.GetTodaySlots();
            for (Slot slot: todaySlots) {
                int rowIndex = (slot.getStartHour()+1);
                int slotUsed = ScheduleAddSlotPopupController.getSlotUsed(slot.getMovie().getDuration());
                int maxHour = slot.getStartHour() + (slotUsed);
                String textLabel = slot.getMovie().getName() + " de " +
                        (slot.getStartHour() > 9 ? slot.getStartHour() : "0" + slot.getStartHour()) +
                        "h à " + (maxHour > 9 ? maxHour : "0" + maxHour) + "h";
                // Add field in Movie for Slot color
                int columnIndex = -1;
                for (Room room: rooms) {
                    if(room.getId() == slot.getRoom().getId()){
                        columnIndex =  rooms.indexOf(room);
                    }
                }
                if (columnIndex == -1){
                    showDefaultErrorAlert("", null);
                    return;
                }
                setVboxInGridPane(createVbox(new Label(textLabel), "lightgray"), rowIndex, columnIndex);
                for (int j = 1; j < slotUsed; j++) {
                    setVboxInGridPane(createVbox(new Label(textLabel), "lightgray"), rowIndex+j, columnIndex);
                }
            }
        }
    }

    private void setVboxInGridPane(VBox vBox, int rowIndex, int columnIndex){
        scheduleGridPane.getChildren().add(rowIndex,vBox);
        GridPane.setRowIndex(vBox, rowIndex);
        GridPane.setColumnIndex(vBox, columnIndex);
    }

    private VBox createVbox(Label label, String backgroundColor){
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        if (!backgroundColor.isBlank()){
            vBox.setStyle("-fx-background-color: " + backgroundColor + ";");
        }
        return vBox;
    }
}