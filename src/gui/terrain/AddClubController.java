/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.terrain;

import java.io.File;
import java.io.IOException;

import entite.Club;
import gui.comptes.SessionContext;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.ClubService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class AddClubController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField adresse;

    private boolean update;
    int idClub;
    String query;

    String sender = "contact.padeler@gmail.com";
    final String senderPassword = "gkbdnropuyadyesy";

    String reciever = "saf.dhaifallah@gmail.com";
    String message = "un club a ete ajouté";
    String obj = "object of email";

    private String publishImagePath;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void addClub(MouseEvent event) {

        try {
            ClubService cs = new ClubService();
            Club club = new Club();
            if (update == false) {
                if (name.getText() != null && adresse.getText() != null) {
                    String nom = name.getText();
                    club.setName(nom);
                    String adr = adresse.getText();
                    club.setAdresse(adr);
                    if (publishImagePath != null && publishImagePath.length() != 0) {
                        club.setImage(publishImagePath);

                    }
                    if (SessionContext.getInstance().getLoggedInUser().getEmail()!=null && SessionContext.getInstance().getLoggedInUser().getEmail().length()!=0 ){
                        this.reciever= SessionContext.getInstance().getLoggedInUser().getEmail() ; 
                    }
                    sendEmail();
                    cs.insert(club);

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Champs Vides !");
                    alert.showAndWait();
                }

            } else {
                String nom = name.getText();
                club.setName(nom);
                String adr = adresse.getText();
                club.setAdresse(adr);
                club.setIdClub(idClub);
                cs.update(club);
            }

        } catch (Exception x) {
            Logger.getLogger(AddClubController.class.getName()).log(Level.SEVERE, null, x);
        }
    }

    @FXML
    private void clean(MouseEvent event) {
        name.setText(null);
        adresse.setText(null);
    }

    void setTextField(int id, String nom, String adress) {

        idClub = id;
        name.setText(nom);
        adresse.setText(adress);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

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
    private void addImage(MouseEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );

//        // Set the initial directory where the images will be stored
        File initialDirectory = new File("C:\\"); // Replace with your desired directory
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            String targetPath = "C:/xampp/htdocs/Mobile/" + selectedFile.getName(); // Construct the target path
            // Copy the selected file to the target path
            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                // Store the target path in your data model or wherever you need it
                //txtimage.setText(targetPath);
                //txtimage.setText(selectedFile.getName());
                publishImagePath = "file:/" + targetPath;

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error case when the file copy fails
            }
        }

    }

}
