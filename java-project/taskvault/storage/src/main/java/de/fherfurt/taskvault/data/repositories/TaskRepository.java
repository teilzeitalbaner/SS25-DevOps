package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.data.daos.ITaskDao;
import de.fherfurt.taskvault.models.Task;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * The TaskRepository class provides an implementation of the ITaskRepository interface.
 * It interacts with the data access layer (DAO) to perform CRUD operations on Task entities.
 */
@AllArgsConstructor
public class TaskRepository implements ITaskRepository {

    private final ITaskDao taskDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getAllTasks() {
        return (List<Task>)taskDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getTask(int mainTaskId, int taskId) {
        return taskDao.findById(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createTask(int mainTaskId, Task task) {
        return taskDao.create(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateTask(int mainTaskId, Task task) {
        Task updatedTask = taskDao.update(task);
        return updatedTask.equals(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteTask(int mainTaskId, int taskId) {
        return taskDao.delete(taskId);
    }
}
