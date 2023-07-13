/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservation;

import entite.Club;
import entite.Disponibiliteterrain;
import entite.Reservation;
import entite.Terrain;
import gui.comptes.SessionContext;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import service.TerrainService;
import java.util.Date;
import java.util.Properties;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import service.DiponibiliteTerrainService;
import service.ClubService;
import service.TerrainService;
import service.UserService;
import service.ReservationService;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class ChoiceClubToReserveController implements Initializable {

    @FXML
    private ChoiceBox<String> selectedClub;
    @FXML
    private ChoiceBox<String> selectedTerrain;
    private List<Terrain> terrainsInterface = new ArrayList<Terrain>(0);
    private List<String> dateDispoTerrain = new ArrayList<String>(0);
    private List<Disponibiliteterrain> dispo = new ArrayList<Disponibiliteterrain>(0);

    private Club clubPassedFrom = new Club();
    @FXML
    private HBox dateDisoEcran;
    @FXML
    private ChoiceBox<java.sql.Date> dateSelectedEcran;
    @FXML
    private HBox dateDisoEcran1;
    @FXML
    private ChoiceBox<String> timeSelectedEcran;

    String sender = "contact.padeler@gmail.com";
    final String senderPassword = "gkbdnropuyadyesy";

    String reciever = "belhadjo167777776@gmail.com";
    //String message = "test email ";
    String obj = "Reservation Confirmation";
    String message = "";
    String userFullName = "";

    public Club getClubPassedFrom() {
        return clubPassedFrom;
    }

    public void setClubPassedFrom(Club clubPassedFrom) {
        this.clubPassedFrom = clubPassedFrom;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TerrainService ts = new TerrainService();

        selectedTerrain.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour les éléments de nomTerrainInterface en fonction de la sélection

            Terrain selTerrain = new Terrain();
            if (selectedTerrain.getValue() != null && selectedTerrain.getValue().length() != 0) {
                selTerrain = ts.readByClubNameAndTerrainName(selectedClub.getValue(), selectedTerrain.getValue());
            }
            LocalDate localDate = LocalDate.now();
            DiponibiliteTerrainService ds = new DiponibiliteTerrainService();

            List<Disponibiliteterrain> dispoList = new ArrayList<>();
            dispo = dispoList;

            dispoList.addAll(ds.readByIdTerrainAndDate(selTerrain.getIdTerrain(), java.sql.Date.valueOf(localDate)));
            dateSelectedEcran.getItems().addAll(dispoList.stream().map(Disponibiliteterrain::getDate).collect(Collectors.toList()));

        });

        dateSelectedEcran.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            timeSelectedEcran.getItems().clear();
            Disponibiliteterrain element = null;
            for (Disponibiliteterrain e : dispo) {
                if (e.getDate().equals(dateSelectedEcran.getValue())) {
                    element = e;
                    break;
                }
            }

            if (element.getTemps1() == 0) {
                timeSelectedEcran.getItems().add("06:30H");
            }
            if (element.getTemps2() == 0) {
                timeSelectedEcran.getItems().add("08:00H");
            }
            if (element.getTemps3() == 0) {
                timeSelectedEcran.getItems().add("09:30H");
            }
            if (element.getTemps4() == 0) {
                timeSelectedEcran.getItems().add("11:00H");
            }
            if (element.getTemps5() == 0) {
                timeSelectedEcran.getItems().add("12:30H");
            }
            if (element.getTemps6() == 0) {
                timeSelectedEcran.getItems().add("14:00H");
            }
            if (element.getTemps7() == 0) {
                timeSelectedEcran.getItems().add("15:30H");
            }
            if (element.getTemps8() == 0) {
                timeSelectedEcran.getItems().add("17:00H");
            }
            if (element.getTemps9() == 0) {
                timeSelectedEcran.getItems().add("18:30H");
            }
            if (element.getTemps10() == 0) {
                timeSelectedEcran.getItems().add("20:00H");
            }
            if (element.getTemps11() == 0) {
                timeSelectedEcran.getItems().add("21:30H");
            }
            if (element.getTemps12() == 0) {
                timeSelectedEcran.getItems().add("23:00H");
            }
            if (element.getTemps13() == 0) {
                timeSelectedEcran.getItems().add("00:30H");
            }
            if (element.getTemps14() == 0) {
                timeSelectedEcran.getItems().add("02:00H");
            }
            if (timeSelectedEcran.getItems().size() == 0) {
                timeSelectedEcran.getItems().add("Acune date dispo pour cette journée");

                timeSelectedEcran.getSelectionModel().select("Acune date dispo pour cette journée");

                timeSelectedEcran.setDisable(true);
            }

        });

    }

    @FXML
    private void clean(MouseEvent event) {

        selectedTerrain.getItems().clear();
        dateSelectedEcran.getItems().clear();
        timeSelectedEcran.getItems().clear();

    }

    public void chargeList() {
        selectedClub.getItems().add(getClubPassedFrom().getClubName());
        selectedClub.getSelectionModel().select(getClubPassedFrom().getClubName());

        TerrainService ts = new TerrainService();
        terrainsInterface = ts.readByClubName(selectedClub.getValue());
        selectedTerrain.getItems().addAll(terrainsInterface.stream().map(Terrain::getName).collect(Collectors.toList()));

    }

    @FXML
    private void reserverTerrain(MouseEvent event) {
        ClubService cs = new ClubService();
        TerrainService ts = new TerrainService();
        DiponibiliteTerrainService dts = new DiponibiliteTerrainService();
        Club c = new Club();
        Terrain tt = new Terrain();
        Reservation rr = new Reservation();
        Disponibiliteterrain dt = new Disponibiliteterrain();
        List<Disponibiliteterrain> dtToUpdate = new ArrayList<Disponibiliteterrain>(0);
        ReservationService resService = new ReservationService();

        if (selectedClub.getValue() != null && selectedClub.getValue().length() != 0) {
            c = cs.readByClubName(selectedClub.getValue());
            rr.setClub(c);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choice club !");
            alert.showAndWait();
            return;
        }
        if (selectedTerrain.getValue() != null && selectedTerrain.getValue().length() != 0) {
            tt = ts.readByClubNameAndTerrainName(c.getClubName(), selectedTerrain.getValue());
            rr.setTerrain(tt);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choice Terrain !");
            alert.showAndWait();
            return;
        }
        if (dateSelectedEcran.getValue() != null) {
            rr.setDate(dateSelectedEcran.getValue());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choice Date !");
            alert.showAndWait();
            return;
        }
        if (timeSelectedEcran.getValue() != null) {
            rr.setTimeReservation(timeSelectedEcran.getValue());
            switch (timeSelectedEcran.getValue()) {
                case "06:30H":
                    dt.setTemps1(1);
                    break;
                case "08:00H":
                    dt.setTemps2(1);
                    break;
                case "09:30H":
                    dt.setTemps3(1);
                    break;
                case "11:00H":
                    dt.setTemps4(1);
                    break;
                case "12:30H":
                    dt.setTemps5(1);
                    break;
                case "14:00H":
                    dt.setTemps6(1);
                    break;
                case "15:30H":
                    dt.setTemps7(1);
                    break;
                case "17:00H":
                    dt.setTemps8(1);
                    break;
                case "18:30H":
                    dt.setTemps9(1);
                    break;
                case "20:00H":
                    dt.setTemps10(1);
                    break;
                case "21:30H":
                    dt.setTemps11(1);
                    break;
                case "23:00H":
                    dt.setTemps12(1);
                    break;
                case "00:30H":
                    dt.setTemps13(1);
                    break;
                case "02:00H":
                    dt.setTemps14(1);
                    break;
            }
            dtToUpdate.addAll(dts.readByIdTerrainAndDate(tt.getIdTerrain(), dateSelectedEcran.getValue()));
            dt.setIdDisponibiliteTerrain(dtToUpdate.get(0).getIdDisponibiliteTerrain());
            dts.updateDisponibiliteTerrain(dt);
            if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                rr.setUser(SessionContext.getInstance().getLoggedInUser());
                resService.inserts(rr);

            } else {
                resService.inserts(rr);
            }
            if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                this.reciever = SessionContext.getInstance().getLoggedInUser().getEmail();
            }
            if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                this.userFullName = SessionContext.getInstance().getLoggedInUser().getFirstName() + " " + SessionContext.getInstance().getLoggedInUser().getLastName();
            }
            sendEmail(rr);
            // generateQRCode(this.message,"");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Rserved successfully !");
            alert.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(events -> alert.close());
            delay.play();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Choice Time !");
            alert.showAndWait();
            return;
        }

    }

    private void sendEmail(Reservation rr) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        this.message = "Reservation confirmée\n"
                + "réservation au nom de : " + this.userFullName + "\n"
                + "Club: " + rr.getClub().getClubName() + "\n"
                + "Terrain: " + rr.getTerrain().getName() + "\n"
                + "Date: " + rr.getDate() + "\n"
                + "Temps: " + rr.getTimeReservation();

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

//     public static void generateQRCode(String text, String filePath) {
//        int size = 250;
//        String fileType = "png";
//        File qrFile = new File("c:/xampp/htdocs/Mobile/");
//
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//
//        try {
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints);
//
//            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//            for (int x = 0; x < size; x++) {
//                for (int y = 0; y < size; y++) {
//                    int grayValue = bitMatrix.get(x, y) ? 0 : 255;
//                    bufferedImage.setRGB(x, y, (grayValue << 16) | (grayValue << 8) | grayValue);
//                }
//            }
//
//            ImageIO.write(bufferedImage, fileType, qrFile);
//            System.out.println("QR Code generated successfully.");
//        } catch (WriterException | IOException e) {
//            System.out.println("Error generating QR Code: " + e.getMessage());
//        }
//    }
}
