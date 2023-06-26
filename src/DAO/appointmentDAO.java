package DAO;

import helper.JDBC;
import modelClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static helper.JDBC.connection;
/**
 * All the query to the appointment table of the SQL Database
 */

public class appointmentDAO {
    /**
     * Retrieves the total number of appointments for a specific type.
     * @param appointmentType
     * @return
     * @throws SQLException
     */
    public static int getTotalAppointmentsByType(String appointmentType) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int totalAppointments = 0;

        try {
            String query = "SELECT COUNT(*) FROM appointments WHERE Type = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, appointmentType);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalAppointments = resultSet.getInt(1);
            }
        } finally {
            // Close the database resources
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return totalAppointments;
    }

    /**
     * Populates the table by retrieving appointments from the database.
     * @throws SQLException
     */
    public static void populateTable() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int aptId = rs.getInt("Appointment_ID");
            String title=rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String startDate = rs.getString("Start");
            String endDate = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdateBy =rs.getString("Last_Updated_By");
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = contactDAO.getContact(contactId);
            customerClass newCustomer = customerTotal.getCustomer(custId);


            /* Getting the Start date information to convert to local time */
            String date = startDate.split(" ")[0];
            String time = startDate.split(" ")[1];
            int year =Integer.parseInt(date.split("-")[0]);
            int month =Integer.parseInt(date.split("-")[1]);
            int day =Integer.parseInt(date.split("-")[2]);
            int hour = Integer.parseInt(time.split(":")[0]);
            int min = Integer.parseInt(time.split(":")[1]);
            String localTimeStart = timeConversion.utcToLocal(year, month, day, hour ,min);


            /* Getting the end date information to convert to local time */
            String eDate = endDate.split(" ")[0];
            String eTime = endDate.split(" ")[1];
            int eYear =Integer.parseInt(eDate.split("-")[0]);
            int eMonth =Integer.parseInt(eDate.split("-")[1]);
            int eDay =Integer.parseInt(eDate.split("-")[2]);
            int eHour = Integer.parseInt(eTime.split(":")[0]);
            int eMin = Integer.parseInt(eTime.split(":")[1]);
            String localTimeEnd = timeConversion.utcToLocal(eYear, eMonth, eDay, eHour ,eMin);


            appointmentClass newAppointment = new appointmentClass(aptId, title, description, location, type, localTimeStart,
                    localTimeEnd, createDate,createdBy, lastUpdate, lastUpdateBy, custId, userId, contactId);


            newCustomer.addAssociatedAppointment(newAppointment);

            newAppointment.setContactName(contactName);

            appointmentTotal.addAppointment(newAppointment);

        }



    }

    /**
     * Inserts new appointments into the database.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createBy
     * @param updateDate
     * @param updateBy
     * @param customerId
     * @param userId
     * @param contactId
     * @return
     * @throws SQLException
     */
    public static int insert(int id, String title, String description, String location, String type, String start, String end,
                             String createDate, String createBy, String updateDate, String updateBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setString(6, start);
        ps.setString(7, end);
        ps.setString(8, createDate);
        ps.setString(9, createBy);
        ps.setString(10, updateDate);
        ps.setString(11, updateBy);
        ps.setInt(12, customerId);
        ps.setInt(13, userId);
        ps.setInt(14, contactId);
        return ps.executeUpdate();
    }

    /**
     * Updates appointments in the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param updateDate
     * @param updateBy
     * @param customerId
     * @param userId
     * @param contactId
     * @param id
     * @return
     * @throws SQLException
     */
    public static int update(String title, String description, String location, String type, String start, String end,
                             String updateDate, String updateBy, int customerId, int userId, int contactId, int id) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type =? ,Start=?, End=?" +
                ",Last_Update=?,Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, end);
        ps.setString(7, updateDate);
        ps.setString(8, updateBy);
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Gets the date that an appointment was created.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getCreateDate(int id) throws SQLException {
        String sql = "SELECT Create_Date FROM appointments Where Appointment_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String createdDate = null;
        while (rs.next()) {
            createdDate = rs.getString("Create_Date");
        }
        return createdDate;
    }

    /**
     * Retrieves the user that created an appointment.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getCreatedBy(int id) throws SQLException {
        String sql = "SELECT Created_By FROM appointments Where Appointment_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String createdBy = null;
        while (rs.next()) {
            createdBy = rs.getString("Created_By");
        }
        return createdBy;
    }

    /**
     * Deletes an appointment from the database.
     * @param id
     * @return
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }



    public appointmentClass getNextAppointment() {

        return null;
    }
}