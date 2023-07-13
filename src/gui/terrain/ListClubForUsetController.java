/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.terrain;

import entite.Club;




import java.io.IOException;
import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.reservation.ShowClubDetailsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import service.ClubService;
import service.CommentaireService;
import service.PublicationService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class ListClubForUsetController implements Initializable {

    @FXML
    private AnchorPane Anchore;
    @FXML
    private Button btnpub;
    @FXML
    private AnchorPane anchorPanePubBlock;
    @FXML
    private GridPane gridPanePub;
    @FXML
    private TextArea txtpub;
    @FXML
    private Button addImage_importBtn;
    @FXML
    private ImageView imported_img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            insertPublication();
        } catch (IOException ex) {
            Logger.getLogger(ListClubForUsetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void publishPublication(MouseEvent event) {
    }

    @FXML
    private void addPublicationImage(ActionEvent event) {
    }

    @FXML
    private void refreshTable(MouseEvent event) {
    }
    
    public void insertPublication() throws IOException {
        ClubService pubS = new ClubService();
        List<Club> listC = pubS.readAll();
        for (int i = 0; i < listC.size(); i++) {

            FXMLLoader Loader1 = new FXMLLoader();
            Loader1.setLocation(getClass().getResource("/GUI/showClubDetails.fxml"));
            AnchorPane anchorPane = Loader1.load();
            ShowClubDetailsController res = Loader1.getController();
            res.setBlock(listC.get(i));
            try {
                if (anchorPane != null) {
                    gridPanePub.add(anchorPane, 0, i);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } else {
                    System.err.println("Failed to load showClubDetails.fxml for Clubs: " );
                }
            } catch (Exception e) {
                System.err.println("Error inserting publication: " + e.getMessage());
                e.printStackTrace();
            }

//            gridPanePub.add(anchorPane, 0, i);
            gridPanePub.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPanePub.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridPanePub.setMaxWidth(Region.USE_PREF_SIZE);
            gridPanePub.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPanePub.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPanePub.setMaxHeight(Region.USE_PREF_SIZE);
            GridPane.setMargin(anchorPane, new Insets(10));

        }

    }
    
}
