package com.psp.gestionproyecto.model.repository.impl;

import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.GroupException;
import com.psp.gestionproyecto.model.repository.GroupRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupRepositoryImpl implements GroupRepository {

    private final JDBCConnection connection = new JDBCConnection();
    private Statement stmt;
    private String sentence;
    private ArrayList<GroupVO> list;
    private GroupVO group;

    public GroupRepositoryImpl() {
    }

    @Override
    public void save(GroupVO group) throws GroupException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            this.sentence = "INSERT INTO grupo (nombre, descripcion) VALUES ('" + group.getGroup_name() + "','" + group.getGroup_description() + "');";
            this.stmt.executeUpdate(this.sentence);
            this.stmt.close();
            this.connection.disconnectDB(conn);
        } catch (SQLException e) {
            System.out.println(e);
            throw new GroupException("Operation couldn't be made");
        }
    }

    @Override
    public void update(GroupVO group) throws GroupException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            String sql = String.format("UPDATE grupo SET nombre = '%s', descripcion = '%s' WHERE id_grupo = %d", group.getGroup_name(), group.getGroup_description(), group.getId_group());
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new GroupException("Couldn't edit");
        }
    }

    @Override
    public ArrayList<GroupVO> load() throws GroupException {
        try {
            Connection conn = this.connection.connectDB();
            this.list = new ArrayList<>();
            this.stmt = conn.createStatement();
            this.sentence = "SELECT * FROM grupo";
            ResultSet rs = this.stmt.executeQuery(this.sentence);
            while (rs.next()) {
                int groupId = rs.getInt("id_grupo");
                String groupName = rs.getString("nombre");
                String groupDescription = rs.getString("descripcion");
                this.group = new GroupVO(groupId, groupName, groupDescription);
                this.list.add(this.group);
            }
            this.connection.disconnectDB(conn);
            return this.list;
        } catch (SQLException e) {
            throw new GroupException("Operation couldn't be made");
        }
    }

    @Override
    public int lastId() throws GroupException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB();
            Statement comando = conn.createStatement();
            ResultSet registro = comando.executeQuery("SELECT id_grupo FROM grupo ORDER BY id_grupo DESC LIMIT 1");
            while (registro.next()) {
                id = registro.getInt("id_grupo");
            }
            return id;
        } catch (SQLException e) {
            throw new GroupException("ID search could not be made");
        }
    }
}