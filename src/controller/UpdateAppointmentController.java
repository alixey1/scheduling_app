package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import controller.*;
import DAO.*;
import modelClasses.*;
import controller.AppointmentController;

/**
 * This is the controller class for the update appointment page.
 */
public class UpdateAppointmentController implements Initializable {

    public Button updateButton;
    public ComboBox contactCombo;
    public TextField customerIDText;
    public TextField userIDText;
    public ComboBox startHourCombo;
    public ComboBox startMinCombo;
    public ComboBox endHourCombo;
    public ComboBox endMinCombo;
    public ComboBox typeCombo;
    public TextField appointmentIDText;
    public TextField titleText;
    public TextField locationText;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public TextField descriptionText;

    private static appointmentClass selectedAppointment;
    public static appointmentClass getSelectedAppointment() {
        return selectedAppointment; // Return the selected customer
    }
    ObservableList<String> hours = FXCollections.observableArrayList(
            "01","02","03","04","05","06","07","08","09","10","11","12",
            "13","14","15","16","17","18","19","20","21","22","23"
    );
    ObservableList<String> timeIncrement = FXCollections.observableArrayList(
            "00","10","15","20","25","30","35","40","45","50","55"
    );
    ObservableList<String> appointmentType = FXCollections.observableArrayList(
            "planning session", "debriefing", "counseling"
    );
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    Alert alert= new Alert(Alert.AlertType.ERROR);

    void warning( String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Input validation to make sure there are no empty text field.
     */
    void emptyText() {
        if (titleText.getText().isBlank() || locationText.getText().isBlank() || contactCombo.getSelectionModel().isEmpty()||
                typeCombo.getValue() == null || descriptionText.getText().isBlank() || userIDText.getText().isBlank() ||
                customerIDText.getText().isBlank() || startDatePicker.getValue() == null || endDatePicker.getValue() == null ||
                startHourCombo.getSelectionModel().isEmpty() || startMinCombo.getSelectionModel().isEmpty() ||
                endHourCombo.getSelectionModel().isEmpty() || endMinCombo.getSelectionModel().isEmpty()) {
            warning("Empty Field", "Empty Field", "Please do not leave any of the fields empty");
        }
    }


    /**
     * This method returns the user to appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 1044, 456);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method updates the appointment in the database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onUpdateButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        emptyText();
        appointmentClass selectedAppointment = AppointmentController.getSelectedAppointment();
        int currentindex = appointmentTotal.indexOf(selectedAppointment);
        /* This gets the values of all the variables to be converted to UTC */
        LocalDate startApptDate = startDatePicker.getValue();
        int startYear = startApptDate.getYear();
        int startMonth = startApptDate.getMonthValue();
        int startDay = startApptDate.getDayOfMonth();
        int startHour = Integer.parseInt((String) startHourCombo.getSelectionModel().getSelectedItem());
        int startMin = Integer.parseInt((String) startMinCombo.getSelectionModel().getSelectedItem());


        LocalDate endApptDate = endDatePicker.getValue();
        int endYear = endApptDate.getYear();
        int endMonth = endApptDate.getMonthValue();
        int endDay = endApptDate.getDayOfMonth();
        int endHour = Integer.parseInt((String) endHourCombo.getSelectionModel().getSelectedItem());
        int endMin = Integer.parseInt((String) endMinCombo.getSelectionModel().getSelectedItem());


        int appointmentId = Integer.parseInt(appointmentIDText.getText());
        String title = titleText.getText();
        String location = locationText.getText();
        String contact = contactCombo.getSelectionModel().getSelectedItem().toString();
        String type = typeCombo.getValue().toString();
        String description = descriptionText.getText();
        int userId = Integer.parseInt(userIDText.getText());
        int customerId = Integer.parseInt(customerIDText.getText());
        String startDate = startDatePicker.getValue().toString();
        String endDate = endDatePicker.getValue().toString();

        String startTime = startHourCombo.getSelectionModel().getSelectedItem().toString() + ":" + startMinCombo.getSelectionModel().getSelectedItem().toString()
                + ":00";
        String endTime = endHourCombo.getSelectionModel().getSelectedItem().toString() + ":" + endMinCombo.getSelectionModel().getSelectedItem().toString()
                + ":00";
        String start = startDate + " " + startTime;
        String end = endDate + " " + endTime;

        String currentTime = dtf.format(now);
        String currentUser = String.valueOf(FirstScreenController.getCurrentUser().getUsername());
        int contactId = contactDAO.getContactId(contact);
        String createDate = appointmentDAO.getCreateDate(appointmentId);
        String createdBy = appointmentDAO.getCreatedBy(appointmentId);
        String contactName = contactDAO.getContact(contactId);
        customerClass newCustomer = customerTotal.getCustomer(customerId);

        /* Converts from localtime to UTC to be inserted into database */
        String utcStart = timeConversion.localToUTC(startYear, startMonth, startDay, startHour, startMin);
        String utcEnd = timeConversion.localToUTC(endYear, endMonth, endDay,endHour,endMin);

        /* Converts from LocalTime to EST for the input validation office hour */
        int estStartHour = timeConversion.localToEST(startYear, startMonth, startDay, startHour, startMin);
        int estEndHour = timeConversion.localToEST(endYear, endMonth, endDay,endHour,endMin);

        if (estStartHour < 8 || estEndHour >= 22) {
            warning("Cannot Schedule Appointment", "Exceeded office hours", "Please choose a time within normal office hours");
            return;
        }

        if (startHour > endHour) {
            warning("Cannot Schedule Appointment", "The end hour cannot be earlier than the start hour", "Please change the time.");
            return;
        }
        if (LocalDate.parse(startDate).compareTo(LocalDate.parse(endDate)) > 0 ) {
            warning("Cannot Schedule Appointment", "The start date cannot exceed the end date", "Please change the date.");
            return;
        }


        for (appointmentClass appointment: appointmentTotal.getAllAppointment()) {

            if (appointment.getCustomerId() == customerId) {
                if (appointment.getAppointmentId() != appointmentId) {
                    if (LocalDate.parse(appointment.getStartDate().split(" ")[0]).compareTo(LocalDate.parse(startDate.split(" ")[0])) == 0) {
                        int existStartHour = Integer.parseInt(appointment.getStartDate().substring(11,13));
                        int existStartMin = Integer.parseInt(appointment.getStartDate().substring(14,16));
                        int existEndHour = Integer.parseInt(appointment.getEndDate().substring(11,13));
                        int existEndMin = Integer.parseInt(appointment.getEndDate().substring(14,16));
                        LocalTime startTime1 = LocalTime.of( existStartHour, existStartMin );
                        LocalTime stopTime1= LocalTime.of( existEndHour , existEndMin );
                        LocalTime startTime2 = LocalTime.of(startHour  , startMin);
                        LocalTime stopTime2 = LocalTime.of( endHour , endMin );

                        Boolean isOverLap = ((startTime1.isBefore( stopTime2 )) && ( stopTime1.isAfter( startTime2 )));
                        if (isOverLap) {
                            warning("Cannot Schedule appointment", "Customer has overlapping appointments", "Please schedule another time for this customer.");
                            return;

                        }
                    }
                }
            }

        }


        /* Adding all the information into the database, appointment class and appointment list class */

        appointmentClass newAppointment = new appointmentClass(appointmentId, title,description,location, type,
                start,end,createDate,createdBy,currentTime,currentUser,customerId,userId,contactId);
        newAppointment.setContactName(contactName);
        newCustomer.addAssociatedAppointment(newAppointment);
        appointmentTotal.updateList(currentindex,newAppointment);
        appointmentDAO.update(title,description,location, type,utcStart, utcEnd, currentTime,currentUser,customerId,userId,contactId,appointmentId);


        updateButton.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 1044, 456);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the UpdateAppointmentController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentController.getSelectedAppointment();
        String hour = selectedAppointment.getStartDate().substring(10,13);
        System.out.println(hour);

