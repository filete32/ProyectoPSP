package com.psp.gestionproyecto;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

import com.psp.gestionproyecto.util.UserPreferences;
import org.apache.commons.net.smtp.*;

public class Notification {
    private static final String DB_URL = "jdbc:mysql://localhost/gestionproyectos";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void run() throws NoSuchAlgorithmException, UnrecoverableKeyException,
            KeyStoreException, InvalidKeyException, InvalidKeySpecException {

        // Creating secure SMTP client
        AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();

        // SMTP server details
        String server = "smtp.gmail.com";
        int port = 587;

        // Email credentials
        String username = "gestionproyectopsp@gmail.com";
        String password = "dkwincfqzxrfsiyc";

        // Sender email address
        String sender = "gestionproyectopsp@gmail.com";

        // Retrieve destination email address from the database
        String destination = obtainRecipientEmailFromDB();

        try {
            int response;

            // Creating key to establish secure channel
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(null, null);
            KeyManager km = kmf.getKeyManagers()[0];

            // Connecting to SMTP server
            client.connect(server, port);
            System.out.println("1 - " + client.getReplyString());
            // Setting key for secure communication
            client.setKeyManager(km);

            response = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(response)) {
                client.disconnect();
                System.err.println("CONNECTION REJECTED.");
                System.exit(1);
            }

            // Sending EHLO command
            client.ehlo(server);// required
            System.out.println("2 - " + client.getReplyString());

            // NEEDS TLS NEGOTIATION - NON-IMPLICIT MODE
            // Executing STARTTLS command and checking if true
            if (client.execTLS()) {
                System.out.println("3 - " + client.getReplyString());

                // Authenticating with the server
                if (client.auth(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN, username, password)) {
                    System.out.println("4 - " + client.getReplyString());

                    String subject = "New Login";
                    String message = "You are logged in to the Project Management program in PSP Group 4.";
                    // Creating header
                    SimpleSMTPHeader header = new SimpleSMTPHeader(sender, destination, subject);

                    // Setting sender and recipient email address
                    client.setSender(sender);
                    client.addRecipient(destination);
                    System.out.println("5 - " + client.getReplyString());

                    // Sending DATA
                    Writer writer = client.sendMessageData();
                    if (writer == null) { // failure
                        System.out.println("FAILED TO SEND DATA.");
                        System.exit(1);
                    }

                    writer.write(header.toString());
                    writer.write(message);
                    writer.close();
                    System.out.println("6 - " + client.getReplyString());

                    boolean success = client.completePendingCommand();
                    System.out.println("7 - " + client.getReplyString());

                    if (!success) {
                        System.out.println("FAILED TO COMPLETE TRANSACTION.");
                        System.exit(1);
                    } else
                        System.out.println("MESSAGE SENT SUCCESSFULLY.");

                } else
                    System.out.println("USER NOT AUTHENTICATED.");
            } else
                System.out.println("FAILED TO EXECUTE STARTTLS.");

        } catch (IOException e) {
            System.err.println("Could not connect to server.");
            e.printStackTrace();
            System.exit(1);
        }
        try {
            client.disconnect();
        } catch (IOException f) {
            f.printStackTrace();
        }

        System.out.println("End of sending.");
    }

    // Method to obtain destination email address from the database
    private static String obtainRecipientEmailFromDB() {
        UserPreferences userPrefs = new UserPreferences(); // Creación de la instancia para manejar las preferencias
        String username = userPrefs.getUsername(); // Obtención del nombre de usuario desde las preferencias
        String destination = null; // Asegúrate de que destination esté declarada fuera del bloque try-catch
        System.out.println("Username to search: " + username); // Verifica que el nombre de usuario se imprima correctamente

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT mail FROM usuario WHERE nom_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username); // Estableciendo el nombre de usuario como parámetro
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        destination = rs.getString("mail"); // Obteniendo el correo electrónico
                    } else {
                        System.out.println("No se encontró el correo electrónico para el usuario: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el stack trace si hay una excepción SQL
        }
        return destination;
    }


}