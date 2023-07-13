/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.terrain;

import entite.Club;

import service.ClubService;
import utils.DataSource;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class TableViewClubController implements Initializable {

    @FXML
    private TableView<Club> clubsTable;
    @FXML
    private TableColumn<Club, String> name;
    @FXML
    private TableColumn<Club, String> adresse;
    @FXML
    private TableColumn<Club, String> user;
    @FXML
    private TableColumn<Club, String> editClub;

    Club club = null;
    ObservableList<Club> clubList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataSource ds1 = DataSource.getInstance();

        loadDate();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddViewClub(MouseEvent event) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("addClub.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void refreshClubTable() {

        ClubService cs = new ClubService();

        try {
            clubList.clear();
            clubList.addAll(cs.readAll());
            clubsTable.setItems(clubList);

        } catch (Exception x) {
            Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, x);
        }

    }

    private void loadDate() {

        refreshClubTable();
        ClubService cs = new ClubService();

        //idClub.setCellValueFactory(new PropertyValueFactory<>("idClub"));
        name.setCellValueFactory(new PropertyValueFactory<>("clubName"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        //user.setCellValueFactory(new PropertyValueFactory<>("user"));
        //emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //add cell of button edit 
        Callback<TableColumn<Club, String>, TableCell<Club, String>> cellFoctory = (TableColumn<Club, String> param) -> {
            // make cell containing buttons
            final TableCell<Club, String> cell = new TableCell<Club, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {

                                club = clubsTable.getSelectionModel().getSelectedItem();
                                cs.delete(club.getIdClub());

                                refreshClubTable();

                            } catch (Exception ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            club = clubsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addClub.fxml"));
                            try {
                                loader.load();

                            } catch (IOException ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddClubController addClubController = loader.getController();
                            addClubController.setUpdate(true);
                            addClubController.setTextField(club.getIdClub(), club.getClubName(), club.getAdresse());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            refreshClubTable();
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editClub.setCellFactory(cellFoctory);
        clubsTable.setItems(clubList);

    }

    @FXML
    private void print(MouseEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        //PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);

        double scaleX = 0.8; // adjust this value as needed
        double scaleY = 0.8; // adjust this value as needed
        Scale scale = new Scale(scaleX, scaleY);

        Group printableNode = new Group();
        printableNode.getTransforms().add(scale);

        // create a new table view
        TableView<Club> printView = new TableView<>();
        printView.setItems(clubsTable.getItems());

        // add the columns to the print view
        TableColumn<Club, Integer> idClub = new TableColumn<>("idClub");
        idClub.setCellValueFactory(new PropertyValueFactory<>("idClub"));

        TableColumn<Club, String> name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Club, String> adresse = new TableColumn<>("adresse");
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        printView.getColumns().addAll(idClub, name, adresse);

        // customize the print view styles
        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");

        // set the preferred
        // customize the print view styles
        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");

        // set t width of each column
        idClub.setPrefWidth(70);
        name.setPrefWidth(120);
        adresse.setPrefWidth(80);

        // set the items and size of the print view
        printView.setItems(clubsTable.getItems());
        printView.setPrefSize(pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());

        // create a header and footer for the printed page
        Text header = new Text("\nLa liste des clubs\n");
        header.setStyle("-fx-font-family: Arial; -fx-font-size: 24pt;"); // increase font size

        // add the print view, header, and footer to the printable node
        printableNode.getChildren().addAll(header, printView
        );

        // set the layout of the header and footer
        double headerWidth = header.getBoundsInLocal().getWidth();
        double headerHeight = header.getBoundsInLocal().getHeight();

        header.setLayoutX((pageLayout.getPrintableWidth() - headerWidth) / 2);
        header.setLayoutY(0); // set to 0 to align with top of printable area

        printView.setLayoutX(0);
        printView.setLayoutY(headerHeight); // set below header

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
