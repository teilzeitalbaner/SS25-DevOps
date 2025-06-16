package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.Task;

public interface ITaskDao extends IGenericDao<Task> {
    Task findByName(String taskName);
    Task findIsCompleted(boolean isCompleted);
}
