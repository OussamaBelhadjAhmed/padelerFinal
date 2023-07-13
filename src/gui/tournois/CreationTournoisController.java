package gui.tournois;

import entite.Club;
import entite.Tournois;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.ClubService;
import service.TournoisService;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class CreationTournoisController implements Initializable {

    @FXML
    private ComboBox<String> addT_club;
    @FXML
    private Button draw;
    @FXML
    private Button clearButton;
    @FXML
    private DatePicker addT_dateD;

    @FXML
    private DatePicker addT_dateFin;

    @FXML
    private TextField addT_id;

    @FXML
    private TextField addT_name;

    @FXML
    private TextField addT_type;

    @FXML
    private Button adduser_addbtn;

    @FXML
    private Button details;

    @FXML
    private TableView<Tournois> adduser_col_tableView;



    @FXML
    private Button adduser_updatebtn;
    @FXML
    private Button delete;

    @FXML
    private AnchorPane arche;

    @FXML
    private TableColumn<Tournois, String> club;

    @FXML
    private TableColumn<Tournois, Date> dateDebut;

    @FXML
    private TableColumn<Tournois, Date> dateFin;

    @FXML
    private TableColumn<Tournois,String> etat;

    @FXML
    private TableColumn<Tournois, String> name_tournois;


    @FXML
    private TableColumn<Tournois, Integer> participants;

    @FXML
    private TableColumn<Tournois, Integer> tournois_id;

    @FXML
    private TextField searchButton;

    @FXML
    private TableColumn<Tournois, String> type;
    @FXML
    private Button refreshButton;

    TournoisService tournoisService = new TournoisService();
    private ObservableList<Tournois> observableTournoisList;
    private Labeled searchField;

    public void addTournois() {
        String name = addT_name.getText().trim();
        String type = addT_type.getText().trim();
        LocalDate dateD = addT_dateD.getValue();
        LocalDate dateFin = addT_dateFin.getValue();
        String selectedClub = addT_club.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || type.isEmpty() || dateD == null || dateFin == null) {
            // At least one input field is empty, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
        } else if (dateD.isAfter(dateFin)) {
            // dateDebut is later than dateFin, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The starting date must be earlier than the ending date!");
            alert.showAndWait();
        } else {
            Date sqlDateD = Date.valueOf(dateD);
            Date sqlDateFin = Date.valueOf(dateFin);
            ClubService clubService = new ClubService();
            Club club = clubService.readByClubName(selectedClub);

            // Check if there are any existing tournaments with overlapping date range
            List<Tournois> existingTournaments = tournoisService.getTournamentsByClubAndDateRange(club.getIdClub(), sqlDateD, sqlDateFin);
            if (!existingTournaments.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The date range overlaps with an existing tournament!");
                alert.showAndWait();
            } else {
                Tournois tournois = new Tournois();
                tournois.setName(name);
                tournois.setType(type);
                tournois.setDateDebut(sqlDateD);
                tournois.setDateFin(sqlDateFin);
                tournois.setIdClub(club.getIdClub());
                tournoisService.insert(tournois);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Tournament inserted successfully!");
                alert.showAndWait();

                // Clear the input fields
                addT_name.clear();
                addT_type.clear();
                addT_dateD.setValue(null);
                addT_dateFin.setValue(null);
            }
        }
    }
    public void showTournois() {
        List<Tournois> tournoisList = tournoisService.readAll();
        ObservableList<Tournois> observableTournoisList = FXCollections.observableArrayList(tournoisList);

        tournois_id.setCellValueFactory(new PropertyValueFactory<>("idTournois"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        name_tournois.setCellValueFactory(new PropertyValueFactory<>("name"));
        club.setCellValueFactory(data -> {
            int clubId = data.getValue().getIdClub();
            ClubService clubService = new ClubService();
            Club club = clubService.readById(clubId);
            return new SimpleStringProperty(club.getClubName());
        });

        etat.setCellValueFactory(data -> {
            LocalDate currentDate = LocalDate.now();
            LocalDate dateDebutValue = data.getValue().getDateDebut().toLocalDate();
            LocalDate dateFinValue = data.getValue().getDateFin().toLocalDate();

            String value;
            if (currentDate.isBefore(dateDebutValue)) {
                value = "Coming Soon";
            } else if (currentDate.isEqual(dateDebutValue) || (currentDate.isAfter(dateDebutValue) && currentDate.isBefore(dateFinValue))) {
                value = "En Cours";
            } else {
                value = "Finished";
            }

            return new SimpleStringProperty(value);
        });
        participants.setCellValueFactory(data -> {
            int tournoisId = data.getValue().getIdTournois();
            int count = tournoisService.countParticipent(tournoisId);
            return new SimpleIntegerProperty(count).asObject();
        });

        adduser_col_tableView.setItems(observableTournoisList);
    }

    public void selectedTournois() {
        Tournois tournois = adduser_col_tableView.getSelectionModel().getSelectedItem();

        if (tournois != null) {
            addT_id.setText(String.valueOf(tournois.getIdTournois()));
            addT_name.setText(tournois.getName());
            addT_type.setText(tournois.getType());
            addT_dateD.setValue(tournois.getDateDebut().toLocalDate());
            addT_dateFin.setValue(tournois.getDateFin().toLocalDate());
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTournois();
        selectedTournois();
        adduser_col_tableView.setOnMouseClicked(this::handleTournoisSelection);
        addClubList();
        clearButton.setOnAction(event -> clearData());
        refreshButton.setOnAction(event -> refreshData());
        searchButton.setOnAction(this::handleSearchButton);
        searchButton.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTournois();
        });
    }
    private void handleTournoisSelection(MouseEvent event) {
        selectedTournois();
    }
    public void updateTournois() {
        Tournois tournois = adduser_col_tableView.getSelectionModel().getSelectedItem();

        if (tournois != null) {
            int id = tournois.getIdTournois();
            String name = addT_name.getText().trim();
            String type = addT_type.getText().trim();
            LocalDate dateD = addT_dateD.getValue();
            LocalDate dateFin = addT_dateFin.getValue();

            if (name.isEmpty() || type.isEmpty() || dateD == null || dateFin == null) {
                // At least one input field is empty, show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields!");
                alert.showAndWait();
            } else if (dateD.isAfter(dateFin)) {
                // dateDebut is later than dateFin, show alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The starting date must be earlier than the ending date!");
                alert.showAndWait();
            } else {
                Date sqlDateD = Date.valueOf(dateD);
                Date sqlDateFin = Date.valueOf(dateFin);

                // Update only the specified attributes
                tournois.setName(name);
                tournois.setType(type);
                tournois.setDateDebut(sqlDateD);
                tournois.setDateFin(sqlDateFin);

                tournoisService.update(tournois);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Tournament updated successfully!");
                alert.showAndWait();

                // Clear the input fields
                addT_name.clear();
                addT_type.clear();
                addT_dateD.setValue(null);
                addT_dateFin.setValue(null);

                showTournois();
            }
        }
    }


    public void searchTournois() {
        String searchText = searchButton.getText().trim();
        List<Tournois> searchResults = tournoisService.searchTournois(searchText);
        ObservableList<Tournois> observableSearchResults = FXCollections.observableArrayList(searchResults);
        adduser_col_tableView.setItems(observableSearchResults);
    }
    @FXML
    private void handleSearchButton(ActionEvent event) {
        searchTournois();
    }



    public void addClubList() {
        ClubService clubService = new ClubService();
        List<Club> clubList = clubService.readAll();
        List<String> clubNames = new ArrayList<>();

        for (Club club : clubList) {
            clubNames.add(club.getClubName());
        }

        ObservableList<String> listData = FXCollections.observableArrayList(clubNames);
        addT_club.setItems(listData);
    }

    public void deleteTournois() {
        Tournois tournois = adduser_col_tableView.getSelectionModel().getSelectedItem();

        if (tournois != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected tournament?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                tournoisService.delete(tournois.getIdTournois());

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Information");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Tournament deleted successfully!");
                successAlert.showAndWait();

                showTournois();
            }
        }
    }

    @FXML
    private void handleDetailsButton(ActionEvent event) {
        Tournois tournois = adduser_col_tableView.getSelectionModel().getSelectedItem();

        if (tournois != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsTournois.fxml"));
                Parent root = loader.load();

                DetailsTournoisController detailsController = loader.getController();
                detailsController.setTournoisId(tournois.getIdTournois());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void handleDrawButton(ActionEvent event) {
        Tournois tournois = adduser_col_tableView.getSelectionModel().getSelectedItem();

        if (tournois != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("draw.fxml"));
                Parent root = loader.load();

                DrawController drawController = loader.getController();
                //TournoisId.tournoisId=tournois.getIdTournois();

                drawController.setTournoisId(tournois.getIdTournois());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close the current window
                Stage currentStage = (Stage) draw.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void clearData() {
        addT_name.clear();
        addT_type.clear();
        addT_dateD.setValue(null);
        addT_dateFin.setValue(null);
        addT_club.getSelectionModel().clearSelection();
        addT_id.clear();
    }
    @FXML
    public void refreshData() {
        showTournois();
    }








}
