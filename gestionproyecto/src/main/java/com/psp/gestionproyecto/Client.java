package com.psp.gestionproyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            boolean loggedIn = false;
            while (!loggedIn) {
                System.out.print("Enter the username: ");
                String username = userInput.readLine();
                System.out.print("Enter your password: ");
                String password = userInput.readLine();

                out.println(username);
                out.println(password);

                String response = in.readLine();
                if (response.equals("VALID")) {
                    System.out.println("Valid credentials. Logging in...");
                    loggedIn = true;

                    String usernameEnvVariable = "USERNAME";
                    System.setProperty(usernameEnvVariable, username);

                    launchMainApplication(primaryStage);
                } else {
                    System.out.println("Incorrect credentials. Try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Connection Error", "Failed to connect to server: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void launchMainApplication(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/MainView.fxml"));
            AnchorPane mainView = loader.load();

            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/RootLayout.fxml"));
            BorderPane rootLayout = rootLoader.load();
            rootLayout.setCenter(mainView);

            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.setTitle("Tasks");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to launch main application: " + e.getMessage());
        }
    }
}
