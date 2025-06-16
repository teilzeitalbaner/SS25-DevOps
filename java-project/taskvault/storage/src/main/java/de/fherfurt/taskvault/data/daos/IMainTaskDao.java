package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;

import java.util.List;

public interface IMainTaskDao extends IGenericDao<MainTask> {
    MainTask findByName(String taskName);
    MainTask findByIsCompleted(boolean isCompleted);

    List<MainTask> findSortedByPriorityAsc();
    List<MainTask> findSortedPriorityDesc();
    List<MainTask> findSortedByCreationDateAsc();
    List<MainTask> findSortedByCreationDateDesc();
    List<MainTask> findSortedByDueDateAsc();
    List<MainTask> findSortedByDueDateDesc();

}
