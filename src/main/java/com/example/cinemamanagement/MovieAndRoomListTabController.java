package com.example.cinemamanagement;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MovieAndRoomListTabController extends CinemaManagementController {

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TextField movieNameFilterField;

    @FXML
    private ComboBox<String> movieGenderFilterComboBox;

    @FXML
    private DatePicker movieStartDateFilterDatePicker;

    @FXML
    private DatePicker movieEndDateFilterDatePicker;

    @FXML
    private TextField movieDurationFilterField;

    @FXML
    private Button resetMovieFiltersButton;

    @FXML
    private TextField roomNameFilterField;

    @FXML
    private Button resetRoomFiltersButton;

    private List<Movie> originalMovieList = new ArrayList<>();
    private List<Room> originalRoomList = new ArrayList<>();

    @FXML
    private void initialize() {
        configureColumns();
        loadGenderDataAndInitializeFilter();
        loadMovieData();
        loadRoomData();
    }

    private void configureColumns() {
        TableColumn<Movie, Integer> idColumn = (TableColumn<Movie, Integer>) movieTableView.getColumns().get(0);
        TableColumn<Movie, String> nameColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(1);
        TableColumn<Movie, String> detailsColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(2);
        TableColumn<Movie, String> genderColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(3);
        TableColumn<Movie, String> dateColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(4);
        TableColumn<Movie, Integer> durationColumn = (TableColumn<Movie, Integer>) movieTableView.getColumns().get(5);
        TableColumn<Movie, Void> actionColumn = (TableColumn<Movie, Void>) movieTableView.getColumns().get(6);

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            {
                editButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    movieEditPopup(movie);
                });

                deleteButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    // Logique pour supprimer le film
                    originalMovieList.remove(movie);
                    movieTableView.setItems(FXCollections.observableArrayList(originalMovieList));
                    showAlert("Film supprimé", "Le film \"" + movie.getName() + "\" a été supprimé avec succès !",
                            Alert.AlertType.INFORMATION);
                });
            }

            private void movieEditPopup(Movie movie) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieEditPopup.fxml"));
                    Parent root = loader.load();
                    MovieEditPopupController controller = loader.getController();
                    controller.setMovieEditPopup(movie, root);
                    //GetMovie and Refresh table
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    VBox buttonBox = new VBox(editButton, deleteButton);
                    buttonBox.setSpacing(5); // Espacement entre les boutons
                    setGraphic(buttonBox);
                }
            }
        });

        TableColumn<Room, Void> actionColumnRoom = (TableColumn<Room, Void>) roomTableView.getColumns().get(3);
        actionColumnRoom.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                editButton.setOnAction(event -> {
                    Room room = getTableView().getItems().get(getIndex());
                    roomEditPopup(room);
                });

                deleteButton.setOnAction(event -> {
                    Room room = getTableView().getItems().get(getIndex());
                    // Logique pour supprimer le film

                    originalRoomList.remove(room);
                    roomTableView.setItems(FXCollections.observableArrayList(originalRoomList));
                    showAlert("Salle supprimé", "La salle \"" + room.getName() + "\" a été supprimé avec succès !",
                            Alert.AlertType.INFORMATION);
                });
            }

            private void roomEditPopup(Room room) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("RoomEditPopup.fxml"));
                    Parent root = loader.load();
                    RoomEditPopupController controller = loader.getController();
                    controller.setRoomEditPopup(room, root);
                    //GetRoom and Refresh table
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    VBox buttonBox = new VBox(editButton, deleteButton);
                    buttonBox.setSpacing(5); // Espacement entre les boutons
                    setGraphic(buttonBox);
                }
            }
        });
    }

    private void loadGenderDataAndInitializeFilter() {
        List<String> genders = Arrays.asList("Action", "Comédie", "Drame", "Horreur", "Science-fiction", "Thriller");
        movieGenderFilterComboBox.getItems().addAll(genders);
    }

    private void loadMovieData() {
        // BDD GetMovies
        originalMovieList.add(new Movie(1, "Film 1", "Détails 1", "Action", LocalDate.of(2024, 1, 1), 120));
        originalMovieList.add(new Movie(2, "Film 2", "Détails 2", "Comédie", LocalDate.of(2024, 2, 15), 90));
        movieTableView.getItems().addAll(originalMovieList);
    }

    private void loadRoomData() {
        // BDD GetRooms
        originalRoomList.add(new Room(1, "Salle 1", 120));
        originalRoomList.add(new Room(2, "Salle 2", 50));
        roomTableView.getItems().addAll(originalRoomList);
    }

    @FXML
    private void applyMovieFilters() {
        // Get data from filter
        String nameFilter = movieNameFilterField.getText().toLowerCase();
        String genderFilter = movieGenderFilterComboBox.getValue();
        LocalDate startDateFilter = movieStartDateFilterDatePicker.getValue();
        LocalDate endDateFilter = movieEndDateFilterDatePicker.getValue();
        Integer durationFilter = ParseDuration(movieDurationFilterField.getText());

        // Filter table data from filtered data
        FilteredList<Movie> filteredData = movieTableView.getItems().filtered(movie -> {
            // Filter by name
            if (!nameFilter.isEmpty() && !movie.getName().toLowerCase().contains(nameFilter)) {
                return false;
            }
            // Filter by gender
            if (genderFilter != null && !genderFilter.isEmpty() && !movie.getGender().equalsIgnoreCase(genderFilter)) {
                return false;
            }
            // Filter by date
            if (startDateFilter != null && movie.getReleaseDate().isBefore(startDateFilter)) {
                return false;
            }
            if (endDateFilter != null && movie.getReleaseDate().isAfter(endDateFilter)) {
                return false;
            }
            // Filter by duration
            return durationFilter == null || movie.getDuration() == durationFilter;
        });

        // Update table data with filtered data
        movieTableView.setItems(filteredData);
        if (filteredData.size() != originalMovieList.size()){
            resetMovieFiltersButton.setDisable(false);
        }

    }

    private Integer ParseDuration(String durationString) {
        try {
            return Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @FXML
    private void resetMovieFilters() {
        movieNameFilterField.clear();
        movieGenderFilterComboBox.getSelectionModel().clearSelection();
        movieStartDateFilterDatePicker.setValue(null);
        movieEndDateFilterDatePicker.setValue(null);
        movieDurationFilterField.clear();
        configureColumns();
        movieTableView.setItems(FXCollections.observableArrayList(originalMovieList));
        resetMovieFiltersButton.setDisable(true);
    }

    @FXML
    private void applyRoomFilters() {
        // Get data from filter
        String nameFilter = roomNameFilterField.getText().toLowerCase();
        FilteredList<Room> filteredData = roomTableView.getItems().filtered(room -> {
            // Filter by name
            return nameFilter.isEmpty() || room.getName().toLowerCase().contains(nameFilter);
        });

        // Update table data with filtered data
        roomTableView.setItems(filteredData);
        if (filteredData.size() != originalRoomList.size()){
            resetRoomFiltersButton.setDisable(false);
        }
    }

    @FXML
    private void resetRoomFilters() {
        roomNameFilterField.clear();
        configureColumns();
        roomTableView.setItems(FXCollections.observableArrayList(originalRoomList));
        resetRoomFiltersButton.setDisable(true);
    }
}
