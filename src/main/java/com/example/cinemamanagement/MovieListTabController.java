package com.example.cinemamanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.DatePicker;

public class MovieListTabController {

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TextField nameFilterField;

    @FXML
    private ComboBox<String> genreFilterComboBox;

    @FXML
    private DatePicker startDateFilterDatePicker;

    @FXML
    private DatePicker endDateFilterDatePicker;

    @FXML
    private TextField durationFilterField;

    @FXML
    private Button resetFiltersButton;

    private List<Movie> originalMovieList = new ArrayList<>();

    @FXML
    private void initialize() {
        configureColumns();
        List<String> genres = Arrays.asList("Action", "Comédie", "Drame", "Horreur", "Science-fiction", "Thriller");
        genreFilterComboBox.getItems().addAll(genres);
        resetFiltersButton.setDisable(true);
        loadMovieData();
    }

    private void configureColumns() {
        TableColumn<Movie, Integer> idColumn = (TableColumn<Movie, Integer>) movieTableView.getColumns().get(0);
        TableColumn<Movie, String> nameColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(1);
        TableColumn<Movie, String> detailsColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(2);
        TableColumn<Movie, String> genreColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(3);
        TableColumn<Movie, String> dateColumn = (TableColumn<Movie, String>) movieTableView.getColumns().get(4);
        TableColumn<Movie, Integer> durationColumn = (TableColumn<Movie, Integer>) movieTableView.getColumns().get(5);
        TableColumn<Movie, Void> actionColumn = (TableColumn<Movie, Void>) movieTableView.getColumns().get(6);

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox buttonBox = new HBox(editButton, deleteButton);

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
                    //VBox buttonBox = new VBox(editButton, deleteButton);
                    buttonBox.setSpacing(5); // Espacement entre les boutons
                    setGraphic(buttonBox);
                }
            }
        });
    }

    private void loadMovieData() {
        // Charger les données de votre modèle de données ici et les ajouter à la TableView
        // Remplacer cette logique par la récupération des données réelles de votre base de données ou autre source
        originalMovieList.add(new Movie(1, "Film 1", "Détails 1", "Action", LocalDate.of(2024, 1, 1), 120));
        originalMovieList.add(new Movie(2, "Film 2", "Détails 2", "Comédie", LocalDate.of(2024, 2, 15), 90));
        movieTableView.getItems().addAll(originalMovieList);
    }

    @FXML
    private void applyFilters() {
        // Récupérer les valeurs des champs de filtre
        String nameFilter = nameFilterField.getText().toLowerCase();
        String genreFilter = genreFilterComboBox.getValue();
        LocalDate startDateFilter = startDateFilterDatePicker.getValue();
        LocalDate endDateFilter = endDateFilterDatePicker.getValue();
        Integer durationFilter = parseDuration(durationFilterField.getText());

        // Filtrer les données du tableau en fonction des valeurs des filtres
        FilteredList<Movie> filteredData = movieTableView.getItems().filtered(movie -> {
            // Filtrer par nom
            if (nameFilter != null && !nameFilter.isEmpty() && !movie.getName().toLowerCase().contains(nameFilter)) {
                return false;
            }
            // Filtrer par genre
            if (genreFilter != null && !genreFilter.isEmpty() && !movie.getGenre().equalsIgnoreCase(genreFilter)) {
                return false;
            }
            // Filtrer par date de sortie
            if (startDateFilter != null && movie.getReleaseDate().isBefore(startDateFilter)) {
                return false;
            }
            if (endDateFilter != null && movie.getReleaseDate().isAfter(endDateFilter)) {
                return false;
            }
            // Filtrer par durée
            if (durationFilter != null && movie.getDuration() != durationFilter) {
                return false;
            }
            return true; // Conserver les entrées qui passent tous les filtres
        });

        // Mettre à jour les données du TableView avec les données filtrées
        movieTableView.setItems(filteredData);
        if (filteredData.size() != originalMovieList.size()){
            resetFiltersButton.setDisable(false);
        }

    }

    // Méthode utilitaire pour convertir une chaîne en entier, en gérant les exceptions
    private Integer parseDuration(String durationString) {
        try {
            return Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            return null; // Retourner null si la conversion échoue
        }
    }

    @FXML
    private void resetFilters() {
        nameFilterField.clear();
        genreFilterComboBox.getSelectionModel().clearSelection();
        startDateFilterDatePicker.setValue(null);
        endDateFilterDatePicker.setValue(null);
        durationFilterField.clear();
        configureColumns();
        movieTableView.setItems(FXCollections.observableArrayList(originalMovieList));
        resetFiltersButton.setDisable(true);
    }
}
