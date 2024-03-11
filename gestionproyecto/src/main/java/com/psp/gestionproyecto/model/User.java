package com.psp.gestionproyecto.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The User class represents a user entity with user-related information.
 * It contains properties for user ID, username, password, email, admin status, and group ID.
 */
public class User {

    // User ID
    private final IntegerProperty userId;

    // User's username
    private final StringProperty username;

    // User's password
    private final StringProperty password;

    // User's email
    private final StringProperty email;

    // User's admin status
    private final BooleanProperty isAdmin;

    // User's group ID
    private final IntegerProperty groupId;

    /**
     * Default constructor for the User class.
     * Creates a User object with default attribute values.
     */
    public User() {
        this.userId = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.isAdmin = new SimpleBooleanProperty();
        this.groupId = new SimpleIntegerProperty();
    }

    /**
     * Parameterized constructor for the User class.
     * Creates a User object with the specified attribute values.
     *
     * @param userId   The unique identifier for the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email    The email address of the user.
     * @param isAdmin  The admin status of the user.
     * @param groupId  The identifier of the group to which the user belongs.
     */
    public User(int userId, String username, String password, String email, boolean isAdmin, int groupId) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

    // Getters and setters for the attributes

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public BooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin.get();
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public IntegerProperty groupIdProperty() {
        return groupId;
    }

    public int getGroupId() {
        return groupId.get();
    }

    public void setGroupId(int groupId) {
        this.groupId.set(groupId);
    }
}
