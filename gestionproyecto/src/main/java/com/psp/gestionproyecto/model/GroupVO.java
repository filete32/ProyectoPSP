package com.psp.gestionproyecto.model;

public class GroupVO {

    private int id_group;
    private String group_name;
    private String group_description;

    public GroupVO() {
    }

    public GroupVO(int id_group, String group_name, String group_description) {
        this.id_group = id_group;
        this.group_name = group_name;
        this.group_description = group_description;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }
}
