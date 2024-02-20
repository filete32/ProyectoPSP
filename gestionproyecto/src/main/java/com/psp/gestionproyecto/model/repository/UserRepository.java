package com.psp.gestionproyecto.model.repository;

import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.UserException;

import java.util.ArrayList;

public interface UserRepository {
    /**
     * Saves a new user.
     *
     * @param user The user to save.
     * @throws UserException If an error occurs while saving the user.
     */
    void save(UserVO user) throws UserException;

    /**
     * Updates the information of an existing user.
     *
     * @param user The user to update.
     * @throws UserException If an error occurs while updating the user.
     */
    void update(UserVO user) throws UserException;

    /**
     * Checks if a user has administrator privileges.
     *
     * @param user The user to check.
     * @return true if the user is an administrator, false otherwise.
     * @throws UserException If an error occurs while performing the check.
     */
    boolean isAdmin(UserVO user) throws UserException;

    /**
     * Loads all stored users.
     *
     * @return A list of all stored users.
     * @throws UserException If an error occurs while loading users.
     */
    ArrayList<UserVO> load() throws UserException;

    /**
     * Gets the last used user ID.
     *
     * @return The last used user ID.
     * @throws UserException If an error occurs when getting the last user ID.
     */

    int lastId() throws UserException;

    /**
     * Obtains user by username.
     *
     * @param username user to search.
     * @return UserVO object corresponding to the username.
     * @throws UserException If error occurs obtaining user.
     */
    UserVO getUserByUsername(String username) throws UserException;
}
