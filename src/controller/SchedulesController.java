package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DAO.*;
import modelClasses.appointmentClass;
import modelClasses.appointmentTotal;
import java.time.LocalDateTime;

import static DAO.customerDAO.populateTable;

/**
 * This is the controller class for the contact schedule page.
 */
public class SchedulesController implements Initializable {
    ObservableList<String> contacts = FXCollections.observableArrayList(
            "Anika Costa", "Daniel Garcia", "Li Lee"
    );


    public ComboBox scheduleCombo;
    public TableView appointmentTable;
    public TableColumn idCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;


    public TableColumn<appointmentClass, LocalDateTime> startCol;
    public TableColumn<appointmentClass, LocalDateTime> endCol;


    /**
     * This method returns the user to the main screen.
     * @param actionEvent
     * @throws IOException
     */

    public void onReturnToMainScreenClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Return to mainscreen button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TransitionScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the SchedulesController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scheduleCombo.setItems(contacts);
        scheduleCombo.setOnAction(this::contactSchedule);
        populateTable(appointmentTotal.getAllAppointment());

        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

    }

    /**
     * This method populates the table with all appointments.
     * @param appointmentListToGet
     */
    void populateTable(ObservableList<appointmentClass> appointmentListToGet) {
        appointmentTable.setItems(appointmentListToGet);
    }

    /**
     * This method alters the table based off of which contact is selected.
     * @param event
     */
    private void contactSchedule(Event event) {
        String contact = scheduleCombo.getSelectionModel().getSelectedItem().toString();
        ObservableList<appointmentClass> newContactList = FXCollections.observableArrayList();

        appointmentTotal.getAllAppointment().forEach((appointment) -> {
            if (appointment.getContactName().contains(contact)) {
                appointmentClass newAppointment = new appointmentClass(appointment.getAppointmentId(), appointment.getTitle(),
                        appointment.getDescription(), appointment.getLocation(),
                        appointment.getType(), appointment.getStartDate(), appointment.getEndDate(), appointment.getCustomerId(),
                        appointment.getUserId(), appointment.getContactId());

                try {
                    newAppointment.setContactName(contactDAO.getContact(appointment.getContactId()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newContactList.add(newAppointment);
            }
        });
        appointmentTable.setItems(newContactList);
        idCol.setCellValueFactory(new PropertyValueFactory<appointmentClass, Integer>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<appointmentClass,String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<appointmentClass, String>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

    }
}
