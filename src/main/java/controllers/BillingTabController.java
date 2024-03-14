package controllers;

import classes.Movie;
import classes.Price;
import classes.Room;
import dataBaseSQL.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class BillingTabController extends CinemaManagementController {
    @FXML
    public ComboBox<Movie> comboBoxMovies;

    @FXML
    public ComboBox<Room> comboBoxRooms;

    @FXML
    private ComboBox<Price> comboBoxPrice;

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    public void initialize() {
        comboBoxPrice.setOnMouseClicked(mouseEvent -> {
            List<Price> pricing = PriceSQL.GetPricing();
            comboBoxPrice.setItems(FXCollections.observableArrayList(pricing));
            comboBoxPrice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    nameField.setText(newValue.getName());
                    costField.setText(String.valueOf(newValue.getCost()));
                }
            });
        });

        comboBoxMovies.setOnMouseClicked(mouseEvent -> {
            List<Movie> movies = MovieSQL.GetMovies();
            comboBoxMovies.setItems(FXCollections.observableArrayList(movies));
        });

        comboBoxRooms.setOnMouseClicked(mouseEvent -> {
            List<Room> rooms = RoomSQL.GetRooms();
            comboBoxRooms.setItems(FXCollections.observableArrayList(rooms));
        });
    }

    @FXML
    public void editAction() {
        Price selectedPrice = comboBoxPrice.getSelectionModel().getSelectedItem();
        if (selectedPrice != null) {
            String name = nameField.getText();
            if (name.isBlank()){
                showAlert("Erreur", "Le nom est obligatoire", Alert.AlertType.ERROR);
                return;
            }
            selectedPrice.setName(name);
            int cost;
            try {
                cost = Integer.parseInt(costField.getText());
            } catch (NumberFormatException e){
                showAlert("Erreur", "Le prix est obligatoire, il faut qu'il soit au format numérique",
                        Alert.AlertType.ERROR);
                return;
            }
            selectedPrice.setCost(cost);

            SQLException exception = PriceSQL.UpdatePrice(selectedPrice);
            if (exception != null){
                showDefaultErrorAlert(" lors de la modification du tarif", exception);
            } else {
                showAlert("Tarif modifié", "Le tarif \"" + selectedPrice.getName() + "\" a été modifié avec succès !",
                        Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void deleteAction() {
        Price selectedPrice = comboBoxPrice.getSelectionModel().getSelectedItem();
        if (selectedPrice != null) {
            comboBoxPrice.getItems().remove(selectedPrice);
            SQLException exception = PriceSQL.DeletePrice(selectedPrice);
            if (exception != null){
                showDefaultErrorAlert(" lors de la suppression du tarif", exception);
            } else {
                showAlert("Tarif supprimé", "Le tarif \"" + selectedPrice.getName() + "\" a été supprimé avec succès !",
                        Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void getTodayIncome() {
        Movie selectedMovie = comboBoxMovies.getSelectionModel().getSelectedItem();
        Room selectedRoom = comboBoxRooms.getSelectionModel().getSelectedItem();

       if (selectedMovie == null && selectedRoom == null){
           float todayIncome = SlotPricingSQL.GetTodayIncome(null, null);
           showAlert("Revenu du jour", "Aujourd'hui nous avons réalisons un revenu de " + todayIncome +
                           "€ pour toutes les salles et films",
                   Alert.AlertType.INFORMATION);
       } else if (selectedMovie != null && selectedRoom == null) {
           float todayIncome = SlotPricingSQL.GetTodayIncome(selectedMovie, null);
           showAlert("Revenu du jour", "Aujourd'hui nous avons réalisons un revenu de " + todayIncome +
                           "€ pour le film '" + selectedMovie.getName() + "'",
                   Alert.AlertType.INFORMATION);
       } else if (selectedMovie == null && selectedRoom != null) {
           float todayIncome = SlotPricingSQL.GetTodayIncome(null, selectedRoom);
           showAlert("Revenu du jour", "Aujourd'hui nous avons réalisons un revenu de " + todayIncome +
                           "€ pour la salle '" + selectedRoom.getName() + "'",
                   Alert.AlertType.INFORMATION);
       } else {
           float todayIncome = SlotPricingSQL.GetTodayIncome(selectedMovie, selectedRoom);
           showAlert("Revenu du jour", "Aujourd'hui nous avons réalisons un revenu de " + todayIncome +
                           "€ pour le film '" + selectedMovie.getName() + "' dans la salle '" + selectedRoom.getName() + "'",
                   Alert.AlertType.INFORMATION);
       }
    }
}
