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
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteDialog extends Stage {

    private boolean confirmed = false;

    public DeleteDialog(Reclamation reclamation) {
        setTitle("Delete Reclamation");
        initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label("Are you sure you want to delete the Reclamation: ");

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            confirmed = true;
            close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> close());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(messageLabel, 0, 0, 2, 1);
        gridPane.add(confirmButton, 0, 1);
        gridPane.add(cancelButton, 1, 1);

        Scene scene = new Scene(gridPane);
        setScene(scene);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
