package gui.reservation;

import entite.Club;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import service.ClubService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListClubForUserController implements Initializable {

    @FXML
    private AnchorPane Anchore;
    @FXML
    private Button btnpub;
    @FXML
    private AnchorPane anchorPanePubBlock;
    @FXML
    private GridPane gridPanePub;
    @FXML
    private TextArea txtpub;
    @FXML
    private Button addImage_importBtn;
    @FXML
    private ImageView imported_img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            insertPublication();
        } catch (IOException ex) {
            Logger.getLogger(ListClubForUsetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void publishPublication(MouseEvent event) {
    }

    @FXML
    private void addPublicationImage(ActionEvent event) {
    }


    @FXML
    private void refreshTable(MouseEvent event) {
    }

    public void insertPublication() throws IOException {
        ClubService pubS = new ClubService();
        List<Club> listC = pubS.readAll();
        for (int i = 0; i < listC.size(); i++) {

            FXMLLoader Loader1 = new FXMLLoader();
            Loader1.setLocation(getClass().getResource("/gui/reservation/showClubDetails.fxml"));
            AnchorPane anchorPane = Loader1.load();
            ShowClubDetailsController res = Loader1.getController();
            res.setBlock(listC.get(i));
            try {
                if (anchorPane != null) {
                    gridPanePub.add(anchorPane, 0, i);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } else {
                    System.err.println("Failed to load showClubDetails.fxml for Clubs: " );
                }
            } catch (Exception e) {
                System.err.println("Error inserting publication: " + e.getMessage());
                e.printStackTrace();
            }

//            gridPanePub.add(anchorPane, 0, i);
            gridPanePub.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPanePub.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridPanePub.setMaxWidth(Region.USE_PREF_SIZE);
            gridPanePub.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPanePub.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPanePub.setMaxHeight(Region.USE_PREF_SIZE);
            GridPane.setMargin(anchorPane, new Insets(10));

        }

    }

}
