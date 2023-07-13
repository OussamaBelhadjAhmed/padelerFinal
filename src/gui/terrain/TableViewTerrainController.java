/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.terrain;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Club;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.TerrainService;
import entite.Terrain;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.util.Callback;
import service.ClubService;

/**
 * FXML Controller class
 *
 * @author oussama.hadjahmed
 */
public class TableViewTerrainController implements Initializable {

    @FXML
    private TableColumn<Terrain, String> name;
    @FXML
    private TableView<Terrain> terrainsTable;
    private TableColumn<Terrain, Integer> idTerrain;
    @FXML
    private TableColumn<Terrain, Integer> status;
    @FXML
    private TableColumn<Terrain, String> clubName;
    @FXML
    private TableColumn<Terrain, String> editTerrain;
    Terrain terrain = null;
    ObservableList<Terrain> terrainList = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> nomClubInterface;
    private List<Club> clubsInterface = new ArrayList<Club>(0);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
        chargeList();
        List<String> noms = new ArrayList<>();
        noms = clubsInterface.stream().map(Club::getClubName).collect(Collectors.toList());
        nomClubInterface.getItems().add(0, "All");
        if (noms.size() != 0) {
            nomClubInterface.getItems().addAll(noms);
        } else {
            nomClubInterface.getItems().add("Acun Club !");
        }

    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddViewTerrain(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("addTerrain.fxml"));
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
    private void refreshTerrainTable() {
        TerrainService ts = new TerrainService();
        String clubNamess = nomClubInterface.getValue();

        try {

            if (clubNamess != null && clubNamess.length() != 0 && !clubNamess.equals("All")) {
                terrainList.clear();
                System.out.println("clubNamess : " + clubNamess);
                terrainList.addAll(ts.readByClubName(clubNamess));
                terrainsTable.setItems(terrainList);

            } else {
                terrainList.clear();
                terrainList.addAll(ts.readAllWitClubName());
                terrainsTable.setItems(terrainList);

            }

        } catch (Exception e) {
            Logger.getLogger(TableViewTerrainController.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    private void loadDate() {

        refreshTerrainTable();
        TerrainService ts = new TerrainService();

        //idTerrain.setCellValueFactory(new PropertyValueFactory<>("idTerrain"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        clubName.setCellValueFactory(new PropertyValueFactory<>("club"));
        System.out.print("test list :" + terrainList);

        //user.setCellValueFactory(new PropertyValueFactory<>("user"));
        //emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //add cell of button edit 
        Callback<TableColumn<Terrain, String>, TableCell<Terrain, String>> cellFoctory = (TableColumn<Terrain, String> param) -> {
            // make cell containing buttons
            final TableCell<Terrain, String> cell = new TableCell<Terrain, String>() {
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

                                terrain = terrainsTable.getSelectionModel().getSelectedItem();
                                ts.delete(terrain.getIdTerrain());

                                refreshTerrainTable();

                            } catch (Exception ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            terrain = terrainsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("addTerrain.fxml"));
                            try {
                                loader.load();

                            } catch (IOException ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddTerrainController addTerrainController = loader.getController();
                            addTerrainController.setUpdate(true);
                            addTerrainController.setTextField(terrain.getIdTerrain(), terrain.getName(), terrain.getStatus(), terrain.getClub().getClubName());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            refreshTerrainTable();
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
        editTerrain.setCellFactory(cellFoctory);
        terrainsTable.setItems(terrainList);

    }

    void chargeList() {
        ClubService cd = new ClubService();
        // Club cs = new Club();
        // cs.setName("All");
        clubsInterface = cd.readAll();
        //clubsInterface.add(cs);

    }

    @FXML
    private void getByClubName(MouseEvent event) {
        refreshTerrainTable();

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
        TableView<Terrain> printView = new TableView<>();
        printView.setItems(terrainsTable.getItems());

        // add the columns to the print view
        TableColumn<Terrain, Integer> idTerrain = new TableColumn<>("idTerrain");
        idTerrain.setCellValueFactory(new PropertyValueFactory<>("idTerrain"));

        TableColumn<Terrain, String> name = new TableColumn<>("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Terrain, Integer> status = new TableColumn<>("status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Terrain, String> club = new TableColumn<>("club");
        club.setCellValueFactory(new PropertyValueFactory<>("club"));

        printView.getColumns().addAll(idTerrain, name, status, club);

        // customize the print view styles
        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");

        // set the preferred
        // customize the print view styles
        printView.setStyle("-fx-font-family: Arial; -fx-font-size: 12pt;");

        // set t width of each column
        idTerrain.setPrefWidth(70);
        name.setPrefWidth(120);
        status.setPrefWidth(80);
        club.setPrefWidth(80);

        // set the items and size of the print view
        printView.setItems(terrainsTable.getItems());
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
