package com.psp.gestionproyecto.util;

import com.psp.gestionproyecto.model.User;
import com.psp.gestionproyecto.model.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * The UserConverter class provides utility methods to convert between User and UserVO objects.
 */
public class UserConverter {

    /**
     * Converts a list of UserVO objects to a list of User objects.
     *
     * @param list List of UserVO to convert.
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
     * Converts a User object to a UserVO object.
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

    /**
     * Converts a UserVO object to a User object.
     *
     * @param userVO The UserVO to convert.
     * @return The resulting User.
     */
    public static User userVOTOUserConverter(UserVO userVO) {
        int userId = userVO.getUserId();
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        String email = userVO.getEmail();
        boolean isAdmin = userVO.isAdmin();
        int groupId = userVO.getGroupId();
        return new User(userId, username, password, email, isAdmin, groupId);
    }
}
