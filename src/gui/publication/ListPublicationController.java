/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.publication;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import gui.terrain.TableViewClubController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import service.ClubService;
import service.PublicationService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author safdh
 */
public class ListPublicationController implements Initializable {

    @FXML
    private TableView<Publication> table_pub;
    @FXML
    private TableColumn<Publication, String> col_user;
    @FXML
    private TableColumn<Publication, String> col_pub;
    @FXML
    private TableColumn<Publication, Date> col_date;
    @FXML
    private TableColumn<Publication, String> col_react;
    @FXML
    private TableColumn<Publication, String> editCol;
    @FXML
    private TableColumn<Publication, String> col_img;
    Publication publication = null;
    ObservableList<Publication> Listpub = FXCollections.observableArrayList();
    private PublicationService publicationService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        publicationService = new PublicationService();
        DataSource ds1 = DataSource.getInstance();

        loadDate();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddViewPublication(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Publication.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ListPublicationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDate() {
        refreshTable();
        PublicationService ps = new PublicationService();

        col_pub.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_img.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_react.setCellValueFactory(new PropertyValueFactory<>("reaction"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("datePub"));
        col_user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getFirstName()));
        Callback<TableColumn<Publication, String>, TableCell<Publication, String>> cellFoctory = (TableColumn<Publication, String> param) -> {
            // make cell containing buttons
            final TableCell<Publication, String> cell = new TableCell<Publication, String>() {
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

                                publication = table_pub.getSelectionModel().getSelectedItem();
                                ps.delete(publication.getIdPublication());

                                refreshTable();

                            } catch (Exception ex) {
                                Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            publication = table_pub.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("Publication.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ListPublicationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            PublicationController addpub = loader.getController();
                            addpub.setUpdate(true);
                            addpub.setTextField(publication.getIdPublication(), publication.getDescription());
//                            addpub.setPublication(publication);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            refreshTable();
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

        editCol.setCellFactory(cellFoctory);
        table_pub.setItems(Listpub);
    }

    @FXML
    private void refreshTable() {
        try {
            Listpub.clear();
            Listpub.addAll(publicationService.readAll());
            table_pub.setItems(Listpub);
        } catch (Exception e) {
            Logger.getLogger(ListPublicationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
