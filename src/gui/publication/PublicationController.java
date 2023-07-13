/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.publication;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import entite.Publication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import service.PublicationService;
import java.net.URL;
import java.text.ParseException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * FXML Controller class
 *
 * @author safdh
 */
public class PublicationController implements Initializable {

    @FXML
    private Button addImage_importBtn;

    @FXML
    private Button btnpub;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextArea txtpub;
    @FXML
    private ImageView imported_img;
    private Image image;
    private boolean update;
    String query;
    int idPublication;
    private String publishImagePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void addPublicationImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            String imagePath = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 101, 127, false, true);
            imported_img.setImage(image);

            publishImagePath = imagePath; // Stocker le chemin d'accès dans une variable locale
        }
    }

    @FXML
    public void publishPublication(MouseEvent event) throws IOException, ParseException {
        try {
            PublicationService publicationService = new PublicationService();
            Publication publication = new Publication();

            if (update == false) {
                if (txtpub.getText() != null) {
                    publication.setDescription(txtpub.getText());

                    if (publishImagePath != null) { 
                        String imageUrl = new File(publishImagePath).toURI().toString();
                        publication.setImage(imageUrl);
                    }

                    publicationService.insert(publication);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Publication has been successfully created!");
                    alert.showAndWait();

                } else {
                    Alert alerterror = new Alert(Alert.AlertType.ERROR);
                    alerterror.setHeaderText(null);
                    alerterror.setContentText("Champs Vides !");
                    alerterror.showAndWait();
                }
            } else {
                String description = txtpub.getText();
                publication.setDescription(description);
                publication.setIdPublication(idPublication);
                publicationService.update(publication);
            }
        } catch (Exception e) {
            Logger.getLogger(PublicationController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    void setTextField(int idPub, String text) {

        txtpub.setText(text);
        idPublication = idPub;

    }

    /**
     * Initializes the controller class.
     */
    
}
