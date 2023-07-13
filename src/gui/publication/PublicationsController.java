/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.publication;

import entite.Commentaire;
import entite.Publication;
import entite.User;
import gui.comptes.SessionContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import service.CommentaireService;
import service.PublicationService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author safdh
 */
public class PublicationsController implements Initializable {

    @FXML
    private AnchorPane Anchore;
    @FXML
    private AnchorPane anchorPanePubBlock;
    @FXML
    private GridPane gridPanePub;
    @FXML
    private AnchorPane Home;
    @FXML
    private Button refreshButton;
    Connection cnx = DataSource.getInstance().getCnx();

    /**
     * Initializes the controller class.
     */
    PublicationService ps = new PublicationService();
    @FXML
    private Button btnpub;
    @FXML
    private TextArea txtpub;
    @FXML
    private Button addImage_importBtn;
    @FXML
    private ImageView imported_img;
    private Image image;
    private boolean update;

    String query;
    int idPublication;
    private String publishImagePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            insertPublication();
        } catch (IOException ex) {
            Logger.getLogger(PublicationsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setInterface(String location) throws IOException {
        Home.getChildren().clear();
        Home.getChildren().add(FXMLLoader.load(this.getClass().
                getResource("/gui/publication/" + location + ".fxml")));
    }

    public void insertPublication() throws IOException {
        PublicationService pubS = new PublicationService();
        List<Publication> listP = pubS.readAll();
        for (int i = 0; i < listP.size(); i++) {

            FXMLLoader Loader1 = new FXMLLoader();
            Loader1.setLocation(getClass().getResource("/gui/publication/Resultat.fxml"));
            AnchorPane anchorPane = Loader1.load();
            ResultatController res = Loader1.getController();
            res.setBlock(listP.get(i));
            try {
                if (anchorPane != null) {
                    gridPanePub.add(anchorPane, 0, i);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } else {
                    System.err.println("Failed to load Resultat.fxml for publication: " + listP.get(i));
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

    @FXML
    private void refreshTable(MouseEvent event) {
        try {
            // Appel de la méthode insertPublication() pour mettre à jour la liste des publications
            insertPublication();
        } catch (IOException ex) {
            Logger.getLogger(PublicationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    void setTextField(int idPub, String text) {

        txtpub.setText(text);
        idPublication = idPub;

    }

    String sender = "contact.padeler@gmail.com";
    final String senderPassword = "gkbdnropuyadyesy";

    String reciever = "saf.dhaifallah@gmail.com";
    String message = "Bonjour ,/n Vous avez ajouté une nouvelle publication sur Padeler";
    String obj = "Nouvelle publication";

    private void sendEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Créer une session avec l'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, senderPassword);
            }
        });

        try {
            // Créer le message
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(sender));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciever));
            emailMessage.setSubject(obj);
            emailMessage.setText(message);

            // Envoyer le message
            Transport.send(emailMessage);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }

    @FXML
    private void publishPublication(MouseEvent event) throws IOException, ParseException {
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

                    if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                        publication.setUser(SessionContext.getInstance().getLoggedInUser());
                    }
                    publicationService.insert(publication);
                    if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                        this.reciever = SessionContext.getInstance().getLoggedInUser().getEmail();
                    }

                    sendEmail();

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

    @FXML
    private void addPublicationImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            String targetPath = "C:/xampp/htdocs/mobile/" + selectedFile.getName();

            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);

                // Load the image into an Image object
                Image image = new Image(selectedFile.toURI().toString(), 101, 127, false, true);

                // Set the image to the ImageView
                imported_img.setImage(image);

                publishImagePath = targetPath;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error case when the file copy fails
            }
        }
    }

    @FXML
    private void handleRefreshButtonAction(ActionEvent event) {
        try {
            // Call the insertPublication() method to refresh the publication list
            insertPublication();

            // Clear the text in the txtpub TextArea
            txtpub.clear();

        } catch (IOException ex) {
            Logger.getLogger(PublicationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
