package com.example.cinemamanagement;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class MovieAndRoomListTabController {

    @FXML
    private TableView<Movie> MovieTableView;

    @FXML
    private TableView<Room> RoomTableView;

    @FXML
    private TextField NameFilterField;

    @FXML
    private ComboBox<String> GenderFilterComboBox;

    @FXML
    private DatePicker StartDateFilterDatePicker;

    @FXML
    private DatePicker EndDateFilterDatePicker;

    @FXML
    private TextField DurationFilterField;

    @FXML
    private Button ResetFiltersButton;

    @FXML
    private TextField roomNameFilterField;

    @FXML
    private void applyRoomFilters() {
        // Appliquer les filtres pour la table des salles
    }

    @FXML
    private void resetRoomFilters() {
        // Réinitialiser les filtres pour la table des salles
    }

    private List<Movie> OriginalMovieList = new ArrayList<>();
    private List<Room> OriginalRoomList = new ArrayList<>();

    @FXML
    private void initialize() {
        ConfigureColumns();
        LoadGenderDataAndInitializFilter();
        LoadMovieData();
        LoadRoomData();
    }

    private void ConfigureColumns() {
        TableColumn<Movie, Integer> idColumn = (TableColumn<Movie, Integer>) MovieTableView.getColumns().get(0);
        TableColumn<Movie, String> nameColumn = (TableColumn<Movie, String>) MovieTableView.getColumns().get(1);
        TableColumn<Movie, String> detailsColumn = (TableColumn<Movie, String>) MovieTableView.getColumns().get(2);
        TableColumn<Movie, String> genderColumn = (TableColumn<Movie, String>) MovieTableView.getColumns().get(3);
        TableColumn<Movie, String> dateColumn = (TableColumn<Movie, String>) MovieTableView.getColumns().get(4);
        TableColumn<Movie, Integer> durationColumn = (TableColumn<Movie, Integer>) MovieTableView.getColumns().get(5);
        TableColumn<Movie, Void> actionColumn = (TableColumn<Movie, Void>) MovieTableView.getColumns().get(6);

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            //private final HBox buttonBox = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    // Logique pour modifier le film
                });

                deleteButton.setOnAction(event -> {
                    Movie movie = getTableView().getItems().get(getIndex());
                    // Logique pour supprimer le film
                });
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
        TableColumn<Room, Void> actionColumnRoom = (TableColumn<Room, Void>) RoomTableView.getColumns().get(3);
        actionColumnRoom.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            //private final HBox buttonBox = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Room room = getTableView().getItems().get(getIndex());
                    // Logique pour modifier le film
                });

                deleteButton.setOnAction(event -> {
                    Room room = getTableView().getItems().get(getIndex());
                    // Logique pour supprimer le film
                });
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

    private void LoadGenderDataAndInitializFilter() {
        List<String> genders = Arrays.asList("Action", "Comédie", "Drame", "Horreur", "Science-fiction", "Thriller");
        GenderFilterComboBox.getItems().addAll(genders);
        ResetFiltersButton.setDisable(true);
    }

    private void LoadMovieData() {
        // Charger les données de votre modèle de données ici et les ajouter à la TableView
        // Remplacer cette logique par la récupération des données réelles de votre base de données ou autre source
        OriginalMovieList.add(new Movie(1, "Film 1", "Détails 1", "Action", LocalDate.of(2024, 1, 1), 120));
        OriginalMovieList.add(new Movie(2, "Film 2", "Détails 2", "Comédie", LocalDate.of(2024, 2, 15), 90));
        MovieTableView.getItems().addAll(OriginalMovieList);
    }

    private void LoadRoomData() {
        // Charger les données de votre modèle de données ici et les ajouter à la TableView
        // Remplacer cette logique par la récupération des données réelles de votre base de données ou autre source
        OriginalRoomList.add(new Room(1, "Salle 1", 120));
        OriginalRoomList.add(new Room(2, "Salle 2", 50));
        RoomTableView.getItems().addAll(OriginalRoomList);
    }

    @FXML
    private void ApplyFilters() {
        // Get data from filter
        String nameFilter = NameFilterField.getText().toLowerCase();
        String genderFilter = GenderFilterComboBox.getValue();
        LocalDate startDateFilter = StartDateFilterDatePicker.getValue();
        LocalDate endDateFilter = EndDateFilterDatePicker.getValue();
        Integer durationFilter = ParseDuration(DurationFilterField.getText());

        // Filter table data from filtered data
        FilteredList<Movie> filteredData = MovieTableView.getItems().filtered(movie -> {
            // Filter by name
            if (nameFilter != null && !nameFilter.isEmpty() && !movie.getName().toLowerCase().contains(nameFilter)) {
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
            if (durationFilter != null && movie.getDuration() != durationFilter) {
                return false;
            }
            return true;
        });

        // Update table data with filtered data
        MovieTableView.setItems(filteredData);
        if (filteredData.size() != OriginalMovieList.size()){
            ResetFiltersButton.setDisable(false);
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
    private void ResetFilters() {
        NameFilterField.clear();
        GenderFilterComboBox.getSelectionModel().clearSelection();
        StartDateFilterDatePicker.setValue(null);
        EndDateFilterDatePicker.setValue(null);
        DurationFilterField.clear();
        ConfigureColumns();
        MovieTableView.setItems(FXCollections.observableArrayList(OriginalMovieList));
        ResetFiltersButton.setDisable(true);
    }
}
