package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/*
* The Database class implements an application that 
* has connection for database and prepared statement.
* @author Dhanya Umapathi (Expleo)
* @since 20 FEB 2024
*
* 
*/

public class Database {
    final private static String URL = "jdbc:oracle:thin:@localhost:1521:Xe";
	final private static String USERNAME = "HTM";
	final private static String PASSWORD = "HTM";

    private static Connection connection;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }




    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // connection.setSchema("HTM");
        }

        return connection;
    }

    public static PreparedStatement prepareStatement(String statement) throws SQLException{
        return getConnection().prepareStatement(statement);
    }

    public static void commit() throws SQLException{
        connection.commit();
    }


    
} 
