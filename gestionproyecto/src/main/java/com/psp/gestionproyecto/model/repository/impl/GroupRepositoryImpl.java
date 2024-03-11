package com.psp.gestionproyecto.model.repository.impl;

import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.GroupException;
import com.psp.gestionproyecto.model.repository.GroupRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Implementation of GroupRepository interface for handling operations related to groups in the database.
 */
public class GroupRepositoryImpl implements GroupRepository {

    private final JDBCConnection connection = new JDBCConnection(); // JDBC connection instance
    private Statement stmt; // Statement object for executing SQL queries
    private String sentence; // SQL query string
    private ArrayList<GroupVO> list; // List to hold GroupVO objects
    private GroupVO group; // GroupVO object

    public GroupRepositoryImpl() {
    }

    /**
     * Saves a group to the database.
     *
     * @param group The group to be saved.
     * @throws GroupException If an error occurs during the operation.
     */
    @Override
    public void save(GroupVO group) throws GroupException {
        try {
            Connection conn = this.connection.connectDB(); // Connect to the database
            this.stmt = conn.createStatement(); // Create a statement
            // SQL insert statement with data from GroupVO
            this.sentence = "INSERT INTO grupo (nombre, descripcion) VALUES ('" + group.getGroup_name() + "','" + group.getGroup_description() + "');";
            this.stmt.executeUpdate(this.sentence); // Execute the SQL statement
            this.stmt.close(); // Close the statement
            this.connection.disconnectDB(conn); // Disconnect from the database
        } catch (SQLException e) {
            System.out.println(e); // Print SQLException (for debugging)
            throw new GroupException("Operation couldn't be made"); // Throw custom exception
        }
    }

    /**
     * Updates a group in the database.
     *
     * @param group The group to be updated.
     * @throws GroupException If an error occurs during the operation.
     */
    @Override
    public void update(GroupVO group) throws GroupException {
        try {
            Connection conn = this.connection.connectDB(); // Connect to the database
            this.stmt = conn.createStatement(); // Create a statement
            // SQL update statement with data from GroupVO
            String sql = String.format("UPDATE grupo SET nombre = '%s', descripcion = '%s' WHERE id_grupo = %d", group.getGroup_name(), group.getGroup_description(), group.getId_group());
            this.stmt.executeUpdate(sql); // Execute the SQL statement
        } catch (SQLException e) {
            throw new GroupException("Couldn't edit"); // Throw custom exception
        }
    }

    /**
     * Loads all groups from the database.
     *
     * @return An ArrayList containing all groups.
     * @throws GroupException If an error occurs during the operation.
     */
    @Override
    public ArrayList<GroupVO> load() throws GroupException {
        try {
            Connection conn = this.connection.connectDB(); // Connect to the database
            this.list = new ArrayList<>(); // Create a new ArrayList to hold GroupVO objects
            this.stmt = conn.createStatement(); // Create a statement
            this.sentence = "SELECT * FROM grupo"; // SQL select statement to retrieve all groups
            ResultSet rs = this.stmt.executeQuery(this.sentence); // Execute the SQL query
            while (rs.next()) {
                // Extract data from ResultSet and create GroupVO objects
                int groupId = rs.getInt("id_grupo");
                String groupName = rs.getString("nombre");
                String groupDescription = rs.getString("descripcion");
                this.group = new GroupVO(groupId, groupName, groupDescription);
                this.list.add(this.group); // Add GroupVO objects to the list
            }
            this.connection.disconnectDB(conn); // Disconnect from the database
            return this.list;
        } catch (SQLException e) {
            throw new GroupException("Operation couldn't be made"); // Throw custom exception
        }
    }

    /**
     * Retrieves the last group ID from the database.
     *
     * @return The last group ID.
     * @throws GroupException If an error occurs during the operation.
     */
    @Override
    public int lastId() throws GroupException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB(); // Connect to the database
            Statement comando = conn.createStatement(); // Create a statement
            // SQL query to retrieve the last group ID
            ResultSet registro = comando.executeQuery("SELECT id_grupo FROM grupo ORDER BY id_grupo DESC LIMIT 1");
            while (registro.next()) {
                id = registro.getInt("id_grupo"); // Get the last group ID from the ResultSet
            }
            return id;
        } catch (SQLException e) {
            throw new GroupException("ID search could not be made"); // Throw custom exception
        }
    }
}
