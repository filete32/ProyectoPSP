package com.psp.gestionproyecto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Group class represents a group entity in the system.
 */
public class Group {

    // Properties representing the attributes of a group
    private final IntegerProperty id_group;
    private final StringProperty group_name;
    private final StringProperty group_description;

    /**
     * Default constructor for the Group class.
     * Initializes properties with default values.
     */
    public Group() {
        this.id_group = new SimpleIntegerProperty();
        this.group_name = new SimpleStringProperty();
        this.group_description = new SimpleStringProperty();
    }

    /**
     * Parameterized constructor for the Group class.
     * Initializes properties with specified values.
     *
     * @param id_group          The ID of the group.
     * @param group_name        The name of the group.
     * @param group_description The description of the group.
     */
    public Group(int id_group, String group_name, String group_description) {
        this.id_group = new SimpleIntegerProperty(id_group);
        this.group_name = new SimpleStringProperty(group_name);
        this.group_description = new SimpleStringProperty(group_description);
    }

    // Getter and setter methods for id_group property
    public IntegerProperty id_groupProperty() {
        return id_group;
    }

    public int getId_group() {
        return id_group.get();
    }

    public void setId_group(int id_group) {
        this.id_group.set(id_group);
    }

    // Getter and setter methods for group_name property
    public StringProperty group_nameProperty() {
        return group_name;
    }

    public String getGroup_name() {
        return group_name.get();
    }

    public void setGroup_name(String group_name) {
        this.group_name.set(group_name);
    }

    // Getter and setter methods for group_description property
    public StringProperty group_descriptionProperty() {
        return group_description;
    }

    public String getGroup_description() {
        return group_description.get();
    }

    public void setGroup_description(String group_description) {
        this.group_description.set(group_description);
    }
}
