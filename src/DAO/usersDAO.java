package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The usersDAO class provides methods for querying and manipulating the users table in the SQL database.
 */
public class usersDAO {

    /**
     * Retrives the last update from the database.
     * @return
     * @throws SQLException
     */
    public static String getLastUpdate() throws SQLException {
        String sql = "SELECT Last_Update FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("Last_Update");
        }

        return null; // Return null if no record found
    }
        public static void select() throws SQLException {
            String sql = "SELECT * FROM USERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                System.out.print(userId + "|");
                System.out.print(userName + "|");
                System.out.print(password + "\n");
            }
    }

    /**
     * Checks that the user has valid credentials.
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean loginCheck(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            return false;
        }

        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String dbUserName = rs.getString("User_Name");
            String dbPassword = rs.getString("Password");
            System.out.print(userId + "|");
            System.out.print(dbUserName + "|");
            System.out.print(dbPassword + "\n");
            return true;
        }
        return true;
    }

}
