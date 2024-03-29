package com.psp.gestionproyecto.util;

import com.psp.gestionproyecto.model.Group;
import com.psp.gestionproyecto.model.GroupVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class GroupConverter {

    /**
     * Converts a GroupVO list to a Group list.
     *
     * @param list GroupVO list to convert.
     * @return An ObservableList of Group.
     */
    public static ObservableList<Group> groupVOToGroupConverter(ArrayList<GroupVO> list) {
        ObservableList<Group> groups = FXCollections.observableArrayList();
        for (GroupVO groupVO : list) {
            Group group = new Group();
            group.setId_group(groupVO.getId_group());
            group.setGroup_name(groupVO.getGroup_name());
            group.setGroup_description(groupVO.getGroup_description());
            groups.add(group);
        }
        return groups;
    }

    public static Group groupVOToGroupConverter(GroupVO groupVO){
        int id_group = groupVO.getId_group();
        String groupName = groupVO.getGroup_name();
        String groupDesc = groupVO.getGroup_description();
        return new Group(id_group, groupName, groupDesc);
    }

    /**
     * Converts a Group to a GroupVO.
     *
     * @param group The Group to convert.
     * @return The resulting GroupVO.
     */
    public static GroupVO groupToGroupVOConverter(Group group) {
        int id_group = group.getId_group();
        String group_name = group.getGroup_name();
        String group_description = group.getGroup_description();
        return new GroupVO(id_group, group_name, group_description);
    }
}
