package DAO;

import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countriesDAO {
    /**
     * Retrieves the countries from the database.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getCountry(int id) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String country = null;
        while (rs.next()) {
            country = rs.getString("Country");
        }
        return country;
    }
}
