package com.psp.gestionproyecto.model;

import java.io.Serializable;

/**
 * The UserVO class represents a User Value Object (VO) used for data transfer.
 * It implements Serializable to support serialization.
 */
public class UserVO implements Serializable {

    // Fields representing user attributes
    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private int groupId;

    /**
     * Default constructor for the UserVO class.
     * Initializes all fields to default values.
     */
    public UserVO() {
    }

    /**
     * Parameterized constructor for the UserVO class.
     * Initializes all fields with the specified values.
     *
     * @param userId    The user's ID.
     * @param username  The user's username.
     * @param password  The user's password.
     * @param email     The user's email address.
     * @param isAdmin   A boolean indicating if the user is an admin.
     * @param groupId   The ID of the group to which the user belongs.
     */
    public UserVO(int userId, String username, String password, String email, boolean isAdmin, int groupId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.groupId = groupId;
    }

    // Getter and setter methods for all fields

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
