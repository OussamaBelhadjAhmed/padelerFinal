/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nouha
 */
package gui.reclamation;

import entite.Reclamation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.ReclamationService;

public class UpdateDialog extends Stage {

    private final TextField reponseField;
    private final Reclamation reclamation;
    private boolean success;

    public UpdateDialog(Reclamation reclamation) {
        this.reclamation = reclamation;

        setTitle("Update Reclamation");
        initModality(Modality.APPLICATION_MODAL);

        reponseField = new TextField(reclamation.getReponse());
        Label reponseLabel = new Label("Reponse:");

        Button saveButton = new Button("Envoyer");
        saveButton.setOnAction(event -> {
            updateReclamation();
            sendEmailAndSetStatus();
            success = true;
            close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            success = false;
            close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(reponseLabel, 0, 0);
        gridPane.add(reponseField, 1, 0);
        gridPane.add(saveButton, 0, 9);
        gridPane.add(cancelButton, 1, 9);

        Scene scene = new Scene(gridPane);
        setScene(scene);
    }

    private void updateReclamation() {
        // Mettez à jour l'objet Reclamation avec les nouvelles valeurs
        reclamation.setReponse(reponseField.getText());

        // Effectuez la mise à jour de la base de données en utilisant ReclamationService
        ReclamationService reclamationService = new ReclamationService();
        reclamationService.update(reclamation);
    }

    private void sendEmailAndSetStatus() {
        String recipientEmail = reclamation.getEmail();
        String subject = "Réponse à votre réclamation";
        String content = reclamation.getReponse();
        System.out.println("email: "+recipientEmail);
        EmailService emailService = new EmailService();
        emailService.sendEmail(recipientEmail, subject, content);

        reclamation.setStatut("Traité");
        ReclamationService reclamationService = new ReclamationService();
        reclamationService.update(reclamation);
    }

    public boolean isSuccess() {
        return success;
    }
}
