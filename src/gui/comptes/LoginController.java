package gui.comptes;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entite.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.UserService;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Label passwordLabel;

    @FXML
    private FontAwesomeIconView close;

    @FXML
    private Hyperlink ForgetPasswordLink;

    @FXML
    private CheckBox login_selectShowPassword;

    @FXML
    private Button signupbtn;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void loginAdmin() {
        try {
            User user = new User();
            user.setFirstName(username.getText());
            user.setPassword(password.getText());
            UserService userService = new UserService();
            User loggedInUser = userService.login(user);

            Alert alert;

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all the fields");
                alert.show();
            } else {
                if (loggedInUser != null) { // Check if login was successful
                    // Set the logged-in user in the SessionContext
                    SessionContext.getInstance().setLoggedInUser(loggedInUser);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully logged in: " + loggedInUser.getEmail());
                    alert.show();
                    Stage stage = new Stage();
                    if (loggedInUser.getRole().equals("ADMIN")) {
                        loginbtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    } else if (loggedInUser.getRole().equals("OWNER")) {
                        loginbtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("OwnerDashboard.fxml"));
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        loginbtn.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("UserDashboard.fxml"));
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    }

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleForgetPassword(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ForgetPassword.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleSignup(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPassword() {
        if (login_selectShowPassword.isSelected()) {
            passwordLabel.setText(password.getText());
            passwordLabel.setVisible(true);
            password.setVisible(false);
        } else {
            passwordLabel.setVisible(false);
            password.setVisible(true);
        }
    }
}
