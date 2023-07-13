package gui.materiel;

import entite.Materiel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.terrain.TableViewClubController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ClubService;
import service.MaterielService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ListeMaterielController implements Initializable {

    @FXML
    private TableColumn<Materiel, Integer> idClub;

    @FXML
    private TableColumn<Materiel, Integer> idMateriel;



    @FXML
    private TableColumn<Materiel, String> image;

    @FXML
    private TableColumn<Materiel, String> name;


    @FXML
    private TableView<Materiel> materieltableau;
    @FXML
    private Button insert;
    @FXML
    private Button delete_btn;

    Materiel materiel=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showListMateriel();
        selectedMateriel();
        // end here
    }
    public void showListMateriel() {
        MaterielService materielService = new MaterielService();

        idMateriel.setCellValueFactory(new PropertyValueFactory<>("idMateriel"));
        idClub.setCellValueFactory(new PropertyValueFactory<>("idClub"));

        // Set the cell value factory for idClub column
        idClub.setCellFactory(column -> new TableCell<Materiel, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    Materiel materiel = getTableView().getItems().get(getIndex());
                    int clubId = materiel.getIdClub();
                    ClubService clubService = new ClubService();
                    String clubName = clubService.readById(clubId).getClubName(); // Replace this method with your logic to get the club name by ID
                    setText(clubName);
                }
            }
        });

        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        image.setCellFactory(column -> new TableCell<Materiel, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    // Create an ImageView to display the image
                    ImageView imageView = new ImageView(new Image(item));
                    imageView.setFitWidth(50); // Set the desired width of the image
                    imageView.setFitHeight(50); // Set the desired height of the image
                    setGraphic(imageView);
                }
            }
        });

        List<Materiel> materielList = materielService.readAll();
        ObservableList<Materiel> obs = FXCollections.observableArrayList(materielList);

        materieltableau.setItems(obs);
    }



    @FXML
    private void addViewMateriel(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("addMateriel.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViewClubController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection connexion;



    public void selectedMateriel() {
        Materiel m = materieltableau.getSelectionModel().getSelectedItem();
        if (m != null) {
            materiel = m;
        }
    }

    @FXML
    private void deleteMateriel(ActionEvent event) {
        Materiel m = materieltableau.getSelectionModel().getSelectedItem();
        if (m != null) {
            MaterielService materielService = new MaterielService();
            materielService.delete(m.getIdMateriel());

            // Refresh the table after deletion
            showListMateriel();

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Materiel deleted successfully!");
            alert.showAndWait();
        } else {
            System.out.println("No materiel selected");
        }
    }
    @FXML
    public void refreshData() {
        // Retrieve the updated list of materiel
        MaterielService materielService = new MaterielService();
        List<Materiel> materielList = materielService.readAll();

        // Update the table's items with the updated list
        ObservableList<Materiel> obs = FXCollections.observableArrayList(materielList);
        materieltableau.setItems(obs);
    }


}
