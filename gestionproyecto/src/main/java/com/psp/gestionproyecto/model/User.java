package com.psp.gestionproyecto.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final IntegerProperty userId;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty email;
    private final BooleanProperty isAdmin;
    private final IntegerProperty groupId;

    public User() {
        this.userId = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.isAdmin = new SimpleBooleanProperty();
        this.groupId = new SimpleIntegerProperty();
    }

    public User(int userId, String username, String password, String email, boolean isAdmin, int groupId) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

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
