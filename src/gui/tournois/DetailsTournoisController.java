package gui.tournois;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import entite.Tournois;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import entite.MatchTournois;
import entite.Team;
import entite.Terrain;
import service.TeamService;
import service.TerrainService;
import service.TournoisService;

public class DetailsTournoisController implements Initializable {

    @FXML
    private TableView<MatchTournois> matchList;
    @FXML
    private ComboBox<String> add_winner;
    @FXML
    private ComboBox<String> phase;

    @FXML
    private AnchorPane arche;

    @FXML
    private TableColumn<MatchTournois, Date> date;

    @FXML
    private TextField final_winner;

    @FXML
    private TableColumn<MatchTournois, String> firstTeam;

    @FXML
    private TableColumn<MatchTournois, Integer> idMatchTournois;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<MatchTournois, String> secondTeam;

    @FXML
    private TableColumn<MatchTournois, String> terrain;

    @FXML
    private TableColumn<MatchTournois, String> winner;

    private int tournoisId;

    private TournoisService tournoisService = new TournoisService();


    public void setTournoisId(int tournoisId) {
        this.tournoisId = tournoisId;
        List<MatchTournois> matchesList = tournoisService.findTournoisDetailsById(tournoisId);
        ObservableList<MatchTournois> observableMatchesList = FXCollections.observableArrayList(matchesList);
        matchList.setItems(observableMatchesList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showMatches();
        System.out.println(tournoisId);
        selectedMatch();
        matchList.setOnMouseClicked(this::handleMatchSelection);
        phase.getItems().addAll("first_phase", "second_phase", "final_phase");
    }

    public void showMatches() {
        TournoisService tournoisService = new TournoisService();
        List<MatchTournois> matchesList = tournoisService.findTournoisDetailsById(tournoisId);
        ObservableList<MatchTournois> observableMatchesList = FXCollections.observableArrayList(matchesList);

        idMatchTournois.setCellValueFactory(new PropertyValueFactory<>("idMatchTournois"));
        firstTeam.setCellValueFactory(data -> {
            int teamId = data.getValue().getIdTeam1();
            TeamService teamService = new TeamService();
            Team team = teamService.readById(teamId);
            return new SimpleStringProperty(team.getName());
        });
        secondTeam.setCellValueFactory(data -> {
            int teamId = data.getValue().getIdTeam2();
            TeamService teamService = new TeamService();
            Team team = teamService.readById(teamId);
            return new SimpleStringProperty(team.getName());
        });
        date.setCellValueFactory(new PropertyValueFactory<>("dateMatch"));
        terrain.setCellValueFactory(data -> {
            int terrainId = data.getValue().getIdTerrain();
            TerrainService terrainService = new TerrainService();
            Terrain terrain = terrainService.readById(terrainId);
            return new SimpleStringProperty(terrain.getName());
        });
        winner.setCellValueFactory(data -> {
            int teamId = data.getValue().getIdTeamWinner();
            if (teamId != 0) {
                TeamService teamService = new TeamService();
                Team team = teamService.readById(teamId);
                return new SimpleStringProperty(team.getName());
            } else {
                return new SimpleStringProperty("Not determined");
            }
        });

        // Set cell factory for the winner column to change background color, add padding and border
        winner.setCellFactory(column -> new TableCell<MatchTournois, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    MatchTournois match = (MatchTournois) getTableRow().getItem();
                    boolean isPlayed = match != null && match.getIdTeamWinner() != 0; // Check if match and winner ID are not null or 0
                    if (isPlayed) {
                        setText(item);
                        setPadding(new Insets(5));
                        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
                    } else {
                        setText("Not started yet");
                        setPadding(new Insets(5));
                        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                        setBackground(Background.EMPTY);
                    }
                } else {
                    setText(null);
                    setPadding(new Insets(5));
                    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    setBackground(Background.EMPTY);
                }
            }
        });


        matchList.setItems(observableMatchesList);
    }

    public void selectedMatch() {
        MatchTournois matchTournois = matchList.getSelectionModel().getSelectedItem();

        if (matchTournois != null) {
            int team1Id = matchTournois.getIdTeam1();
            int team2Id = matchTournois.getIdTeam2();
            Date matchDate = matchTournois.getDateMatch();

            // Disable match selection and winner assignment if the match date is after today's date

            // Populate the add_winner ComboBox with the team names
            TeamService teamService = new TeamService();
            String team1Name = teamService.readById(team1Id).getName();
            String team2Name = teamService.readById(team2Id).getName();
            ObservableList<String> teamNames = FXCollections.observableArrayList(team1Name, team2Name);
            add_winner.setItems(teamNames);
        }
    }

    private void handleMatchSelection(MouseEvent event) {
        selectedMatch();
    }

    public void addWinnerFromSelectedMatch() {
        MatchTournois matchTournois = matchList.getSelectionModel().getSelectedItem();
        String selectedWinner = add_winner.getSelectionModel().getSelectedItem();
        String selectedPhase = phase.getSelectionModel().getSelectedItem();

        if (matchTournois != null && selectedWinner != null && selectedPhase != null) {
            // Get the Team object for the selected winner
            TeamService teamService = new TeamService();
            Team winnerTeam = teamService.readByName(selectedWinner);

            // Update the match with the winner and phase
            matchTournois.setIdTeamWinner(winnerTeam.getIdTeam());
            matchTournois.setPhase(selectedPhase);
            tournoisService.updateMatchTournois(matchTournois);

            // Check if idTeam2 is not 0 before adding to the second phase
            if (matchTournois.getIdTeam2() != 0) {
                tournoisService.addSecondPhaseMatch(tournoisId, winnerTeam.getIdTeam());
            }

            // Show a success message or perform any other required actions
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Winner added successfully!");
            alert.showAndWait();

            // Refresh the match list
            showMatches();
        }
    }



}
