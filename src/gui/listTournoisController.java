package gui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Tournois;
import gui.tournois.CreateTeamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.TournoisService;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.ResourceBundle;

public class listTournoisController implements Initializable {
    @FXML
    private TableColumn<Tournois, Date> col_dateDebut;

    @FXML
    private TableColumn<Tournois, Date> col_dateFin;

    @FXML
    private TableColumn<Tournois, String> col_name;

    @FXML
    private TableColumn<Tournois, String> col_type;

    @FXML
    private TableView<Tournois> table_tournois;
    @FXML
    private TableColumn<Tournois, String> editCol;

    Tournois tournois=null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TournoisService ps=new TournoisService();

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        col_dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        List<Tournois> tournoisList = ps.readAll();
        ObservableList<Tournois> obs = FXCollections.observableArrayList(tournoisList);

        Callback<TableColumn<Tournois, String>, TableCell<Tournois, String>> cellFoctory = (TableColumn<Tournois, String> param) -> {

            final TableCell<Tournois, String> cell = new TableCell<Tournois, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        System.out.println("vide");
                        setGraphic(null);
                        setText(null);

                    } else {
                        System.out.println("not vide");

                        FontAwesomeIconView detailsIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
                        FontAwesomeIconView inscrireIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        detailsIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#008000;"
                        );
                        inscrireIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#0000FF;"
                                        + "-fx-arc-width: 100px"
                        );
                        inscrireIcon.setOnMouseClicked((MouseEvent event) -> {

                            tournois = table_tournois.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("CreateTeam.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(listTournoisController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            CreateTeamController createTeamController = loader.getController();
                            createTeamController.setUpdate(true);
                            createTeamController.setTextField(tournois.getName());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();






                        });



                    HBox managebtn = new HBox(inscrireIcon);
                    managebtn.setStyle("-fx-alignment:center");
                    HBox.setMargin(inscrireIcon, new Insets(2, 2, 0, 3));

                    setGraphic(managebtn);

                    setText(null);

                }
            }

        };

        return cell;
    };
        editCol.setCellFactory(cellFoctory);
        table_tournois.setItems(obs);



        // end here
    }
}
