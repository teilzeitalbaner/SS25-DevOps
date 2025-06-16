package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.data.repositories.IMainTaskRepository;
import de.fherfurt.taskvault.data.repositories.MainTaskRepository;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the service for managing mainTasks and their subtasks.
 */
public interface IMainTaskService {
    /**
     * Retrieves all mainTasks.
     *
     * @return a list of all mainTask objects.
     */
    List<MainTask> getAllMainTasks();

    /**
     * Retrieves a mainTask by its ID.
     *
     * @param mainTaskId the ID of the mainTask to be retrieved.
     * @return mainTask if found, empty if not found
     */
    Optional<MainTask> getMainTaskById(int mainTaskId);

    /**
     * Adds a new mainTask.
     *
     * @param mainTask the mainTask object to be added.
     * @return true if the mainTask was successfully added, false otherwise.
     */
    boolean addMainTask(MainTask mainTask);

    /**
     * Updates an existing mainTask.
     *
     * @param mainTask the mainTask object with updated information.
     * @return true if the mainTask was successfully updated, false otherwise.
     */
    boolean updateMainTask(MainTask mainTask);

    /**
     * Deletes a mainTask by its ID.
     *
     * @param mainTaskId the ID of the mainTask to be deleted.
     * @return true if the mainTask was successfully deleted, false otherwise.
     */
    boolean deleteMainTask(int mainTaskId);

    /**
     * Retrieves all mainTasks sorted by a specific criterion.
     *
     * @param sortOrder the criterion determining the sort order.
     *        Possible params:  PRIORITY_MAINTASK_ASC
     *                          PRIORITY_MAINTASK_DESC
     *                          CREATIONDATE_MAINTASK_ASC
     *                          CREATIONDATE_MAINTASK_DESC
     *                          DUEDATE_MAINTASK_ASC
     *                          DUEDATE_MAINTASK_ASC
     * @return a list of sorted main task objects.
     */
    List<MainTask> getMainTasksSortedBy(SortCriteriaEnum sortOrder);

}
