/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.terrain;

import entite.Terrain;
import entite.Club;
import service.ClubService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.TerrainService;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class AddTerrainController implements Initializable {

    @FXML
    private TextField name;
    private boolean update;
    int idTerrain;
    String query;
    @FXML
    private ChoiceBox<String> selectedClub;
    private List<Club> clubs = new ArrayList<Club>(0);
    private String clubName;
    private int Status ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chargeList();
        List<String> noms = new ArrayList<>();
        noms = clubs.stream().map(Club::getClubName).collect(Collectors.toList());
        if (noms.size() != 0) {
            selectedClub.getItems().addAll(noms);
        } else {
            selectedClub.getItems().add("Acun Club !");
        }

    }

    @FXML
    private void addTerrain(MouseEvent event) {
        TerrainService ts = new TerrainService();
        ClubService cs = new ClubService();
        Terrain terrain = new Terrain();
        Club c = new Club();

        try {
            if (!update) {
                String terrainName = name.getText();
                String clubName = selectedClub.getValue();
                if (terrainName != null && clubName != null) {
                    terrain.setName(terrainName);
                    c = cs.readByClubName(clubName);
                    terrain.setClub(c);
                    ts.insert(terrain);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Terrain ajouté avec succès !");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs !");
                    alert.showAndWait();
                }
            } else {
                String terrainName = name.getText();
                String clubName = selectedClub.getValue();
                if (terrainName != null && clubName != null) {
                    terrain.setIdTerrain(idTerrain);
                    terrain.setName(terrainName);
                    c = cs.readByClubName(clubName);
                    terrain.setClub(c);
                    terrain.setStatus(Status);
                    ts.update(terrain);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Terrain mis à jour avec succès !");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs !");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AddTerrainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    private void clean(MouseEvent event) {
        name.setText(null);
    }

    void setTextField(int id, String nom, Integer status, String clubName) {

        idTerrain = id;
        name.setText(nom);
        this.clubName = clubName;
        this.Status = status;

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    void chargeList() {
        ClubService cd = new ClubService();
        clubs = cd.readAll();
    }

}
