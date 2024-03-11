package com.psp.gestionproyecto.model.repository.impl;

import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;

/**
 * UserRepositoryImpl is an implementation of UserRepository interface
 * responsible for handling database operations related to users.
 */
public class UserRepositoryImpl implements UserRepository {

    private final JDBCConnection connection = new JDBCConnection(); // JDBC connection instance
    private Statement stmt;
    private String sentence;
    private ArrayList<UserVO> list;
    private UserVO user;

    /**
     * Default constructor for UserRepositoryImpl.
     */
    public UserRepositoryImpl() {
    }

    /**
     * Saves a user to the database.
     *
     * @param user The UserVO object representing the user to be saved.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public void save(UserVO user) throws UserException {
        try {
            Connection conn = this.connection.connectDB(); // Connecting to the database
            this.stmt = conn.createStatement();
            // Creating SQL insert statement with data from UserVO
            this.sentence = "INSERT INTO usuario (nom_usuario, password, mail, admin, id_grupo) VALUES ('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getEmail() + "'," + (user.isAdmin() ? 1 : 0) + "," + user.getGroupId() + ");";
            this.stmt.executeUpdate(this.sentence); // Executing the SQL statement
            this.stmt.close();
            this.connection.disconnectDB(conn); // Disconnecting from the database
        } catch (SQLException e) {
            System.out.println(e);
            throw new UserException("Operation couldn't be made"); // Throwing UserException if operation fails
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The UserVO object representing the user with updated information.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public void update(UserVO user) throws UserException {
        try {
            Connection conn = this.connection.connectDB(); // Connecting to the database
            this.stmt = conn.createStatement();
            // Creating SQL update statement with data from UserVO
            String sql = String.format("UPDATE usuario SET nom_usuario = '%s', password = '%s', mail = '%s', admin = %d, id_grupo = %d WHERE id_usuario = %d", user.getUsername(), user.getPassword(), user.getEmail(), (user.isAdmin() ? 1 : 0), user.getGroupId(), user.getUserId());
            this.stmt.executeUpdate(sql); // Executing the SQL statement
        } catch (SQLException e) {
            throw new UserException("Edit couldn't be made"); // Throwing UserException if edit fails
        }
    }

    /**
     * Checks if a user is an admin based on the provided UserVO object.
     *
     * @param user The UserVO object representing the user to check for admin status.
     * @return true if the user is an admin, false otherwise.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public boolean isAdmin(UserVO user) throws UserException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (user != null) {
                conn = this.connection.connectDB(); // Connecting to the database
                String query = "SELECT admin FROM usuario WHERE nom_usuario = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, user.getUsername());
                rs = stmt.executeQuery();
                boolean isAdmin = false;
                if (rs.next()) {
                    isAdmin = rs.getBoolean("admin");
                }
                return isAdmin;
            } else {
                throw new UserException("User is null");
            }
        } catch (SQLException e) {
            throw new UserException("Error verifying admin permits: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    this.connection.disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads all users from the database.
     *
     * @return An ArrayList containing all UserVO objects representing the users in the database.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public ArrayList<UserVO> load() throws UserException {
        try {
            Connection conn = this.connection.connectDB(); // Connecting to the database
            this.list = new ArrayList<>();
            this.stmt = conn.createStatement();
            this.sentence = "SELECT * FROM usuario";
            ResultSet rs = this.stmt.executeQuery(this.sentence); // Executing the SQL query
            while (rs.next()) {
                int userId = rs.getInt("id_usuario");
                String userName = rs.getString("nom_usuario");
                String userPassword = rs.getString("password");
                String userMail = rs.getString("mail");
                boolean isAdmin = rs.getBoolean("admin");
                int groupId = rs.getInt("id_grupo");
                this.user = new UserVO(userId, userName, userPassword, userMail, isAdmin, groupId);
                this.list.add(this.user);
            }
            this.connection.disconnectDB(conn); // Disconnecting from the database
            return this.list;
        } catch (SQLException e) {
            throw new UserException("Operation couldn't be made");
        }
    }

    /**
     * Retrieves the last ID from the database.
     *
     * @return The last ID stored in the database.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public int lastId() throws UserException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB(); // Connecting to the database
            Statement comando = conn.createStatement();
            ResultSet registro = comando.executeQuery("SELECT id_usuario FROM usuario ORDER BY id_usuario DESC LIMIT 1");
            while (registro.next()) {
                id = registro.getInt("id_usuario");
            }
            return id;
        } catch (SQLException e) {
            throw new UserException("ID search couldn't be made");
        }
    }

    /**
     * Retrieves a user by username from the database.
     *
     * @param username The username of the user to retrieve.
     * @return The UserVO object representing the user with the specified username.
     * @throws UserException if an error occurs during the operation.
     */
    @Override
    public UserVO getUserByUsername(String username) throws UserException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserVO user = null;
        try {
            conn = this.connection.connectDB(); // Connecting to the database
            String query = "SELECT * FROM usuario WHERE nom_usuario = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id_usuario");
                String userName = rs.getString("nom_usuario");
                String userPassword = rs.getString("password");
                String userMail = rs.getString("mail");
                boolean isAdmin = rs.getBoolean("admin");
                int groupId = rs.getInt("id_grupo");
                user = new UserVO(userId, userName, userPassword, userMail, isAdmin, groupId);
            }
        } catch (SQLException e) {
            throw new UserException("Error obtaining user by username: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    this.connection.disconnectDB(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

}
