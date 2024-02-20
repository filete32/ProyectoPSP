package com.psp.gestionproyecto.model.repository;

import com.psp.gestionproyecto.model.GroupVO;
import com.psp.gestionproyecto.model.GroupException;

import java.util.ArrayList;

public interface GroupRepository {
    /**
     * Stores new group.
     *
     * @param group group to save.
     * @throws GroupException if error happens storing group.
     */
    void save(GroupVO group) throws GroupException;

    /**
     * Updates existing group info.
     *
     * @param group group to update.
     * @throws GroupException if error happens updating group.
     */
    void update(GroupVO group) throws GroupException;

    /**
     * Loads all stored groups.
     *
     * @return list with all groups.
     * @throws GroupException if error happens loading groups.
     */
    ArrayList<GroupVO> load() throws GroupException;

    /**
     * Obtains last id from used group.
     *
     * @return last id from used group.
     * @throws GroupException if error happens obtaining last id.
     */
    int lastId() throws GroupException;
}
