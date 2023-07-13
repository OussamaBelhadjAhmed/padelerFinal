/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tournois;

import entite.Club;
import entite.Disponibiliteterrain;
import entite.Terrain;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import service.ClubService;
import service.DiponibiliteTerrainService;
import service.TerrainService;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class AddNewDateControllers implements Initializable {

    @FXML
    private ChoiceBox<String> selectedClub;
    @FXML
    private DatePicker choiceDate;
    @FXML
    private ChoiceBox<String> selectedTerrain;

    private List<Club> clubs = new ArrayList<Club>(0);
    private List<Club> clubsInterface = new ArrayList<Club>(0);
    private List<Terrain> terrainsInterface = new ArrayList<Terrain>(0);
    List<String> noms = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        chargeList();
        TerrainService ts = new TerrainService();
        terrainsInterface = ts.readAll();
        selectedClub.getItems().add("--");
        selectedTerrain.getItems().add("--");

        noms = clubs.stream().map(Club::getClubName).collect(Collectors.toList());
        if (noms.size() != 0) {

            selectedClub.getItems().addAll(noms);
        } else {
            selectedClub.getItems().add("Acun Club !");
        };

        selectedClub.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour les éléments de nomTerrainInterface en fonction de la sélection

            terrainsInterface.clear();
            selectedTerrain.getItems().clear();

            terrainsInterface = ts.readByClubName(newValue);

            List<String> nomsTerrains = terrainsInterface.stream().map(Terrain::getName).collect(Collectors.toList());
            System.out.println(terrainsInterface);
            selectedTerrain.getItems().add("--");
            selectedTerrain.getItems().addAll(nomsTerrains);

        });
    }

    @FXML
    private void clean(MouseEvent event) {

    }

    void chargeList() {
        ClubService cd = new ClubService();
        clubs = cd.readAll();
    }

    @FXML
    private void addDisponibiliteTerrain(MouseEvent event) {
        DiponibiliteTerrainService dis = new DiponibiliteTerrainService();
        Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain();
        String clubName = selectedClub.getValue().toString();
        String terrainName = selectedTerrain.getValue().toString();
        LocalDate datehoisie = choiceDate.getValue();
        Terrain tt = new Terrain();
        TerrainService ts = new TerrainService();
        if (datehoisie.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Date invalide !");
            alert.showAndWait();
            return;
        }
        if (clubName == null || clubName.length() == 0 || clubName.equals("--")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Club Name invalide !");
            alert.showAndWait();
            return;
        }
        if (terrainName == null || terrainName.length() == 0 || terrainName.equals("--")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Terrain Name invalide !");
            alert.showAndWait();
            return;
        }

        try {
            disponibiliteterrain.setTerrain(tt);
            disponibiliteterrain.setDate(Date.valueOf(datehoisie));

            tt = ts.readByClubNameAndTerrainName(clubName, terrainName);
            disponibiliteterrain.setTerrain(tt);
            dis.insert(disponibiliteterrain);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Date Added Successfully !");
                    alert.showAndWait();

        } catch (Exception e) {
            Logger.getLogger(AddNewDateControllers.class.getName()).log(Level.SEVERE, null, e);

        }

    }

}
