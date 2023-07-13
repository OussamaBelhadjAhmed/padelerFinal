package gui.comptes;

import entite.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private Button loginbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField tel;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private UserService userService= new UserService();
    public void signUp() {
        String firstNameText = firstName.getText().trim();
        String lastNameText = lastName.getText().trim();
        String emailText = email.getText().trim();
        String telText = tel.getText().trim();
        String passwordText = password.getText().trim();


        if (firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty() || telText.isEmpty() || passwordText.isEmpty()) {
            // At least one input field is empty, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
        } else if (!isValidEmail(emailText)) {
            // Invalid email format, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email format!");
            alert.showAndWait();
        } else if (userService.checkEmail(emailText)) {
            // Email already exists, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Email already exists!");
            alert.showAndWait();
        } else if (telText.length() != 8) {
            // Telephone number must have exactly 8 characters, show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Telephone number must be 8 characters long!");
            alert.showAndWait();
        } else {
            User user = new User();
            user.setFirstName(firstNameText);
            user.setLastName(lastNameText);
            user.setEmail(emailText);
            int numTel = Integer.parseInt(telText);
            user.setNumTel(numTel);
            user.setPassword(passwordText);
            user.setRole("USER");

            User loggedInUser = userService.signup(user);

            if(loggedInUser != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/userDashboard.fxml"));
                    Parent root = loader.load();

                    // Access the controller of userDashboard.fxml if needed
                    UserDashboardController userDashboardController = loader.getController();
                    // Set any necessary properties or call initialization methods

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) main_form.getScene().getWindow(); // Get the current stage
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    // Failed to load userDashboard.fxml, show error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to load userDashboard.fxml!");
                    alert.showAndWait();
                }
            } else {
                // Failed to insert user, show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to insert user!");
                alert.showAndWait();
            }
        }
    }

    private boolean isValidEmail(String email) {
        // Email validation regular expression
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
