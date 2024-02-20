package com.psp.gestionproyecto.model.repository.impl;

import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.UserException;
import com.psp.gestionproyecto.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;

public class UserRepositoryImpl implements UserRepository {

    private final JDBCConnection connection = new JDBCConnection();
    private Statement stmt;
    private String query;
    private ArrayList<UserVO> list;
    private UserVO user;

    public UserRepositoryImpl() {
    }

    @Override
    public void save(UserVO user) throws UserException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            this.query = "INSERT INTO usuario (nom_usuario, password, mail, admin, id_grupo) VALUES ('" + user.getUsername() + "','" + user.getPassword() + "','" + user.getEmail() + "'," + (user.isAdmin() ? 1 : 0) + "," + user.getGroupId() + ");";
            this.stmt.executeUpdate(this.query);
            this.stmt.close();
            this.connection.disconnectDB(conn);
        } catch (SQLException e) {
            System.out.println(e);
            throw new UserException("Operation could not be performed");
        }
    }

    @Override
    public void update(UserVO user) throws UserException {
        try {
            Connection conn = this.connection.connectDB();
            this.stmt = conn.createStatement();
            String sql = String.format("UPDATE usuario SET nom_usuario = '%s', password = '%s', mail = '%s', admin = %d, id_grupo = %d WHERE id_usuario = %d", user.getUsername(), user.getPassword(), user.getEmail(), (user.isAdmin() ? 1 : 0), user.getGroupId(), user.getUserId());
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new UserException("Edition could not be performed");
        }
    }

    @Override
    public boolean isAdmin(UserVO user) throws UserException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (user != null) {
                conn = this.connection.connectDB();
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
                throw new UserException("The user is null");
            }
        } catch (SQLException e) {
            throw new UserException("Error verifying administrator privileges: " + e.getMessage());
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


    @Override
    public ArrayList<UserVO> load() throws UserException {
        try {
            Connection conn = this.connection.connectDB();
            this.list = new ArrayList<>();
            this.stmt = conn.createStatement();
            this.query = "SELECT * FROM usuario";
            ResultSet rs = this.stmt.executeQuery(this.query);
            while (rs.next()) {
                int idUser = rs.getInt("id_usuario");
                String username = rs.getString("nom_usuario");
                String password = rs.getString("password");
                String email = rs.getString("mail");
                boolean isAdmin = rs.getBoolean("admin");
                int idGroup = rs.getInt("id_grupo");
                this.user = new UserVO(idUser, username, password, email, isAdmin, idGroup);
                this.list.add(this.user);
            }
            this.connection.disconnectDB(conn);
            return this.list;
        } catch (SQLException e) {
            throw new UserException("Operation could not be performed");
        }
    }

    @Override
    public int lastId() throws UserException {
        int id = 0;
        try {
            Connection conn = this.connection.connectDB();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_usuario FROM usuario ORDER BY id_usuario DESC LIMIT 1");
            while (resultSet.next()) {
                id = resultSet.getInt("id_usuario");
            }
            return id;
        } catch (SQLException e) {
            throw new UserException("ID search could not be performed");
        }
    }

    @Override
    public UserVO getUserByUsername(String username) throws UserException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserVO user = null;
        try {
            conn = this.connection.connectDB();
            String query = "SELECT * FROM usuario WHERE nom_usuario = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int idUser = rs.getInt("id_usuario");
                String name = rs.getString("nom_usuario");
                String password = rs.getString("password");
                String email = rs.getString("mail");
                boolean isAdmin = rs.getBoolean("admin");
                int idGroup = rs.getInt("id_grupo");
                user = new UserVO(idUser, name, password, email, isAdmin, idGroup);
            }
        } catch (SQLException e) {
            throw new UserException("Error getting user by username: " + e.getMessage());
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
