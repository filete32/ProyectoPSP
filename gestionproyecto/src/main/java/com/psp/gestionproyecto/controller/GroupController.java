package com.psp.gestionproyecto.controller;

import com.psp.gestionproyecto.model.Group;
import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.repository.impl.GroupRepositoryImpl;
import com.psp.gestionproyecto.model.GroupException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class GroupController {

    @FXML
    private TableView<Group> groupTable;
    @FXML
    private TableColumn<Group, Number> idColumn;
    @FXML
    private TableColumn<Group, String> groupNameColumn;
    @FXML
    private TableColumn<Group, String> descriptionColumn;
    @FXML
    private TextField groupNameInput;
    @FXML
    private TextField groupDescriptionInput;
    @FXML
    private Label groupNameLabel;
    @FXML
    private Label groupDescriptionLabel;
    @FXML
    private Label groupIdLabel;

    private final ObservableList<Group> groupList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id_group"));
        }
        if (groupNameColumn != null) {
            groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("group_name"));
        }
        if (descriptionColumn != null) {
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("group_description"));
        }

        groupTable.setItems(groupList);
        groupTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        loadGroupFromDB();
        groupTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                if (newValue != null) {
                    showSelectedGroup(newValue);
                }
            }
        });
    }

    private void showSelectedGroup(Group group) {
        groupIdLabel.setText(String.valueOf(group.getId_group()));
        groupNameLabel.setText(group.getGroup_name());
        groupDescriptionLabel.setText(group.getGroup_description());
    }



    private void loadGroupFromDB() {
        GroupRepositoryImpl groupRepository = new GroupRepositoryImpl();
        try {
            ArrayList<GroupVO> groupsVO = groupRepository.load();
            for (GroupVO groupVO : groupsVO) {
                Group group = new Group(groupVO.getId_group(), groupVO.getGroup_name(), groupVO.getGroup_description());
                groupList.add(group);
            }
        } catch (GroupException e) {
            showAlert("Error loading groups", e.getMessage());
        }
    }


    @FXML
    private void handleAddGroup() {
    }

    @FXML
    private void handleUpdateGroup() {
    }

    private void clearInput() {
        groupNameInput.clear();
        groupDescriptionInput.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
