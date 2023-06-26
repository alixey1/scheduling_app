package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The contactDAO class provides methods for querying and manipulating the contact table in the SQL database.
 */
public class contactDAO {

    /**
     * Retrieves contacts from the database.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getContact(int id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String contactName = null;
        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }

        return contactName;
    }

    /**
     * Retrieves contact ID from the database
     * @param name
     * @return
     * @throws SQLException
     */
    public static int getContactId(String name) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }

        return contactId;
    }

    /**
     * Gets all contacts from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList getAllContact() throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String contact = rs.getString("Contact_Name");
            contactList.add(contact);
        }
        return contactList;
    }

}
