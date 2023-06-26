package controller;

import DAO.customerDAO;
import DAO.divisionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelClasses.customerClass;
import modelClasses.customerTotal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This is the controller class for adding a customer to the database.
 */
public class AddCustomerController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox countryCombo;
    @FXML
    private ComboBox divisionCombo;
    @FXML
    private TextField customerIDText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField postalCodeText;
    @FXML
    private TextField phoneNumberText;


    ObservableList<String> countryList = FXCollections.observableArrayList("U.S", "UK", "Canada");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    /**
     * This button saves the new customer to the database.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onSaveButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        int id = customerTotal.getAllCustomer().size() + 1;
        String name = nameText.getText();
        String address = addressText.getText();
        String division = divisionCombo.getSelectionModel().getSelectedItem().toString();
        String country = countryCombo.getSelectionModel().getSelectedItem().toString();
        String phone = phoneNumberText.getText();
        String postal = postalCodeText.getText();
        int divisionId = divisionDAO.getDivisionId(division);
        String currentTime = dtf.format(now);
        String currentUser = String.valueOf(FirstScreenController.getCurrentUser().getUsername());

        customerDAO.insert(id, name, address, postal, phone, currentTime, currentUser, currentTime, currentUser, divisionId);
        customerClass newCustomer = new customerClass(id, name, address, postal, phone, division, country, currentTime, currentUser , currentTime, currentUser);
        customerTotal.addCustomer(newCustomer);
        saveButton.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 669, 412);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method returns the user to the customer screen.
     * @param actionEvent
     * @throws IOException
     */

    public void onCancelButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 669, 412);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the AddCustomerController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(countryList);
        countryCombo.setValue("U.S");
        try {
            divisionCombo.setItems(divisionDAO.getDivision("U.S"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int nextCustomerId = customerTotal.getAllCustomer().size() + 1;
        customerIDText.setText(Integer.toString(nextCustomerId));
    }

    public void onCountryCombo(ActionEvent actionEvent) {
        String country = countryCombo.getSelectionModel().getSelectedItem().toString();
        try {
            divisionCombo.setItems(divisionDAO.getDivision(country));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
