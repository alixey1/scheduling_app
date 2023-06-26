package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class divisionDAO {
    /**
     * Retrieves the divisons from the database.
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getDivision(int id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String division = null;

        while (rs.next()) {
            division = rs.getString("Division");
            System.out.print(division+ "\n");

        }
        return division;
    }

    /**
     * Gets the country code.
     * @param id
     * @return
     * @throws SQLException
     */
    public static int getCountryCode(int id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        int countryCode = 0;
        while (rs.next()) {
            countryCode = rs.getInt("Country_ID");
            System.out.print(countryCode+ "\n");

        }
        return countryCode;
    }

    /**
     * Gets the divisionID
     * @param division
     * @return
     * @throws SQLException
     */
    public static int getDivisionId(String division) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        int divisionId = 0;
        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");

        }
        return divisionId;
    }
    public static ObservableList getDivision(String country) throws SQLException {
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        int countryCode = 0;
        if (country == "U.S") {
            countryCode =1 ;
        } else if (country == "UK") {
            countryCode =2;
        } else {
            countryCode =3;
        }

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryCode);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String division = rs.getString("Division");
            divisionList.add(division);
        }
        return divisionList;
    }

}
