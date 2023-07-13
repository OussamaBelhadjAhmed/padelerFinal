/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservation;

import entite.Club;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class ShowClubDetailsController implements Initializable {

    @FXML
    private AnchorPane Pub1Block;
    @FXML
    private Pane PubBlock;
    @FXML
    private Label pubBlockLabel;
    @FXML
    private ImageView image;
    @FXML
    private Button Button_Commenter;
    @FXML
    private Label id_id;
    @FXML
    private Label labelReactions_T;
    public Club clubGlobal = new Club();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AjoutCommentaire(ActionEvent event) {
        System.out.println("selected club : " + clubGlobal.getClubName());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("choiceClubToReserve.fxml"));
            Parent parent = loader.load();

            ChoiceClubToReserveController ccR = loader.getController();
            ccR.setClubPassedFrom(clubGlobal);
            ccR.chargeList();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ShowClubDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setBlock(Club p) {
//        +" \n " +p.getId_Publication()
        clubGlobal = p;
        pubBlockLabel.setText("Club Name : " + p.getClubName());
        labelReactions_T.setText("Club Adress : " + p.getAdresse());
        //labelReactions.setText(p.getReaction() + "");
        try {

            if (p.getImage() != null) {
                String imagePath = p.getImage(); // Chemin d'accès à l'image dans la base de données
                // Charger l'image à partir du chemin d'accès
                Image img = new Image(imagePath);
                image.setImage(img);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
            // image.setImage(null);
        }

        id_id.setText(p.getIdClub() + "");
        id_id.setVisible(false);
    }

}