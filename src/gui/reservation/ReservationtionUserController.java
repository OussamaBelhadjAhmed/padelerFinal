/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.reservation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Club;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import entite.Reservation;
import gui.comptes.SessionContext;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.ClubService;

import service.ReservationService;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class ReservationtionUserController implements Initializable {

    @FXML
    private TableView<Reservation> clubsTable;
    @FXML
    private TableColumn<Reservation, String> editClub;
    @FXML
    private TableColumn<Reservation, String> club;
    @FXML
    private TableColumn<Reservation, String> terrain;
    @FXML
    private TableColumn<Reservation, Date> date;
    ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    Reservation reservation = new Reservation();
    @FXML
    private TableColumn<Reservation, String> time;
    String userFullName = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();

    }

    @FXML
    private void refreshClubTable() {
        ReservationService rs = new ReservationService();
        reservationList.clear();
        clubsTable.getItems().clear();

        try {
            /* reservationList.clear();
            int idUser = 1;
            if (SessionContext.getInstance().getLoggedInUser().getIdUser() + "".length() != 0) {

                idUser = SessionContext.getInstance().getLoggedInUser().getIdUser();
            }
            if (idUser != 0) {
                reservationList.addAll(rs.readAllJoinTerrainAndClub(1));
                clubsTable.setItems(reservationList);
            } else {
             */
            if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0) {
                this.userFullName = SessionContext.getInstance().getLoggedInUser().getFirstName() + " " + SessionContext.getInstance().getLoggedInUser().getLastName();
            }
            if (SessionContext.getInstance().getLoggedInUser().getEmail() != null && SessionContext.getInstance().getLoggedInUser().getEmail().length() != 0 && SessionContext.getInstance().getLoggedInUser().getEmail() != null && !SessionContext.getInstance().getLoggedInUser().getRole().toUpperCase().equals("ADMIN")) {
                reservationList.addAll(rs.readAllByUser(true, SessionContext.getInstance().getLoggedInUser().getIdUser()));
                clubsTable.setItems(reservationList);

            } else {
                reservationList.addAll(rs.readAll());
                clubsTable.setItems(reservationList);
            }

            //  }
        } catch (Exception x) {
            Logger.getLogger(ReservationtionUserController.class.getName()).log(Level.SEVERE, null, x);
        }

    }

    private void loadDate() {

        refreshClubTable();
        ReservationService cs = new ReservationService();

        club.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getClubName()));
        terrain.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTerrain().getName()));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("timeReservation"));

        //user.setCellValueFactory(new PropertyValueFactory<>("user"));
        //emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //add cell of button edit 
        Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>> cellFoctory = (TableColumn<Reservation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reservation, String> cell = new TableCell<Reservation, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView prinnt = new FontAwesomeIconView(FontAwesomeIcon.PRINT);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        prinnt.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        prinnt.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                reservation = clubsTable.getSelectionModel().getSelectedItem();
                                print(event);
                            } catch (Exception ex) {
                                Logger.getLogger(ReservationtionUserController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        HBox managebtn = new HBox(prinnt);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(prinnt, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editClub.setCellFactory(cellFoctory);
        clubsTable.setItems(reservationList);

    }

//    private void print(MouseEvent event) {
//        Printer printer = Printer.getDefaultPrinter();
//        //PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
//        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
//
//        double scaleX = 0.8; // adjust this value as needed
//        double scaleY = 0.8; // adjust this value as needed
//        Scale scale = new Scale(scaleX, scaleY);
//
//        Group printableNode = new Group();
//        printableNode.getTransforms().add(scale);
//
//        // create a new table view
//        TableView<Reservation> printView = new TableView<>();
//        printView.getItems().add(this.reservation);
//        // add the columns to the print view
//        TableColumn<Reservation, String> club = new TableColumn<>("Club");
//        club.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getClubName()));
//
//        TableColumn<Reservation, String> terrain = new TableColumn<>("terrain");
//        terrain.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTerrain().getName()));
//
//        TableColumn<Reservation, Date> date = new TableColumn<>("date");
//        date.setCellValueFactory(new PropertyValueFactory<>("date"));
//        TableColumn<Reservation, Date> timeReservation = new TableColumn<>("Time");
//        timeReservation.setCellValueFactory(new PropertyValueFactory<>("timeReservation"));
//
//        printView.getColumns().addAll(club, terrain, date, timeReservation);
//
//        // customize the print view styles
//        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");
//
//        // set the preferred
//        // customize the print view styles
//        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");
//
//        // set t width of each column
//        club.setPrefWidth(120);
//        terrain.setPrefWidth(120);
//        date.setPrefWidth(120);
//        timeReservation.setPrefWidth(120);
//
//        // set the items and size of the print view
//        printView.setItems(clubsTable.getSelectionModel().getSelectedItems());
//        printView.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
//
//        // create a header and footer for the printed page
//        Text header = new Text("\nConfirmation reservation\n");
//        header.setStyle("-fx-font-family: Arial; -fx-font-size: 24pt;"); // increase font size
//
//        // add the print view, header, and footer to the printable node
//        printableNode.getChildren().addAll(header, printView
//        );
//
//        // set the layout of the header and footer
//        double headerWidth = header.getBoundsInLocal().getWidth();
//        double headerHeight = header.getBoundsInLocal().getHeight();
//
//        header.setLayoutX((pageLayout.getPrintableWidth() - headerWidth) / 2);
//        header.setLayoutY(0); // set to 0 to align with top of printable area
//
//        printView.setLayoutX(0);
//        printView.setLayoutY(headerHeight); // set below header
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        PrinterJob job = PrinterJob.createPrinterJob();
//        if (job != null && job.showPrintDialog(stage)) {
//            boolean success = job.printPage(printableNode);
//            if (success) {
//                job.endJob();
//            }
//        }
//    }
//    
    private void print(MouseEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);

        double scaleX = 0.8;
        double scaleY = 0.8;
        Scale scale = new Scale(scaleX, scaleY);

        Group printableNode = new Group();
        printableNode.getTransforms().add(scale);

        Reservation selectedReservation = clubsTable.getSelectionModel().getSelectedItem();

        // create a new table view
        TableView<Reservation> printView = new TableView<>();
        printView.getItems().add(selectedReservation);

        // add the columns to the print view (similar to the previous example)
        // customize the print view styles (similar to the previous example)
        // set the width of each column (similar to the previous example)
        // set the items and size of the print view (similar to the previous example)
        // create a header and footer for the printed page (similar to the previous example)
        // add the print view, header, and footer to the printable node (similar to the previous example)
        // set the layout of the header and footer (similar to the previous example)
        Text titleText = new Text("Confirmation Reservation");
        titleText.setStyle("-fx-font-family: Arial; -fx-font-size: 24pt; -fx-font-weight: bold;");
        double titleTextWidth = titleText.getBoundsInLocal().getWidth();
        double titleTextHeight = titleText.getBoundsInLocal().getHeight();
        double titleTextOffset = 150; // Ajustez cette valeur pour décaler le titre
        titleText.setLayoutX((pageLayout.getPrintableWidth() - titleTextWidth) / 2 - titleTextOffset);
        titleText.setLayoutY(printView.getLayoutY() + titleTextHeight); // Position it above the print view

        // Add the message to the printable node
        Text messageText = new Text("Reservation confirmée\n"
                + "réservation au nom de : " + this.userFullName + "\n"
                + "Club: " + selectedReservation.getClub().getClubName() + "\n"
                + "Terrain: " + selectedReservation.getTerrain().getName() + "\n"
                + "Date: " + selectedReservation.getDate() + "\n"
                + "Temps: " + selectedReservation.getTimeReservation());
        messageText.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");
        double messageTextHeight = messageText.getBoundsInLocal().getHeight();
        messageText.setLayoutX(20); // Adjust the X position as needed
        messageText.setLayoutY(printView.getLayoutY() + printView.getHeight() + messageTextHeight); // Position it below the print view

        printableNode.getChildren().addAll(titleText, messageText);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(stage)) {
            boolean success = job.printPage(printableNode);
            if (success) {
                job.endJob();
            }
        }
    }

}
