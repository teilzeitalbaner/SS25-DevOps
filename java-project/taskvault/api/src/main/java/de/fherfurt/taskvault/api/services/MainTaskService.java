package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.data.core.DataController;
import de.fherfurt.taskvault.data.repositories.IMainTaskRepository;
import de.fherfurt.taskvault.data.repositories.MainTaskRepository;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing main tasks.
 * Implements the IMainTaskService interface and provides methods for CRUD operations on main task objects.
 */
public class MainTaskService implements IMainTaskService{

    private final IMainTaskRepository mainTaskRepository;

    /**
     * Constructs a new MainTaskService and initializes the IMainTaskRepository using the DataController.
     */
    public MainTaskService() {
        this.mainTaskRepository = DataController
                .getInstance()
                .getMainTaskRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MainTask> getAllMainTasks() {
        return mainTaskRepository.getAllMainTasks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MainTask> getMainTaskById(int mainTaskId) {
        MainTask mainTask = mainTaskRepository.getMainTask(mainTaskId);

        return Optional.ofNullable(mainTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMainTask(MainTask mainTask) {
        return mainTaskRepository.createMainTask(mainTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMainTask(MainTask mainTask) {
        return mainTaskRepository.updateMainTask(mainTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteMainTask(int mainTaskId) {
        return mainTaskRepository.deleteMainTask(mainTaskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MainTask> getMainTasksSortedBy(SortCriteriaEnum sortOrder) {
        return mainTaskRepository.getMainTasksSortedBy(sortOrder);
    }

    /**
     * {@inheritDoc}
     */
    public IMainTaskRepository getMainTaskRepostitory() {
        return this.mainTaskRepository;
    }

}
