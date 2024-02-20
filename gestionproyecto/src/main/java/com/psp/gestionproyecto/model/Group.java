package com.psp.gestionproyecto.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Group {

    private final IntegerProperty id_group;
    private final StringProperty group_name;
    private final StringProperty group_description;

    public Group() {
        this.id_group = new SimpleIntegerProperty();
        this.group_name = new SimpleStringProperty();
        this.group_description = new SimpleStringProperty();
    }

    public Group(int id_group, String group_name, String group_description) {
        this.id_group = new SimpleIntegerProperty(id_group);
        this.group_name = new SimpleStringProperty(group_name);
        this.group_description = new SimpleStringProperty(group_description);
    }

    public IntegerProperty id_groupProperty() {
        return id_group;
    }

    public int getId_group() {
        return id_group.get();
    }

    public void setId_group(int id_group) {
        this.id_group.set(id_group);
    }

    public StringProperty group_nameProperty() {
        return group_name;
    }

    public String getGroup_name() {
        return group_name.get();
    }

    public void setGroup_name(String group_name) {
        this.group_name.set(group_name);
    }

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