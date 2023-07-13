package gui.tournois;

import entite.MatchTournois;
import entite.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import service.TeamService;
import service.TournoisService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DrawController implements Initializable {

    @FXML
    private TextField first_phase_1;

    @FXML
    private TextField first_phase_2;

    @FXML
    private TextField first_phase_3;

    @FXML
    private TextField first_phase_4;

    @FXML
    private TextField second_phase_1;

    @FXML
    private TextField second_phase_2;

    @FXML
    private TextField final_phase;

    @FXML
    private TextField winner;

    public int tournoisId;

    private TournoisService tournoisService = new TournoisService();
    private TeamService teamService = new TeamService();

    public void setTournoisId(int tournoisId) {
        this.tournoisId = tournoisId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateFirstPhaseMatches();
        populateSecondPhaseMatches();
        populateFinalPhaseMatch();

    }

    private void populateFirstPhaseMatches() {
        List<MatchTournois> firstPhaseMatches = tournoisService.getFirstPhaseMatchesForCurrentTournament(1);

        for (int i = 0; i < firstPhaseMatches.size(); i++) {
            MatchTournois match = firstPhaseMatches.get(i);

            // Retrieve team names using team IDs
            Integer team1Id = match.getIdTeam1();
            Integer team2Id = match.getIdTeam2();

            String team1Name = "";
            if (team1Id != null) {
                Team team1 = teamService.readById(team1Id);
                if (team1 != null) {
                    team1Name = team1.getName();
                }
            }

            String team2Name = "";
            if (team2Id != null) {
                Team team2 = teamService.readById(team2Id);
                if (team2 != null) {
                    team2Name = team2.getName();
                }
            }

            // Assign team names to corresponding text fields
            TextField textField = getFirstPhaseTextField(i + 1);
            if (textField != null) {
                if (!team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs " + team2Name);
                } else if (team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText("........ vs " + team2Name);
                } else if (!team1Name.isEmpty() && team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs ..........");
                } else {
                    textField.setText(" vs ");
                }
            }
        }
    }


    private void populateSecondPhaseMatches() {
        List<MatchTournois> secondPhaseMatches = tournoisService.getSecondPhaseMatchesForCurrentTournament(1);

        for (int i = 0; i < secondPhaseMatches.size(); i++) {
            MatchTournois match = secondPhaseMatches.get(i);

            // Retrieve team names using team IDs
            Integer team1Id = match.getIdTeam1();
            Integer team2Id = match.getIdTeam2();

            String team1Name = "";
            if (team1Id != null) {
                Team team1 = teamService.readById(team1Id);
                if (team1 != null) {
                    team1Name = team1.getName();
                }
            }

            String team2Name = "";
            if (team2Id != null) {
                Team team2 = teamService.readById(team2Id);
                if (team2 != null) {
                    team2Name = team2.getName();
                }
            }

            // Assign team names to corresponding text fields
            TextField textField = getSecondPhaseTextField(i + 1);
            if (textField != null) {
                if (!team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs " + team2Name);
                } else if (team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText("........ vs " + team2Name);
                } else if (!team1Name.isEmpty() && team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs ..........");
                } else {
                    textField.setText(" vs ");
                }
            }
        }
    }



    private void populateFinalPhaseMatch() {
        List<MatchTournois> finalPhaseMatches = tournoisService.getFinalPhaseMatchesForCurrentTournament(1);

        for (int i = 0; i < finalPhaseMatches.size(); i++) {
            MatchTournois match = finalPhaseMatches.get(i);

            // Retrieve team names using team IDs
            Integer team1Id = match.getIdTeam1();
            Integer team2Id = match.getIdTeam2();

            String team1Name = "";
            if (team1Id != null) {
                Team team1 = teamService.readById(team1Id);
                if (team1 != null) {
                    team1Name = team1.getName();
                }
            }

            String team2Name = "";
            if (team2Id != null) {
                Team team2 = teamService.readById(team2Id);
                if (team2 != null) {
                    team2Name = team2.getName();
                }
            }

            // Assign team names to corresponding text fields
            TextField textField = getFinalPhaseTextField(i + 1);
            if (textField != null) {
                if (!team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs " + team2Name);
                } else if (team1Name.isEmpty() && !team2Name.isEmpty()) {
                    textField.setText("........ vs " + team2Name);
                } else if (!team1Name.isEmpty() && team2Name.isEmpty()) {
                    textField.setText(team1Name + " vs ..........");
                } else {
                    textField.setText(" vs ");
                }
            }
        }
    }



    private TextField getFirstPhaseTextField(int index) {
        switch (index) {
            case 1:
                return first_phase_1;
            case 2:
                return first_phase_2;
            case 3:
                return first_phase_3;
            case 4:
                return first_phase_4;
            default:
                return null;
        }
    }

    private TextField getSecondPhaseTextField(int index) {
        switch (index) {
            case 1:
                return second_phase_1;
            case 2:
                return second_phase_2;
            default:
                return null;
        }
    }
    private TextField getFinalPhaseTextField(int index) {
        switch (index) {
            case 1:
                return final_phase;
            default:
                return null;
        }
    }
}
