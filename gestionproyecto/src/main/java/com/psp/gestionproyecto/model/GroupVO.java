package com.psp.gestionproyecto.model;

import java.io.Serializable;

/**
 * The GroupVO class represents a Value Object (VO) for a group entity in the system.
 */
public class GroupVO implements Serializable {

    // Attributes representing the properties of a group
    private int id_group; // ID of the group
    private String group_name; // Name of the group
    private String group_description; // Description of the group

    /**
     * Default constructor for the GroupVO class.
     * Initializes the attributes with default values.
     */
    public GroupVO() {
    }

    /**
     * Parameterized constructor for the GroupVO class.
     * Initializes the attributes with specified values.
     *
     * @param id_group           The ID of the group.
     * @param group_name         The name of the group.
     * @param group_description  The description of the group.
     */
    public GroupVO(int id_group, String group_name, String group_description) {
        this.id_group = id_group;
        this.group_name = group_name;
        this.group_description = group_description;
    }

    // Getter and setter methods for id_group attribute
    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    // Getter and setter methods for group_name attribute
    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    // Getter and setter methods for group_description attribute
    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }
}
