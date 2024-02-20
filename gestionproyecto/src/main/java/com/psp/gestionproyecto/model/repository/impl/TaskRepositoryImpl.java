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
    private String sentence;
    private ArrayList<TaskVO> list;
    private TaskVO task;

    public TaskRepositoryImpl() {
    }

    @Override
    public void save(TaskVO tarea) throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            this.sentence = "INSERT INTO tarea (nom_tarea, fecha_creacion, fecha_entrega, id_grupo) VALUES ('" + tarea.getTaskName() + "', CURDATE(), '" + tarea.getDueDate() + "', " + tarea.getGroupId() + ");";
            this.stmt.executeUpdate(this.sentence);
            this.stmt.close();
            this.connection.disconnectDB(conn);
        } catch (SQLException e) {
            System.out.println(e);
            throw new TaskException("Operation couldn't be made");
        }
    }


    @Override
    public void update(TaskVO task) throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            String sql = String.format("UPDATE tarea SET nom_tarea = '%s', fecha_creacion = '%s', fecha_entrega = '%s', id_grupo = %d WHERE id_tarea = %d", task.getTaskName(), task.getCreationDate(), task.getDueDate(), task.getGroupId(), task.getTaskId());
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new TaskException("Edit couldn't be made");
        }
    }

    @Override
    public ArrayList<TaskVO> load() throws TaskException {
        try {
            Connection conn = this.connection.connectDB();
            this.list = new ArrayList<>();
            this.stmt = conn.createStatement();
            this.sentence = "SELECT * FROM tarea";
            ResultSet rs = this.stmt.executeQuery(this.sentence);
            while (rs.next()) {
                int taskId = rs.getInt("id_tarea");
                String taskName = rs.getString("nom_tarea");
                String createDate = rs.getString("fecha_creacion");
                String dueDate = rs.getString("fecha_entrega");
                int groupdId = rs.getInt("id_grupo");
                this.task = new TaskVO(taskId, taskName, LocalDate.parse(createDate), LocalDate.parse(dueDate), groupdId);
                this.list.add(this.task);
            }
            this.connection.disconnectDB(conn);
            return this.list;
        } catch (SQLException e) {
            throw new TaskException("Operation couldn't be made");
        }
    }

    @Override
    public int lastId() throws TaskException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB();
            Statement command = conn.createStatement();
            ResultSet rs = command.executeQuery("SELECT id_tarea FROM tarea ORDER BY id_tarea DESC LIMIT 1");
            while (rs.next()) {
                id = rs.getInt("id_tarea");
            }
            return id;
        } catch (SQLException e) {
            throw new TaskException("ID search couldn't be made");
        }
    }
}