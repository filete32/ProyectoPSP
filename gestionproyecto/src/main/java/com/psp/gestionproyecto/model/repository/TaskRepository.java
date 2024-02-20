package com.psp.gestionproyecto.model.repository;

import com.psp.gestionproyecto.model.TaskVO;
import com.psp.gestionproyecto.model.TaskException;

import java.util.ArrayList;

public interface TaskRepository {
    /**
     * Save a new task.
     *
     * @param task The task to save.
     * @throws TaskException If an error occurs while saving the task.
     */
    void save(TaskVO task) throws TaskException;

    /**
     * Update the information of an existing task.
     *
     * @param task The task to update.
     * @throws TaskException If an error occurs while updating the task.
     */
    void update(TaskVO task) throws TaskException;

    /**
     * Load all stored tasks.
     *
     * @return A list of all stored tasks.
     * @throws TaskException If an error occurs while loading the tasks.
     */
    ArrayList<TaskVO> load() throws TaskException;

    /**
     * Get the last used task ID.
     *
     * @return The last used task ID.
     * @throws TaskException If an error occurs while getting the last task ID.
     */
    int lastId() throws TaskException;
}
