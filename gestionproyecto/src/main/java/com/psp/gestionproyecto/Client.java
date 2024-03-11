package com.psp.gestionproyecto;

import com.psp.gestionproyecto.controller.LoginController;
import com.psp.gestionproyecto.controller.MainController;
import com.psp.gestionproyecto.controller.RootLayoutController;
import com.psp.gestionproyecto.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * The Client class represents the client application that communicates with the server.
 */
public class Client extends Application {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * The main method of the application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the client application.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            // Here we pass the Client instance to the LoginController
            loginController.setClient(this);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Shows an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert.
     * @param message The message content of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Launches the main application window.
     *
     * @param primaryStage The primary stage of the application.
     */
    public void launchMainApplication(Stage primaryStage) {
        Platform.runLater(() -> {
            try {
                FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/RootLayout.fxml"));
                BorderPane rootLayout = rootLoader.load();
                RootLayoutController rootLayoutController = rootLoader.getController();
                Client client = rootLayoutController.getClient();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/psp/gestionproyecto/MainView.fxml"));
                AnchorPane mainView = loader.load();
                MainController mainController = loader.getController();
                mainController.setClient(client);

                rootLayout.setCenter(mainView);

                primaryStage.setScene(new Scene(rootLayout));
                primaryStage.setTitle("Tasks");
                primaryStage.show();
                primaryStage.toFront();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to launch main application");
            }
        });
    }

    /**
     * Sends a TaskVO object to the server to be saved.
     *
     * @param taskVO The TaskVO object to be saved.
     */
    public void saveTask(TaskVO taskVO) {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            out.println("SAVE_TASK");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(taskVO);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a UserVO object to the server to be saved.
     *
     * @param userVO The UserVO object to be saved.
     */
    public void saveUser(UserVO userVO) {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.println("SAVE_USER");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(userVO);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveGroup(GroupVO groupVO){
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.println("SAVE_GROUP");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(groupVO);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a TaskVO object to the server to be updated.
     *
     * @param taskVO The TaskVO object to be updated.
     */
    public void updateTask(TaskVO taskVO) {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.println("UPDATE_TASK");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(taskVO);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

/**
 * Sends a UserVO object
 * to the server to be updated.
 *
 * @param userVO The UserVO object to be updated.
 */
public void updateUser(UserVO userVO) {
    try {
        socket = new Socket(SERVER_IP, SERVER_PORT);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    PrintWriter out;
    try {
        out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    try {
        out.println("UPDATE_USER");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(userVO);
        objectOutputStream.flush();
        objectOutputStream.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

public void updateGroup(GroupVO groupVO){
    try {
        socket = new Socket(SERVER_IP, SERVER_PORT);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    PrintWriter out;
    try {
        out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    try {
        out.println("UPDATE_GROUP");
        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream1.writeObject(groupVO);
        objectOutputStream1.flush();
        objectOutputStream1.close();
    } catch (IOException e){
        throw new RuntimeException(e);
    }
}

    /**
     * Loads tasks from the server.
     *
     * @return An ArrayList of TaskVO objects loaded from the server.
     */
    public ArrayList<TaskVO> loadTasks() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<TaskVO> taskVOS = new ArrayList<>();
        try {
            out.println("LOAD_TASKS");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object receivedObject = objectInputStream.readObject();

            if (receivedObject instanceof ArrayList<?>) {
                ArrayList<?> receivedList = (ArrayList<?>) receivedObject;
                for (Object obj : receivedList) {
                    if (obj instanceof TaskVO) {
                        taskVOS.add((TaskVO) obj);
                    }
                }
            } else {
                throw new RuntimeException("Received object is not an ArrayList<TaskVO>");
            }

            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return taskVOS;
    }

    /**
     * Loads users from the server.
     *
     * @return An ArrayList of UserVO objects loaded from the server.
     */
    public ArrayList<UserVO> loadUsers() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<UserVO> userVOS = new ArrayList<>();

        try {
            out.println("LOAD_USERS");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object receivedObject = objectInputStream.readObject();

            if (receivedObject instanceof ArrayList<?>) {
                ArrayList<?> receivedList = (ArrayList<?>) receivedObject;
                for (Object obj : receivedList) {
                    if (obj instanceof UserVO) {
                        userVOS.add((UserVO) obj);
                    }
                }
            } else {
                throw new RuntimeException("Received object is not an ArrayList<UserVO>");
            }

            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userVOS;
    }

    public ArrayList<GroupVO> loadGroups(){
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<GroupVO> groupVOS = new ArrayList<>();

        try {
            out.println("LOAD_GROUPS");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object receiveObject = objectInputStream.readObject();

            if (receiveObject instanceof ArrayList<?>){
                ArrayList<?> receivedList = (ArrayList<?>) receiveObject;
                for (Object obj : receivedList){
                    if (obj instanceof GroupVO){
                        groupVOS.add((GroupVO) obj);
                    }
                }
            } else {
                throw new RuntimeException("Received object is not an ArrayList<GroupVO>");
            }
            objectInputStream.close();
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return groupVOS;
    }

    /**
     * Checks if a user is an administrator.
     *
     * @param username The username of the user to check.
     * @return True if the user is an administrator, false otherwise.
     * @throws UserException If there is an error while checking the user's admin status.
     */
    public boolean isAdmin(String username) throws UserException {

        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        out.println("IS_ADMIN");

        try {
            if (in.readLine().equals("YES")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Attempts to log in with the provided username and password.
     *
     * @param username The username to log in with.
     * @param password The password to log in with.
     * @return True if the login is successful, false otherwise.
     */
    public boolean login(String username, String password) {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.println("LOGIN");
            out.println(username);
            out.println(password);

            String response = in.readLine();
            return response != null && response.equals("VALID");
        } catch (IOException e) {
            showAlert("Error", "Failed to login");
            return false;
        }
    }


    /**

     Sends a notification via email using the provided socket connection.
     This method initializes a PrintWriter with the socket's output stream
     and sends a "NOTIFY" message.
     @throws RuntimeException if an IOException occurs while creating the PrintWriter
     markdown
     Copy code
     or sending the notification
     */

    public void mailNotify(){
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println("NOTIFY");
    }
}
