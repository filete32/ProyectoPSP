package com.psp.gestionproyecto.model.repository.impl;

import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.model.TaskException;
import com.psp.gestionproyecto.model.repository.TaskRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskRepositoryImpl implements TaskRepository {

    private final JDBCConnection connection = new JDBCConnection();
    private Statement stmt;
    private String query;
    private ArrayList<TaskVO> list;
    private TaskVO task;

    public TaskRepositoryImpl() {
    }

    @Override
    public void save(TaskVO task) throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            this.query = "INSERT INTO task (nom_tarea, fecha_creacion, fecha_entrega, id_grupo) VALUES ('" + task.getTaskName() + "', CURDATE(), '" + task.getDueDate() + "', " + task.getGroupId() + ");";
            this.stmt.executeUpdate(this.query);
            this.stmt.close();
            this.connection.disconnectDB(conn);
        } catch (SQLException e) {
            System.out.println(e);
            throw new TaskException("Operation could not be performed");
        }
    }


    @Override
    public void update(TaskVO task) throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            String sql = String.format("UPDATE task SET nom_tarea = '%s', fecha_creacion = '%s', fecha_entrega = '%s', id_grupo = %d WHERE id_tarea = %d", task.getTaskName(), task.getCreationDate(), task.getDueDate(), task.getGroupId(), task.getTaskId());
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new TaskException("Edition could not be performed");
        }
    }

    @Override
    public ArrayList<TaskVO> load() throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.list = new ArrayList<>();
            this.stmt = conn.createStatement();
            this.query = "SELECT * FROM task";
            ResultSet rs = this.stmt.executeQuery(this.query);
            while (rs.next()) {
                int idTask = rs.getInt("id_tarea");
                String taskName = rs.getString("nom_tarea");
                String creationDate = rs.getString("fecha_creacion");
                String dueDate = rs.getString("fecha_entrega");
                int idGroup = rs.getInt("id_grupo");
                this.task = new TaskVO(idTask, taskName, LocalDate.parse(creationDate), LocalDate.parse(dueDate), idGroup);
                this.list.add(this.task);
            }
            this.connection.disconnectDB(conn);
            return this.list;
        } catch (SQLException e) {
            throw new TaskException("Operation could not be performed");
        }
    }

    @Override
    public int lastId() throws TaskException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_tarea FROM task ORDER BY id_tarea DESC LIMIT 1");
            while (resultSet.next()) {
                id = resultSet.getInt("id_tarea");
            }
            return id;
        } catch (SQLException e) {
            throw new TaskException("ID search could not be performed");
        }
    }
}
