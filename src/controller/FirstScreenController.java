package controller;

import DAO.usersDAO;
import helper.logActivity;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelClasses.appointmentClass;
import modelClasses.userClass;
import DAO.appointmentDAO;
import DAO.customerDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is the controller class for the first screen.
 */
public class FirstScreenController implements Initializable {
    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;
    @FXML
    private Label loginLabel;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label warningLabel;

    @FXML
    private TextField userNameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Label currentLocationLabel;

    private static userClass currentUser;
    private String warningTitle = "Login Error";
    private String warningHeader = "Login Failed";
    private String warningContent = "Login has failed, please try again";

    private Alert warningAlert = new Alert(Alert.AlertType.ERROR);


    /**
     * Gets the current user that just logged in
     *
     * @return The user that just logged in.
     */
    public static userClass getCurrentUser() {
        return currentUser;
    }

    /**
     * Warning Error Alert
     *
     * @param title   title - Title for the error alert
     * @param header  header - Header for the error alert
     * @param content Content - Main Body of the error alert
     */
    private void showErrorAlert(String title, String header, String content) {
        warningAlert.setTitle(title);
        warningAlert.setHeaderText(header);
        warningAlert.setContentText(content);

        /**
         * This lambda expression simplifies the code by providing a concise syntax for executing code on the JavaFX application thread, ensuring that UI-related operations are performed correctly.
         */
        // Show the alert on the JavaFX application thread
        Platform.runLater(() -> warningAlert.showAndWait().ifPresent(result -> {

        }));
    }

    /**
     * This method initializes the FirstScreenController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        String currentLocation = zone.toString();
        currentLocationLabel.setText(currentLocation);

        if (Locale.getDefault().getLanguage().equals("fr")) {
            loginLabel.setText("connexion");
            userNameLabel.setText("Nom d'utilisateur");
            passwordLabel.setText("Mot de passe");
            loginButton.setText("Connexion");
            warningTitle = "Erreur de connexion";
            warningHeader = "Échec de la connexion";
            warningContent = "La connexion a échoué, veuillez réessayer";
        } else {
            userNameLabel.setText("Username");
            passwordLabel.setText("Password");
            loginButton.setText("Login");
            warningTitle = "Login Error";
            warningHeader = "Login Failed";
            warningContent = "Login has failed, please try again";
        }
    }

    /**
     * This method closes the application.
     * @param actionEvent
     */
    @FXML
    public void onExitButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method allows the user to log into the application.
     * @param actionEvent
     * @throws SQLException
     */
    public void onLoginButtonClicked(ActionEvent actionEvent) throws SQLException {
        String tUserName = userNameText.getText();
        String tPassword = passwordText.getText();
        boolean loginSuccess;
        try {
            loginSuccess = usersDAO.loginCheck(tUserName, tPassword);
            if (!loginSuccess) {
                showErrorAlert(warningTitle, warningHeader, warningContent);
                logActivity.LogActivity.logon(tUserName, false);
            } else {
                currentUser = new userClass(tUserName);
                logActivity.LogActivity.logon(tUserName, true);
                customerDAO.populateTable();
                appointmentDAO.populateTable();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TransitionScreen.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    loginButton.getScene().getWindow().hide();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "Login Failed", "An error occurred while accessing the database.");
        }
        }
    }


