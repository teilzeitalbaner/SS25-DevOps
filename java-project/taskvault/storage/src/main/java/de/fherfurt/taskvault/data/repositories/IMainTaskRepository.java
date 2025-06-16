package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;

import java.util.List;

/**
 * IMainTaskRepository provides an interface for managing MainTask entities in the data layer.
 * It defines the basic CRUD operations (Create, Read, Update, Delete) that can be performed
 * on MainTask entities, as well as a method for retrieving sorted MainTask entities.
 */
public interface IMainTaskRepository {

    /**
     * Retrieves all MainTask entities from the repository.
     *
     * @return a list of all MainTask entities
     */
    List<MainTask> getAllMainTasks();

    /**
     * Retrieves a MainTask entity by its ID.
     *
     * @param mainTaskId the ID of the MainTask to retrieve
     * @return the MainTask entity with the specified ID, or null if not found
     */
    MainTask getMainTask ( int mainTaskId );

    /**
     * Creates a new MainTask entity in the repository.
     *
     * @param mainTask the MainTask entity to create
     * @return true if the MainTask was successfully created, false otherwise
     */
    boolean createMainTask( MainTask mainTask );

    /**
     * Updates an existing MainTask entity in the repository.
     *
     * @param mainTask the MainTask entity with updated information
     * @return true if the MainTask was successfully updated, false otherwise
     */
    boolean updateMainTask( MainTask mainTask );

    /**
     * Deletes a MainTask entity from the repository by its ID.
     *
     * @param mainTaskId the ID of the MainTask to delete
     * @return true if the MainTask was successfully deleted, false otherwise
     */
    boolean deleteMainTask( int mainTaskId );

    /**
     * Retrieves a list of MainTask entities sorted according to the specified criteria.
     *
     * @param sortOrder the criteria by which to sort the MainTask entities
     * @return a list of sorted MainTask entities
     */
    List<MainTask> getMainTasksSortedBy( SortCriteriaEnum sortOrder );
}
