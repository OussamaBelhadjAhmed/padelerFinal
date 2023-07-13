package gui.materiel;

import entite.Club;
import entite.Materiel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import service.ClubService;
import service.MaterielService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;


public class AddMaterielController implements Initializable {

    @FXML
    private Button addImage;

    @FXML
    private Button add_btn;

    @FXML
    private ComboBox<String> listClub;

    @FXML
    private ImageView materiel_image;

    @FXML
    private TextField materiel_name;

    private String publishImagePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadClubs();
    }

    @FXML
    public void addMateriel(ActionEvent event) {
        MaterielService materielService = new MaterielService();
        String selectedClubName = listClub.getValue();
        String materielName = materiel_name.getText();

        // Create a new Materiel object with the selected club and materiel name
        Materiel materiel = new Materiel();
        materiel.setName(materielName);

        // Retrieve the club ID based on the selected club name
        ClubService clubService = new ClubService();
        Club selectedClub = clubService.readByClubName(selectedClubName);
        if (selectedClub != null) {
            materiel.setIdClub(selectedClub.getIdClub());
        }

        if (publishImagePath !=null){
            System.out.println("rouge image est :"+publishImagePath);
            materiel.setImage(publishImagePath);


        }

        // Call the insert method in the MaterielService class to add the materiel to the database
        materielService.insert(materiel);

        // Clear the inputs after adding the materiel
        listClub.getSelectionModel().clearSelection();
        materiel_name.clear();
        materiel_image.setImage(null);

        // Show a success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Materiel added successfully!");
        alert.showAndWait();
    }

    private void loadClubs() {
        ClubService clubService = new ClubService();
        List<Club> clubs = clubService.readAll();

        for (Club club : clubs) {
            listClub.getItems().add(club.getClubName());
        }
    }



    @FXML
    private void addImages(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg")
        );

//        // Set the initial directory where the images will be stored
        File initialDirectory = new File("C:\\Users\\"); // Replace with your desired directory
        fileChooser.setInitialDirectory(initialDirectory);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            String targetPath = "C:/xampp/htdocs/mobile/" + selectedFile.getName(); // Construct the target path
            // Copy the selected file to the target path
            try {
                Files.copy(selectedFile.toPath(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                // Store the target path in your data model or wherever you need it
                //txtimage.setText(targetPath);
                //txtimage.setText(selectedFile.getName());
                publishImagePath = "file:/" + targetPath;
                System.out.println("filllllleee "+publishImagePath);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error case when the file copy fails
            }
        }
    }

}
