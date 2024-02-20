package com.psp.gestionproyecto.model;

public class UserVO {

    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private int groupId;

    public UserVO() {
    }

    public UserVO(int userId, String username, String password, String email, boolean isAdmin, int groupId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}