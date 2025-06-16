package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.data.daos.IMainTaskDao;
import de.fherfurt.taskvault.data.daos.ITaskDao;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;
import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.List;

/**
 * The MainTaskRepository class provides an implementation of the IMainTaskRepository interface.
 * It interacts with the data access layer (DAO) to perform CRUD operations on MainTask entities,
 * as well as operations for retrieving sorted MainTask entities based on different criteria.
 */
@AllArgsConstructor
public class MainTaskRepository implements IMainTaskRepository{
    private final IMainTaskDao mainTaskDao;
    private final ITaskDao taskDao;

    /** The DAO instance used to perform database operations on MainTask entities. */
    @Override
    public List<MainTask> getAllMainTasks() {
        return new ArrayList<>(mainTaskDao.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MainTask getMainTask(int mainTaskId) {
        return mainTaskDao.findById(mainTaskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createMainTask(MainTask mainTask) {
        return mainTaskDao.create(mainTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMainTask(MainTask mainTask) {
        MainTask updatedMainTask = mainTaskDao.update(mainTask);
        return updatedMainTask.equals(mainTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteMainTask(int mainTaskId) {
        return mainTaskDao.delete(mainTaskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MainTask> getMainTasksSortedBy( SortCriteriaEnum sortOrder) {
        if( sortOrder == SortCriteriaEnum.PRIORITY_MAINTASK_ASC ) {
            return new ArrayList<>( this.mainTaskDao.findSortedByPriorityAsc() );
        } else if ( sortOrder == SortCriteriaEnum.PRIORITY_MAINTASK_DESC ){
            return new ArrayList<>( this.mainTaskDao.findSortedPriorityDesc() );
        } if( sortOrder == SortCriteriaEnum.CREATIONDATE_MAINTASK_ASC ) {
            return new ArrayList<>( this.mainTaskDao.findSortedByCreationDateAsc() );
        } else if ( sortOrder == SortCriteriaEnum.CREATIONDATE_MAINTASK_DESC ){
            return new ArrayList<>( this.mainTaskDao.findSortedByCreationDateDesc() );
        } if( sortOrder == SortCriteriaEnum.DUEDATE_MAINTASK_ASC ) {
            return new ArrayList<>( this.mainTaskDao.findSortedByDueDateAsc() );
        } else if ( sortOrder == SortCriteriaEnum.DUEDATE_MAINTASK_DESC ){
            return new ArrayList<>( this.mainTaskDao.findSortedByDueDateDesc() );
        } else {
            return new ArrayList<>(this.getAllMainTasks());
        }
    }
}
