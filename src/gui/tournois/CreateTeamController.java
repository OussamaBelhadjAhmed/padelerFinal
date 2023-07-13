package gui.tournois;

import entite.Team;
import entite.User;
import gui.comptes.SessionContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.TeamService;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateTeamController implements Initializable{

    @FXML
    private TextField col_email_user1;

    @FXML
    private TextField col_email_user2;

    @FXML
    private TextField col_team_name;
    @FXML
    private Label col_tor_name;
    private boolean update;

    public void setUpdate(boolean b) {
        this.update = b;

    }
    public void setTextField(String idTournois) {
        col_tor_name.setText(idTournois);
    }

    public void addTeam() {
        String teamName = col_team_name.getText();
        String user2Email = col_email_user2.getText();

        // Retrieve User object for the current connected user
        User user1 = SessionContext.getInstance().getLoggedInUser();

        if (user1 == null) {
            showAlert("Error", "No user is currently logged in.");
            return;
        }

        // Check if the team name already exists
        TeamService teamService = new TeamService();
        Team existingTeam = teamService.readByName(teamName);
        if (existingTeam != null) {
            showAlert("Error", "The team name already exists.");
            return;
        }

        // Retrieve User object for user2
        UserService userService = new UserService();
        User user2 = userService.readByEmail(user2Email);

        if (user2 == null) {
            showAlert("Error", "Mate  Email does not exist.");
            return;
        }

        Team team = new Team(teamName);
        team.setUsers(new ArrayList<>());
        team.getUsers().add(user1);
        team.getUsers().add(user2);

        teamService.createTeam(team, user1.getEmail(), user2.getEmail());

        showAlert("Success", "Team created successfully.");

        // Navigate to example.fxml
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("example.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) col_team_name.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_email_user1.setDisable(true);
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}