        appointmentIDText.setText(String.valueOf(selectedAppointment.getAppointmentId()));
        titleText.setText(selectedAppointment.getTitle());
        locationText.setText(selectedAppointment.getLocation());
        typeCombo.setValue(selectedAppointment.getType());
        descriptionText.setText(selectedAppointment.getDescription());
        userIDText.setText(String.valueOf(selectedAppointment.getUserId()));
        customerIDText.setText(String.valueOf(selectedAppointment.getCustomerId()));
        startDatePicker.setValue(LocalDate.parse(selectedAppointment.getStartDate().split(" ")[0]));
        startHourCombo.setValue(selectedAppointment.getStartDate().substring(11,13));
        startMinCombo.setValue(selectedAppointment.getStartDate().substring(14,16));
        endDatePicker.setValue(LocalDate.parse(selectedAppointment.getEndDate().split(" ")[0]));
        endHourCombo.setValue(selectedAppointment.getEndDate().substring(11,13));
        endMinCombo.setValue(selectedAppointment.getEndDate().substring(14,16));
        try {
            contactCombo.setValue(contactDAO.getContact(selectedAppointment.getContactId()));
            contactCombo.setItems(contactDAO.getAllContact());
            startHourCombo.setItems(hours);
            startMinCombo.setItems(timeIncrement);
            endHourCombo.setItems(hours);
            endMinCombo.setItems(timeIncrement);
            typeCombo.setItems(appointmentType);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
