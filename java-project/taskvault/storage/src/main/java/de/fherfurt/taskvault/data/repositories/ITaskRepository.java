package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.models.Task;

import java.util.List;

/**
 * ITaskRepository provides an interface for managing Task entities within the data layer.
 * It defines the basic CRUD operations (Create, Read, Update, Delete) that can be performed
 * on Task entities, including operations that are specific to tasks within a MainTask.
 */
public interface ITaskRepository {

    /**
     * Retrieves all Task entities from the repository.
     *
     * @return a list of all Task entities
     */
    List<Task> getAllTasks();

    /**
     * Retrieves a Task entity by its ID and associated MainTask ID.
     *
     * @param mainTaskId the ID of the MainTask to which the Task belongs
     * @param taskId the ID of the Task to retrieve
     * @return the Task entity with the specified ID, or null if not found
     */
    Task getTask ( int mainTaskId, int taskId );

    /**
     * Creates a new Task entity within a specified MainTask.
     *
     * @param mainTaskId the ID of the MainTask to which the Task belongs
     * @param task the Task entity to create
     * @return true if the Task was successfully created, false otherwise
     */
    boolean createTask( int mainTaskId, Task task );

    /**
     * Updates an existing Task entity within a specified MainTask.
     *
     * @param mainTaskId the ID of the MainTask to which the Task belongs
     * @param task the Task entity with updated information
     * @return true if the Task was successfully updated, false otherwise
     */
    boolean updateTask( int mainTaskId, Task task );

    /**
     * Deletes a Task entity by its ID and associated MainTask ID.
     *
     * @param mainTaskId the ID of the MainTask to which the Task belongs
     * @param taskId the ID of the Task to delete
     * @return true if the Task was successfully deleted, false otherwise
     */
    boolean deleteTask( int mainTaskId, int taskId );
}
