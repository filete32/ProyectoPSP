package com.psp.gestionproyecto.model.repository;

import com.psp.gestionproyecto.model.UserVO;
import com.psp.gestionproyecto.model.UserException;

import java.util.ArrayList;

public interface UserRepository {
    /**
     * Save a new user.
     *
     * @param user The user to save.
     * @throws UserException If an error occurs while saving the user.
     */
    void save(UserVO user) throws UserException;

    /**
     * Update the information of an existing user.
     *
     * @param user The user to update.
     * @throws UserException If an error occurs while updating the user.
     */
    void update(UserVO user) throws UserException;

    /**
     * Check if a user has administrator privileges.
     *
     * @param user The user to check.
     * @return true if the user is an administrator, false otherwise.
     * @throws UserException If an error occurs while performing the check.
     */
    boolean isAdmin(UserVO user) throws UserException;

    /**
     * Load all stored users.
     *
     * @return A list of all stored users.
     * @throws UserException If an error occurs while loading the users.
     */
    ArrayList<UserVO> load() throws UserException;

    /**
     * Get the last used user ID.
     *
     * @return The last used user ID.
     * @throws UserException If an error occurs while getting the last user ID.
     */
    int lastId() throws UserException;

    /**
     * Get a user by their username.
     *
     * @param username The username to search for.
     * @return The UserVO object corresponding to the provided username.
     * @throws UserException If an error occurs while searching for the user.
     */
    UserVO getUserByUsername(String username) throws UserException;
}
