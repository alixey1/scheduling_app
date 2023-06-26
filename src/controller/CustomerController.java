package controller;

import DAO.customerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelClasses.customerClass;
import modelClasses.customerTotal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller class for the customer screen.
 */
public class CustomerController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView customerTable;

    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn postalCodeCol;
    @FXML
    private TableColumn phoneNumberCol;
    @FXML
    private TableColumn countryCol;
    @FXML
    private TableColumn divisionCol;




    private static customerClass selectedCustomer;
    public static customerClass getSelectedCustomer() {
        return selectedCustomer; // Return the selected customer
    }

    public static customerClass selectedCustomer() { return selectedCustomer; };
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alert= new Alert(Alert.AlertType.ERROR);
    /**
     * Warning Alert
     * @param title - Title of Warning Alert
     * @param header - Header of Warning Alert
     * @param content - Main body of Warning Alert
     */
    void warning(String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This button redirects the user to the update customer screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onUpdateButtonClicked(ActionEvent actionEvent) throws IOException {
        selectedCustomer = (customerClass) customerTable.getSelectionModel().getSelectedItem();
        System.out.println("Update button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCustomer.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes a customer from the database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onDeleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        customerClass selectedCustomer = (customerClass) customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            warning("Warning", "Nothing is selected", "Please select a customer to delete.");
        } else {
            confirmationAlert.setTitle("");
            confirmationAlert.setHeaderText("Delete");
            confirmationAlert.setContentText("Are you sure you want to delete this?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (((Optional<?>) result).get() == ButtonType.OK) {
                if (!selectedCustomer.getAssocAppointment().isEmpty()) {
                    warning("Delete", "Cannot be deleted", "Please delete the associated appointment before deleting the customer.");
                } else {
                    int custID = selectedCustomer.getId();
                    System.out.println(custID);
                    customerDAO.delete(custID);
                    customerTotal.deleteCustomer(selectedCustomer);
                    informationAlert.setContentText("Customer has been deleted");
                    informationAlert.show();
                }
            } else {
                return;
            }
        }
    }

    /**
     * This method returns the user to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onReturnToMainScreenButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Return to main screen button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TransitionScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method redirects the user to the add customer page.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Add button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCustomer.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the CustomerController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable.setItems(customerTotal.getAllCustomer());
        idCol.setCellValueFactory(new PropertyValueFactory<customerClass, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<customerClass, String>("name"));
        addressCol.setCellValueFactory((new PropertyValueFactory<customerClass, String>("fullAddress")));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<customerClass, String>("postalCode"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<customerClass, String>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<customerClass, String>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<customerClass, String>("division"));

    }
}
