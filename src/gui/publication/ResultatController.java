/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.publication;

import entite.Commentaire;
import java.net.URL;
import java.util.ResourceBundle;

import entite.Publication;
import entite.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import service.CommentaireService;
import service.PublicationService;
import service.UserService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author wiemhjiri
 */
public class ResultatController implements Initializable {

    Connection cnx = DataSource.getInstance().getCnx();

    private TextField comment;

    @FXML
    private ImageView image;

    private boolean update;

    @FXML
    private Button Like;
    @FXML
    private Button Dislike;
    @FXML
    private AnchorPane AnchorPaneBlockPub;
    @FXML
    private AnchorPane Pub1Block;
    @FXML
    private Pane PubBlock;
    @FXML
    private Label pubBlockLabel;
    @FXML
    private Label id_id;
    @FXML
    private Label labelReactions_T;
    @FXML
    private Label labelReactions;
    private List<Publication> PubList;
    private String baseUrl = DataSource.getInstance().getBaseUrl();
    @FXML
    private Button Button_Commenter;
    @FXML
    private TextField text_Commenter;
    int IdComment;
    private Label commentBlockLabel;
    @FXML
    private AnchorPane anchorPaneCommentairesBlock;
    @FXML
    private GridPane gridPaneComms;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            insertCommentaires();
            

        } catch (IOException ex) {
            Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBlock(Publication p) {
        pubBlockLabel.setText(p.getDescription());

        labelReactions.setText(p.getReaction() + "");
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

        id_id.setText(p.getIdPublication() + "");
        id_id.setVisible(false);
        labelReactions_T.setText("Réactions: ");
    }

    public void setBlockComment(Commentaire c) {
        commentBlockLabel.setText(c.getDescription());

    }

    private void insertCommentaires() throws IOException {
        CommentaireService comS = new CommentaireService();
        List<Commentaire> listC = comS.readAll();
        for (int i = 0; i < listC.size(); i++) {

            FXMLLoader Loader1 = new FXMLLoader();
            Loader1.setLocation(getClass().getResource("/gui/publication/Comment.fxml"));
            AnchorPane anchorPane = Loader1.load();
            CommfxmlController itemController = Loader1.getController();
            itemController.setBlock(listC.get(i));
            try {
                if (anchorPane != null) {
                    gridPaneComms.add(anchorPane, 0, i);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } else {
                    System.err.println("Failed to load Comment.fxml for commentaire: " + listC.get(i));
                }
            } catch (Exception e) {
                System.err.println("Error inserting commentaire: " + e.getMessage());
                e.printStackTrace();
            }
//            gridPaneComms.add(anchorPane, 0, i);
            gridPaneComms.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPaneComms.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridPaneComms.setMaxWidth(Region.USE_PREF_SIZE);
            //set grid height
            gridPaneComms.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPaneComms.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPaneComms.setMaxHeight(Region.USE_PREF_SIZE);
            GridPane.setMargin(anchorPane, new Insets(10));

        }
    }

    @FXML
    private void LikeButton(ActionEvent event) {
        PreparedStatement pt;
        Publication p = new Publication();
        int id_pub1 = Integer.parseInt(id_id.getText());
        System.out.println(id_pub1);
        p.setIdPublication(id_pub1);
        int nbr_Reactions_Label = Integer.parseInt(labelReactions.getText());
        p.setReaction(nbr_Reactions_Label);
        nbr_Reactions_Label++;
        try {
            pt = cnx.prepareStatement("UPDATE publication SET Reaction=? where `idPublication` = ?");
            pt.setInt(1, p.getReaction() + 1);
            pt.setInt(2, p.getIdPublication());
            pt.executeUpdate();
            System.err.println("publication Updated Successfully");
            labelReactions.setText("");
            labelReactions.setText(Integer.toString(nbr_Reactions_Label));

        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        Like.setDisable(true);

    }

    @FXML
    private void DislikeButton(ActionEvent event) {

        PreparedStatement pt;
        Publication p = new Publication();
        int id_pub1 = Integer.parseInt(id_id.getText());
        System.out.println(id_pub1);
        p.setIdPublication(id_pub1);
        int nbr_Reactions_Label = Integer.parseInt(labelReactions.getText());
        p.setReaction(nbr_Reactions_Label);
        nbr_Reactions_Label++;
        try {
            pt = cnx.prepareStatement("UPDATE publication SET Reaction=? where `idPublication` = ?");
            pt.setInt(1, p.getReaction() - 1);
            pt.setInt(2, p.getIdPublication());
            pt.executeUpdate();
            System.err.println("publication Updated Successfully");
            labelReactions.setText("");
            labelReactions.setText(Integer.toString(nbr_Reactions_Label));

        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        Like.setDisable(true);
    }

    private void DeleteButton(ActionEvent event) {
        PublicationService ps = new PublicationService();
        int id = Integer.parseInt(id_id.getText());
        ps.delete(id);

    }

    @FXML
    private void AjoutCommentaire(ActionEvent event) throws IOException {
        try {
            CommentaireService comS = new CommentaireService();
            Commentaire c = new Commentaire();
         
            if (update == false) {
                if (text_Commenter.getText() != null) {
                    PublicationService ps = new PublicationService();
                    Publication p = new Publication();
                    int id_pub1 = Integer.parseInt(id_id.getText());
                    

                    c.setPub(ps.readById(id_pub1));
            

                    c.setDescription(text_Commenter.getText());

                    comS.insert(c);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Comment has been successfully added");
                    alert.showAndWait();

                } else {
                    Alert alerterror = new Alert(Alert.AlertType.ERROR);
                    alerterror.setHeaderText(null);
                    alerterror.setContentText("Champs Vides !");
                    alerterror.showAndWait();
                }
            } else {
                System.err.println("ffffffffffff");

                String description = text_Commenter.getText();
                c.setDescription(description);
                c.setIdComment(IdComment);
                comS.update(c);
            }
        } catch (Exception e) {
            Logger.getLogger(ResultatController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
