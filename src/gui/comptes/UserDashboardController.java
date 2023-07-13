package gui.comptes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {

    @FXML
    private StackPane contentPane;

    @FXML
    private Button match;
    @FXML
    private Button g_reclamation;

    @FXML
    private Button g_tournois;

    @FXML
    private Button g_publication;
    @FXML
    private Button g_club;
    @FXML
    private Button g_materiel;
    @FXML
    private Button g_reservation;



    @FXML
    void switchForm(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "g_publication":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/publication/publications.fxml");
                break;
            case "g_tournois":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/tournois/CreateTeam.fxml");
                break;
            case "g_materiel":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/materiel/Materiel.fxml");
                break;
            case "g_reservation":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/reservation/reservationtionUser.fxml");
                break;
            case "g_club":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/reservation/listClubForUset.fxml");
                break;
            case "g_reclamation":
                // Load and display the CreateTeam form in the center
                loadForm("/gui/reclamation/addReclamation.fxml");
                break;


            default:
                break;
        }
    }
    @FXML
    void logout(ActionEvent event) {
        System.out.println("im here logut");
       /* SessionContext.getInstance().setLoggedInUser(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent loginForm = loasder.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(loginForm));
            stage.show();

            // Close the current stage (User Dashboard)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void loadForm(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            AnchorPane form = loader.load();

            contentPane.getChildren().setAll(form);
            AnchorPane.setTopAnchor(form, 0.0);
            AnchorPane.setRightAnchor(form, 0.0);
            AnchorPane.setBottomAnchor(form, 0.0);
            AnchorPane.setLeftAnchor(form, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
