/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.materiel;

import entite.Materiel;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import entite.Publication;
import entite.User;
import entite.UserMateriel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import service.MaterielService;

/**
 * FXML Controller class
 *
 * @author Ghassen
 */
public class MaterielController implements Initializable {
    @FXML
    private DatePicker col_dateDebut;

    @FXML
    private DatePicker col_dateFin;

    @FXML
    private ImageView image_view;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Text name;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    @FXML
    private Button valider;

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
    MaterielService ps=new MaterielService();
    Materiel materiel = new Materiel();
    materiel=ps.readById(3);

       name.setText(materiel.getName());
        System.out.println(materiel.getName());
        System.out.println(materiel.getImage());
       image_view.setImage(new Image(materiel.getImage()));
       // System.out.println(image_view);
    }*/
    private List<Materiel> materielList;
    private int currentIndex;
    private int currentId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaterielService ps = new MaterielService();
        materielList = ps.readAll();
        currentIndex = 0;
        showMateriel(currentIndex);

        // Set up DayCellFactory for the date debut DatePicker
        col_dateDebut.setDayCellFactory(picker -> new DatePickerCell());

        // Set up DayCellFactory for the date fin DatePicker
        col_dateFin.setDayCellFactory(picker -> new DatePickerCell());

        // Set the default value of date debut and date fin to today's date
        col_dateDebut.setValue(LocalDate.now());
        col_dateFin.setValue(LocalDate.now());
    }

    private class DatePickerCell extends javafx.scene.control.DateCell {
        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            setDisable(item.isBefore(LocalDate.now()));
        }
    }



    @FXML
    private void handleNext(ActionEvent event) {
        currentIndex++;
        if (currentIndex >= materielList.size()) {
            currentIndex = 0;
        }

        showMateriel(currentIndex);
    }

    @FXML
    private void handlePrevious(ActionEvent event) {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = materielList.size() - 1;
        }

        showMateriel(currentIndex);
    }

    private void showMateriel(int index) {
        Materiel materiel = materielList.get(index);

        name.setText(materiel.getName());
        if (materiel.getImage() != null) {
            String imagePath = materiel.getImage();
            System.out.print("image : "+imagePath);
// Chemin d'accès à l'image dans la base de données
            // Charger l'image à partir du chemin d'accès
            Image img = new Image(imagePath);
            image_view.setImage(img);
        }
    }


    @FXML
    private void louer() {
        MaterielService materielService = new MaterielService();
        int idUser = 5; // Example user ID, replace it with your actual user ID
        int idMateriel = materielList.get(currentIndex).getIdMateriel();

        Materiel materiel = materielService.readById(idMateriel);

        LocalDate dateDebut = col_dateDebut.getValue();
        LocalDate dateFin = col_dateFin.getValue();

        java.sql.Date sqlDateDebut = java.sql.Date.valueOf(dateDebut);
        java.sql.Date sqlDateFin = java.sql.Date.valueOf(dateFin);

        if (materielService.isMaterielAvailable(materiel, sqlDateDebut, sqlDateFin)) {
            // The materiel is available, proceed with the renting process
            UserMateriel userMateriel = new UserMateriel(idUser, idMateriel, sqlDateDebut, sqlDateFin, 0);
            materielService.louerMateriel(materiel, userMateriel);
        } else {
            // The materiel is not available in the selected date range
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Materiel Not Available");
            alert.setHeaderText(null);
            alert.setContentText("The selected materiel is not available in the specified date range.");
            alert.showAndWait();
        }
    }



}
/**
 * Initializes the controller class.
 */
  

