package DAO;

import helper.JDBC;
import modelClasses.customerClass;
import modelClasses.customerTotal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The customerDAO class provides methods for querying and manipulating the customer table in the SQL database.
 */
public class customerDAO {

    /**
     * Retrieves the customers from the database and adds them to the table.
     * @throws SQLException
     */
    public static void populateTable() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String number = rs.getString("Phone");
            String createdDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            String firstLevelDiv = divisionDAO.getDivision(divisionId);
            int country_code = divisionDAO.getCountryCode(divisionId);
            String country = countriesDAO.getCountry(country_code);


            customerClass addNewCustomer = new customerClass(customerId, name, address, postalCode, number, firstLevelDiv, country, createdDate, createdBy, lastUpdate, lastUpdatedBy);
            customerTotal.addCustomer(addNewCustomer);
        }
    }

    /**
     * Inserts a new customer into the database.
     * @param id
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param createDate
     * @param createBy
     * @param updateDate
     * @param updateBy
     * @param divisionId
     * @return
     * @throws SQLException
     */
    public static int insert(int id, String name, String address, String postal, String phone,
                             String createDate, String createBy, String updateDate, String updateBy, int divisionId) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postal);
        ps.setString(5, phone);
        ps.setString(6, createDate);
        ps.setString(7, createBy);
        ps.setString(8, updateDate);
        ps.setString(9, updateBy);
        ps.setInt(10, divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates a customer in the database.
     * @param id
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @param updateDate
     * @param updateBy
     * @param divisionId
     * @return
     * @throws SQLException
     */
    public static int update(int id,String name, String address, String postal, String phone,String updateDate, String updateBy, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update =? ,Last_Updated_By=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setString(5, updateDate);
        ps.setString(6, updateBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Retrieves the date a customer was created.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getCreateDate(int id) throws SQLException {
        String sql = "SELECT Create_Date FROM CUSTOMERS Where Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String createdDate = null;
        while (rs.next()) {
            createdDate = rs.getString("Create_Date");
        }
        return createdDate;
    }

    /**
     * Retrieves who created a customer.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getCreatedBy(int id) throws SQLException {
        String sql = "SELECT Created_By FROM CUSTOMERS Where Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String createdBy = null;
        while (rs.next()) {
            createdBy = rs.getString("Created_By");
        }
        return createdBy;
    }

    /**
     * Deletes a customer from the database.
     * @param id
     * @return
     * @throws SQLException
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
