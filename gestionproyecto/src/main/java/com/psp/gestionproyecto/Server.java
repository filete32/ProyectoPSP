package com.psp.gestionproyecto;

import com.psp.gestionproyecto.model.TaskException;
import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.repository.TaskRepository;
import com.psp.gestionproyecto.model.repository.UserRepository;
import com.psp.gestionproyecto.model.repository.impl.TaskRepositoryImpl;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * The Server class represents the main server that handles client connections.
 */
public class Server {
    private static final int PORT = 12345;
    private static Connection connection;

    /**
     * The main method that starts the server.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        establishDBConnection(); // Establish database connection

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for connections...");

            // Continuously accept client connections
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept incoming client connection
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // Create a new ClientHandler thread to handle the client connection
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.setConnection(connection); // Pass the database connection to the client handler
                Thread clientThread = new Thread(clientHandler);
                clientThread.start(); // Start the client handler thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeDBConnection(); // Close database connection when server stops
        }
    }

    /**
     * Establishes the connection to the database.
     */
    private static void establishDBConnection() {
        String jdbcUrl = "jdbc:mysql://localhost/gestionproyectos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=";

        try {
            connection = DriverManager.getConnection(jdbcUrl); // Create connection to the database
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    /**
     * Closes the connection to the database.
     */
    private static void closeDBConnection() {
        if (connection != null) {
            try {
                connection.close(); // Close the database connection
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}