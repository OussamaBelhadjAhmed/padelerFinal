/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.comptes;

/**
 *
 * @author Esprit
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private Button submitButton;

    @FXML
    private void submit() {
        String email = emailField.getText();
        // TODO: Implement the logic to handle the forget password functionality
        // For example, you can send an email with a password reset link to the provided email address
        // You can use JavaMail or a third-party library to send the email
        // Show a confirmation message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Password reset link has been sent to your email address!");
        alert.showAndWait();
    }}

