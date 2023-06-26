package controller;


import DAO.appointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import modelClasses.appointmentClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelClasses.appointmentTotal;
import modelClasses.customerClass;
import modelClasses.customerTotal;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * This is the controller class for the Appointment screen.
 */

public class AppointmentController implements Initializable {
    @FXML
    public TableColumn appointmentIDCol;
    @FXML
    public TableColumn titleCol;
    @FXML
    public TableColumn typeCol;
    @FXML
    public TableView appointmentTable;
    public TableColumn<appointmentClass, String> startCol;
    public TableColumn<appointmentClass, String> endCol;
    @FXML
    public TableColumn locationCol;
    @FXML
    public TableColumn contactCol;
    @FXML
    public TableColumn customerIDCol;
    @FXML
    public TableColumn userIDCol;
    @FXML
    public TableColumn descriptionCol;
    public ToggleGroup radioButtonGroup;
    @FXML
    private RadioButton allAppointmentsRadio;
    @FXML
    private RadioButton currentWeekRadio;
    @FXML
    private RadioButton currentMonthRadio;
    LocalDate currentDate = LocalDate.now();
    public static appointmentClass getSelectedAppointment() {
        return selectedAppointment;
    }

    public static appointmentClass selectedAppointment;
    Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alert = new Alert(Alert.AlertType.ERROR);

    void warning(String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * This method redirects the user to the update screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onUpdateButtonClicked(ActionEvent actionEvent) throws IOException {
        selectedAppointment = (appointmentClass) appointmentTable.getSelectionModel().getSelectedItem();
        System.out.println("Update button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAppointment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method deletes an existing appointment from the database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onDeleteButtonClicked(ActionEvent actionEvent) throws SQLException {
        System.out.println("delete part button clicked");
        appointmentClass selectedAppointment = (appointmentClass) appointmentTable.getSelectionModel().getSelectedItem();
        customerClass currentCustomer = customerTotal.getCustomer(selectedAppointment.getCustomerId());

        if (selectedAppointment == null) {
            warning("Warning", "Nothing is selected", "Please select an appointment to delete.");
        } else {
            confirmationAlert.setTitle("");
            confirmationAlert.setHeaderText("Delete");
            confirmationAlert.setContentText("Are you sure you want to delete this?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                int apptId = selectedAppointment.getAppointmentId();
                currentCustomer.removeAssociatedAppointment(selectedAppointment);
                System.out.println(apptId);
                appointmentDAO.delete(apptId);
                appointmentTotal.deleteAppointment(selectedAppointment);
                informationAlert.setContentText("Appointment ID:" + selectedAppointment.getAppointmentId() + " \nAppointment Type: " + selectedAppointment.getType() + " \nhas been deleted.");
                informationAlert.show();
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method redirects the user to the add appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Add button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the AppointmentController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");
        allAppointmentsRadio.setSelected(true);
        populateTable(appointmentTotal.getAllAppointment());

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));



    }

    /**
     * This method populates the controller.
     * @param appointmentListToGet
     */
    void populateTable(ObservableList<appointmentClass> appointmentListToGet) {
        appointmentTable.setItems(appointmentListToGet);
    }

    /**
     * This method populates the table with all appointments.
     * @param actionEvent
     */
    public void onAllAppointmentsRadioClicked(ActionEvent actionEvent) {
        allAppointmentsRadio.setSelected(true);
        currentMonthRadio.setSelected(false);
        currentWeekRadio.setSelected(false);
        populateTable(appointmentTotal.getAllAppointment());
    }

    /**
     * This method populates the table with appointments happening this week.
     * @param actionEvent
     */
    public void onCurrentWeekRadioClicked(ActionEvent actionEvent) {
        allAppointmentsRadio.setSelected(false);
        currentMonthRadio.setSelected(false);
        currentWeekRadio.setSelected(true);
        ObservableList<appointmentClass> newAppointmentList = FXCollections.observableArrayList();
        LocalDate date = LocalDate.now();
        TemporalField weekofyear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNum = date.get(weekofyear);
        int weekNum2;

        for (appointmentClass appointment : appointmentTotal.getAllAppointment()) {
            int year = Integer.parseInt(appointment.getStartDate().substring(0, 4));
            int month = Integer.parseInt(appointment.getStartDate().substring(5, 7));
            int day = Integer.parseInt(appointment.getStartDate().substring(8, 10));
            LocalDate date2 = LocalDate.of(year, month, day);
            weekNum2 = date2.get(weekofyear);
            if (weekNum == weekNum2) {
                newAppointmentList.add(appointment);
            }
        }
        appointmentTable.setItems(newAppointmentList);
    }

    /**
     * This method populates the table with appointments happening this month.
     * @param actionEvent
     */
    public void onCurrentMonthRadioClicked(ActionEvent actionEvent) {
        allAppointmentsRadio.setSelected(false);
        currentMonthRadio.setSelected(true);
        currentWeekRadio.setSelected(false);
        ObservableList<Object> newAppointmentList = FXCollections.observableArrayList();

        int month = currentDate.getMonthValue();
        for (appointmentClass appointment : appointmentTotal.getAllAppointment()) {
            if (Integer.parseInt(appointment.getStartDate().substring(5, 7)) == month) {
                newAppointmentList.add(appointment);
            }
        }
        appointmentTable.setItems(newAppointmentList);
    }
}
