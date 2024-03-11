package com.psp.gestionproyecto.model.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    // Default constructor
    public JDBCConnection() {
    }

    // Method to establish a database connection
    public Connection connectDB() throws SQLException {
        try {
            // Establishing connection to the MySQL database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/gestionproyectos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            // Registering the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return conn; // Returning the established connection
        } catch (SQLException var2) {
            // Handling SQLException
            SQLException ex = var2;
            System.out.println("\n--- SQLException captured ---\n");

            // Printing SQLException details
            while (ex != null) {
                System.out.println("Message:   " + ex.getMessage());
                System.out.println("SQLState:  " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }

            throw new SQLException(); // Throwing SQLException
        } catch (Exception var3) {
            // Handling other exceptions
            throw new SQLException();
        }
    }

    // Method to disconnect from the database
    public void disconnectDB(Connection conn) {
        try {
            conn.close(); // Closing the database connection
        } catch (SQLException var3) {
            // Handling SQLException
            SQLException ex = var3;
            System.out.println("\n--- SQLException captured ---\n");

            // Printing SQLException details
            while (ex != null) {
                System.out.println("Message:   " + ex.getMessage());
                System.out.println("SQLState:  " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }
        }
    }
}
