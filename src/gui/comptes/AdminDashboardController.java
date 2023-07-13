package gui.comptes;

import gui.comptes.SessionContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button g_reclamation;

    @FXML
    private Button g_tournois;

    @FXML
    private Button g_demandes;
    @FXML
    private Button g_dispo;

    @FXML
    private AnchorPane layout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button g_terrain;

    @FXML
    private Button g_publication;

    @FXML
    private Button minimize;

    @FXML
    private Button g_club;

    @FXML
    private Label username;

    @FXML
    private Button g_materiel;

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
    }

    @FXML
    void switchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();
        switch (buttonId) {
            case "g_tournois":
                // Load and display the Add User form in the center
                loadForm("tournois/CreationTournois.fxml");
                break;
            case "g_reclamation":
                // Load and display the Add User form in the center
                loadForm("reclamation/UserReclamation.fxml");
                break;
            case "g_demandes":
                // Load and display the Add User form in the center
                loadForm("materiel/ListeDemande.fxml");
                break;
            case "g_materiel":
                // Load and display the Add User form in the center
                loadForm("materiel/ListeMateriel.fxml");
                break;
            case "g_club":
                // Load and display the Add User form in the center
                loadForm("terrain/tableViewClub.fxml");
                break;
            case "g_terrain":
                // Load and display the Add User form in the center
                loadForm("terrain/tableViewTerrain.fxml");
                break;
            case "g_dispo":
                // Load and display the Add User form in the center
                loadForm("terrain/manageDisponibiliteTerrain.fxml");
                break;
            case "g_publication":
                // Load and display the Add User form in the center
                loadForm("publication/ListPublication.fxml");
                break;
            default:
                break;
        }
    }

    private void loadForm(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlFileName));
            AnchorPane form = loader.load();

            layout.getChildren().setAll(form);
            AnchorPane.setTopAnchor(form, 0.0);
            AnchorPane.setRightAnchor(form, 0.0);
            AnchorPane.setBottomAnchor(form, 0.0);
            AnchorPane.setLeftAnchor(form, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        username.setText(SessionContext.getInstance().getLoggedInUser().getEmail());
        System.out.println(username);
    }
}
