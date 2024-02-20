package com.psp.gestionproyecto;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
    private static final int PORT = 12345;
    private static Connection connection;

    public static void main(String[] args) {
        establishDBConnection();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                Thread clientThread = new ClientHandler(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeDBConnection();
        }
    }

    private static void establishDBConnection() {
        String jdbcUrl = "jdbc:mysql://localhost/gestionproyectos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=userdjbc";

        try {
            connection = DriverManager.getConnection(jdbcUrl);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database: " + e.getMessage());
            // Es importante manejar adecuadamente la excepción o lanzarla nuevamente si es necesario
        }
    }

    private static void closeDBConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {

                String username = in.readLine();
                String password = in.readLine();

                boolean loggedIn = checkCredentials(username, password);

                out.println(loggedIn ? "VALID" : "FAILURE");

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean checkCredentials(String username, String password) {

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuario WHERE nom_usuario = ? AND password = ?")) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}