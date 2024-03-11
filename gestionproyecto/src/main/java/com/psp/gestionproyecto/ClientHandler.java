package com.psp.gestionproyecto;

import com.psp.gestionproyecto.model.*;
import com.psp.gestionproyecto.model.repository.GroupRepository;
import com.psp.gestionproyecto.model.repository.TaskRepository;
import com.psp.gestionproyecto.model.repository.UserRepository;
import com.psp.gestionproyecto.model.repository.impl.GroupRepositoryImpl;
import com.psp.gestionproyecto.model.repository.impl.TaskRepositoryImpl;
import com.psp.gestionproyecto.model.repository.impl.UserRepositoryImpl;

import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The ClientHandler class manages the communication with a single client in a multi-threaded server application.
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket; // Socket representing the connection to the client
    private static BufferedReader in; // BufferedReader for reading input from the client
    private PrintWriter out; // PrintWriter for writing output to the client

    private Connection connection; // Database connection
    private final TaskRepository taskRepository = new TaskRepositoryImpl(); // Task repository for database operations
    private final UserRepository userRepository = new UserRepositoryImpl(); // User repository for database operations
    private final GroupRepository groupRepository = new GroupRepositoryImpl(); // Group repository for database operations

    /**
     * Constructs a new ClientHandler with the given client socket.
     *
     * @param socket The client socket.
     */
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the client handler thread.
     */
    @Override
    public void run() {
        try {
            String request = in.readLine(); // Read the client's request
            switch (request) {
                case "LOGIN":
                    loginFromClient(); // Handle login request from client
                    break;
                case "SAVE_TASK":
                    handleSaveTaskFromClient(); // Handle save task request from client
                    break;
                case "LOAD_TASKS":
                    handleLoadTasks(); // Handle load tasks request from client
                    break;
                case "UPDATE_TASK":
                    handleUpdateTask(); // Handle update task request from client
                    break;
                case "SAVE_USER":
                    handleSaveUserFromClient(); // Handle save user request from client
                    break;
                case "UPDATE_GROUP":
                    handleUpdateGroup();
                    break;
                case "LOAD_USERS":
                    handleLoadUsers(); // Handle load users request from client
                    break;
                case "SAVE_GROUP":
                    handleSaveGroupFromClient();
                    break;
                case "LOAD_GROUPS":
                    handleLoadGroups();
                    break;
                case "IS_ADMIN":
                    isAdmin(); // Handle check admin status request from client
                    break;
                case "UPDATE_USER":
                    handleUpdateUser(); // Handle update user request from client
                    break;
                default:
                    break;
            }

            clientSocket.close(); // Close the client socket

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks the credentials received from the client.
     *
     * @param username The username received from the client.
     * @param password The password received from the client.
     * @return True if the credentials are valid, false otherwise.
     */
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

    /**
     * Sets the database connection for the client handler.
     *
     * @param connection The database connection.
     */
    public void setConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * Handles the save task request received from the client.
     */
    private void handleSaveTaskFromClient() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            TaskVO taskVO = (TaskVO) objectInputStream.readObject();
            taskRepository.save(taskVO); // Save the task in the database
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException | TaskException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the save user request received from the client.
     */
    private void handleSaveUserFromClient() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            UserVO userVO = (UserVO) objectInputStream.readObject();
            userRepository.save(userVO); // Save the user in the database
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException | UserException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSaveGroupFromClient(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            GroupVO groupVO = (GroupVO) objectInputStream.readObject();
            groupRepository.save(groupVO);
            objectInputStream.close();
        } catch (IOException | GroupException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the update task request received from the client.
     */
    private void handleUpdateTask(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            TaskVO taskVO = (TaskVO) objectInputStream.readObject();
            taskRepository.update(taskVO); // Update the task in the database
            objectInputStream.close();
        } catch (TaskException | IOException | ClassNotFoundException e) {
            System.err.println("Error updating the task: " + e.getMessage());
        }
    }

    /**
     * Handles the update user request received from the client.
     */
    private void handleUpdateUser(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            UserVO userVO = (UserVO) objectInputStream.readObject();
            userRepository.update(userVO); // Update the user in the database
            objectInputStream.close();
        } catch (IOException | UserException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUpdateGroup(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            GroupVO groupVO = (GroupVO) objectInputStream.readObject();
            groupRepository.update(groupVO);
            objectInputStream.close();
        } catch (IOException | GroupException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the load tasks request received from the client.
     */
    private void handleLoadTasks(){
        try {
            ArrayList<TaskVO> taskVOS = taskRepository.load(); // Load tasks from the database

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(taskVOS); // Send the list of tasks to the client
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (TaskException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the load users request received from the client.
     */
    private void handleLoadUsers(){
        try {
            ArrayList<UserVO> userVOS = userRepository.load(); // Load users from the database

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(userVOS); // Send the list of users to the client
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException | UserException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLoadGroups(){
        try {
            ArrayList<GroupVO> groupVOS = groupRepository.load(); // Load group from the database

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(groupVOS);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException | GroupException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the login request received from the client.
     */
    // Inside the loginFromClient() method in the ClientHandler class
    private void loginFromClient() {
        try {
            String username = in.readLine();
            String password = in.readLine();
            boolean loggedIn = checkCredentials(username, password); // Verify credentials
            if (loggedIn) {
                out.println("VALID"); // Send Response to Customer
            } else {
                out.println("LOGIN_FAILURE"); // Send Response to Customer
            }
            Notification notification = new Notification();
            try {
                notification.run();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (UnrecoverableKeyException e) {
                throw new RuntimeException(e);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            out.println("LOGIN_FAILURE");
        }
    }


    /**
     * Checks if the client is an admin user.
     *
     * @throws UserException If an error occurs while retrieving user information.
     */
    public void isAdmin() throws UserException {
        String username = null;
        try {
            username = in.readLine(); // Read username from client
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserRepository userRepository = new UserRepositoryImpl();
        UserVO currentUser = userRepository.getUserByUsername(username); // Get user information

        out.println(userRepository.isAdmin(currentUser) ? "YES" : "NO"); // Send response to client
    }
}
