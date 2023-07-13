package gui.reclamation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nouha
 */
import entite.Reclamation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsDialog extends Stage {

    public DetailsDialog(Reclamation r) {
        setTitle("reclamation Details");
        initModality(Modality.APPLICATION_MODAL);

        Label descriptionLabel = new Label("Description: " + r.getDescription());
        Label dateLabel = new Label("Date: " + r.getDate());
        Label etatLabel = new Label("Etat: " + r.getStatut());
        Label userLabel = new Label("UserId: " + r.getIdUser());

        
        

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> close());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(descriptionLabel, 0, 0, 2, 1);
        gridPane.add(dateLabel, 0, 1, 2, 1);
        gridPane.add(etatLabel, 0, 2, 2, 1);
        gridPane.add(userLabel, 0, 3, 2, 1);
        gridPane.add(closeButton, 0, 7, 2, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
    }
}
