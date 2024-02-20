package com.psp.gestionproyecto.util;

import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class UserConverter {

    /**
     * Convert a list of UserVO to a list of User.
     *
     * @param list The list of UserVO to convert.
     * @return An ObservableList of User.
     */
    public static ObservableList<User> userVOToUserConverter(ArrayList<UserVO> list) {
        ObservableList<User> users = FXCollections.observableArrayList();
        for (UserVO userVO : list) {
            User user = new User();
            user.setUserId(userVO.getUserId());
            user.setUsername(userVO.getUsername());
            user.setPassword(userVO.getPassword());
            user.setEmail(userVO.getEmail());
            user.setAdmin(userVO.isAdmin());
            user.setGroupId(userVO.getGroupId());
            users.add(user);
        }
        return users;
    }

    /**
     * Convert a User to a UserVO.
     *
     * @param user The User to convert.
     * @return The resulting UserVO.
     */
    public static UserVO userToUserVOConverter(User user) {
        int userId = user.getUserId();
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        boolean isAdmin = user.isAdmin();
        int groupId = user.getGroupId();
        return new UserVO(userId, username, password, email, isAdmin, groupId);
    }
}
