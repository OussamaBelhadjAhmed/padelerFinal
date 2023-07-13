/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.materiel;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Materiel;
import entite.UserMateriel;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import service.MaterielService;
import service.UserMaterielService;
import service.UserService;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Ghassen
 */


public class ListdesdemandesController implements Initializable {
    @FXML
    private Button approved;
    @FXML
    private Button disapprove;
    @FXML
    private TableColumn<UserMateriel , Integer> approve;

    @FXML
    private TableColumn<UserMateriel, Date> dateDebut;

    @FXML
    private TableColumn<UserMateriel, Date> dateFin;

    @FXML
    private TableColumn<UserMateriel, Integer> idUser;

    @FXML
    private TableColumn<UserMateriel, Integer> idUserMateriel;

    @FXML
    private TableView<UserMateriel> demandetableau;

    @FXML
    private TableColumn<UserMateriel,Integer>idMateriel;

    @FXML
    private Button rafraichir;

    UserMateriel usermateriel=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    showData();

        // end here
    }

    public void showData() {
        UserMaterielService userMaterielService = new UserMaterielService();
        UserService userService = new UserService();
        MaterielService materielService = new MaterielService();

        idUserMateriel.setCellValueFactory(new PropertyValueFactory<>("idUserMateriel"));
        idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        idMateriel.setCellValueFactory(new PropertyValueFactory<>("idMateriel"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        approve.setCellValueFactory(new PropertyValueFactory<>("approve"));

        approve.setCellFactory(column -> new TableCell<UserMateriel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == 0) {
                    setText("In Progress");
                    setStyle("-fx-text-fill: grey;");
                } else if (item == 1) {
                    setText("Approved");
                    setStyle("-fx-text-fill: green;");
                } else if (item == 2) {
                    setText("Disapproved");
                    setStyle("-fx-text-fill: red;");
                } else {
                    setText("");
                    setStyle(""); // Reset the style
                }
            }
        });

        demandetableau.setItems(FXCollections.observableArrayList(userMaterielService.readAll()));

        idUser.setCellFactory(column -> new TableCell<UserMateriel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    UserMateriel userMateriel = getTableView().getItems().get(getIndex());
                    int userId = userMateriel.getIdUser();
                    String userName = userService.readById(userId).getEmail();
                    setText(userName);
                }
            }
        });

        idMateriel.setCellFactory(column -> new TableCell<UserMateriel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    UserMateriel userMateriel = getTableView().getItems().get(getIndex());
                    int materielId = userMateriel.getIdMateriel();
                    String materialName = materielService.readById(materielId).getImage();
                    setText(materialName);
                }
            }
        });
    }

    @FXML
    private void setApproved(ActionEvent event) {
        UserMateriel m = demandetableau.getSelectionModel().getSelectedItem();
        if (m != null) {
            UserMaterielService userMaterielService = new UserMaterielService();
            userMaterielService.approve(m.getIdUserMateriel());
            // Refresh the table after deletion
            showData();

            sendEmail();
            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("rent approved successfully!");
            alert.showAndWait();
        } else {
            System.out.println("No materiel selected");
        }
    }
    @FXML
    private void setDisapprove(ActionEvent event) {
        UserMateriel m = demandetableau.getSelectionModel().getSelectedItem();
        if (m != null) {
            System.out.println(m.getApprove());
            UserMaterielService userMaterielService = new UserMaterielService();
            userMaterielService.disapprove(m.getIdUserMateriel());
            // Refresh the table after deletion
            showData();


            sendEmail();
            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("rent Disapproved successfully!");
            alert.showAndWait();
        } else {
            System.out.println("No materiel selected");
        }
    }
    @FXML
    public void refreshData() {
        // Retrieve the updated list of materiel
        UserMaterielService materielService = new UserMaterielService();
        List<UserMateriel> materielList = materielService.readAll();

        // Update the table's items with the updated list
        ObservableList<UserMateriel> obs = FXCollections.observableArrayList(materielList);
        demandetableau.setItems(obs);
    }
    String sender = "contact.padeler@gmail.com";
    String senderPassword = "gkbdnropuyadyesy";

    String reciever = "ghassenbelkassem@gmail.com";
    String message = "test email ";
    String obj = "object of email";

    private void sendEmail() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Créer une session avec l'authentification
        Session session;
        session = Session.getInstance(properties, new Authenticator() {
            protected javax.mail.PasswordAuthentication  getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(sender, "senderPassword");
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
        } catch (Exception e) {

            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }



}







