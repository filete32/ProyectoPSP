package com.psp.gestionproyecto.model.repository;

import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.GroupException;

import java.util.ArrayList;

public interface GroupRepository {
    /**
     * Save a new group.
     *
     * @param group The group to save.
     * @throws GroupException If an error occurs while saving the group.
     */
    void save(GroupVO group) throws GroupException;

    /**
     * Update the information of an existing group.
     *
     * @param group The group to update.
     * @throws GroupException If an error occurs while updating the group.
     */
    void update(GroupVO group) throws GroupException;

    /**
     * Load all stored groups.
     *
     * @return A list of all stored groups.
     * @throws GroupException If an error occurs while loading the groups.
     */
    ArrayList<GroupVO> load() throws GroupException;

    /**
     * Get the last used group ID.
     *
     * @return The last used group ID.
     * @throws GroupException If an error occurs while getting the last group ID.
     */
    int lastId() throws GroupException;
}
