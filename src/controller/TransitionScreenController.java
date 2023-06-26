package controller;

import DAO.appointmentDAO;
import DAO.usersDAO;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelClasses.appointmentClass;
import modelClasses.appointmentTotal;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This is the controller class for the transition screen.
 */
public class TransitionScreenController implements Initializable {
    @FXML
    private Label totalAppointmentLabel;

    @FXML
    private Label nextAppointmentLabel;
    @FXML
    private Label lastUpdateLabel;
    public ComboBox appointmentTypeCombo;
    private static boolean reminderDisplayed = false; // Static flag to track reminder status


    ObservableList<String> appointmentType = FXCollections.observableArrayList(
            "planning session", "debriefing", "counseling"
    );


    /**
     * Method for appointment reminder label.
     * The label sets a message if there's an appointment within 15mins of current time.
     */
    void reminderAppointment() {
        if (reminderDisplayed) {
            return; // Reminder already displayed, no need to show it again
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean hasUpcomingAppointment = false;

        for (appointmentClass appointment : appointmentTotal.getAllAppointment()) {
            if (LocalDate.parse(appointment.getStartDate().split(" ")[0])
                    .compareTo(LocalDate.parse(dtf.format(now).split(" ")[0])) == 0) {
                String startDate = String.valueOf(appointment.getStartDateTime());
                String systemTime = dtf.format(now).toString();
                if (Integer.parseInt(startDate.substring(11, 13))
                        == Integer.parseInt(systemTime.substring(11, 13))) {
                    int timeDifference = Integer.parseInt(startDate.substring(14, 16))
                            - Integer.parseInt(systemTime.substring(14, 16));
                    if (timeDifference < 16 && timeDifference >= 0) {
                        String alertContent = "Reminder: There's an upcoming appointment.\n" +
                                "Appointment ID: " + appointment.getAppointmentId() + "\n" +
                                "Date: " + dtf.format(now).split(" ")[0] + "\n" +
                                "Time: " + appointment.getStartDate().split(" ")[1];

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Appointment Reminder");
                        alert.setHeaderText(null);
                        alert.setContentText(alertContent);
                        alert.show();

                        hasUpcomingAppointment = true;
                    }
                }
            }
        }

        if (!hasUpcomingAppointment) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText(null);
            alert.setContentText("Reminder: There are no upcoming appointments.");
            alert.showAndWait();
        }

        reminderDisplayed = true; // Set the flag to indicate reminder has been displayed
    }

    /**
         * This method takes the user to the customers page.
         * @param actionEvent
         * @throws IOException
         */
        public void onViewCustomersButtonClicked (ActionEvent actionEvent) throws IOException {
            System.out.println("View Customer button clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Customer.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 669, 412);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        /**
         * This method takes the user to the appointment screen.
         * @param actionEvent
         * @throws IOException
         */
        public void onViewAppointmentsButtonClicked (ActionEvent actionEvent) throws IOException {
            System.out.println("View appointments button clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 1044, 456);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        /**
         * This method logs the user out of the application.
         * @param actionEvent
         * @throws IOException
         */
        public void onLogoutButtonClicked (ActionEvent actionEvent) throws IOException {
            System.out.println("logout button clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FirstScreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 800, 600);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        /**
         * This method takes you to the contact schedule page.
         * @param actionEvent
         * @throws IOException
         */
        public void onViewScheduleButtonClicked (ActionEvent actionEvent) throws IOException {
            System.out.println("View schedules button clicked");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Schedules.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 600, 400);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }



        /**
         * This method initializes the TransitionScreenController.
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            reminderAppointment();
            appointmentTypeCombo.setItems(appointmentType);
            try {
                String lastUpdate = usersDAO.getLastUpdate();
                if (lastUpdate != null) {
                    lastUpdateLabel.setText("Last Update: " + lastUpdate);
                } else {
                    lastUpdateLabel.setText("Last Update: N/A");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            /**
             * By using a lambda expression, you can express the event handling logic for appointmentTypeCombo.setOnAction in a more compact and readable manner.
             * The lambda expression reduces the need for extra code, making it easier to understand and maintain.
             */
            appointmentTypeCombo.setOnAction(event -> {
                String selectedType = (String) appointmentTypeCombo.getValue();
                int totalAppointments = 0;

                try {
                    totalAppointments = appointmentDAO.getTotalAppointmentsByType(selectedType);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                totalAppointmentLabel.setText(String.valueOf(totalAppointments));
            });

            // Get the next upcoming appointment
            appointmentDAO appointmentDao = new appointmentDAO();

            appointmentClass nextAppointment = appointmentDao.getNextAppointment();
            if (nextAppointment != null) {
                // Process the next appointment
                String appointmentText = "Next Appointment: " + nextAppointment.getStartDate();
                nextAppointmentLabel.setText(appointmentText);
            } else {
                nextAppointmentLabel.setText("No upcoming appointments.");
            }
        }
    }