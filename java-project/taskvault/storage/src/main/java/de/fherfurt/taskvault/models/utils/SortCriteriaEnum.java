package de.fherfurt.taskvault.models.utils;

import de.fherfurt.taskvault.models.MainTask;
import lombok.Getter;

import java.util.Comparator;

/**
 * Enum representing different sorting criteria for MainTask entities.
 * Each enum constant provides a specific comparator that can be used to sort a list of MainTask objects
 * based on various attributes such as priority, creation date, or due date, either in ascending or descending order.
 */
@Getter
public enum SortCriteriaEnum {

    /**
     * Sorts MainTasks by priority in ascending order.
     */
    PRIORITY_MAINTASK_ASC(Comparator.comparing(MainTask::getPriority)),

    /**
     * Sorts MainTasks by priority in descending order.
     */
    PRIORITY_MAINTASK_DESC(PRIORITY_MAINTASK_ASC.getComparator().reversed()),

    /**
     * Sorts MainTasks by creation date in ascending order.
     */
    CREATIONDATE_MAINTASK_ASC(Comparator.comparing(MainTask::getCreationDate)),

    /**
     * Sorts MainTasks by creation date in descending order.
     */
    CREATIONDATE_MAINTASK_DESC(CREATIONDATE_MAINTASK_ASC.getComparator().reversed()),

    /**
     * Sorts MainTasks by due date in ascending order.
     */
    DUEDATE_MAINTASK_ASC(Comparator.comparing(MainTask::getDueDate)),

    /**
     * Sorts MainTasks by due date in descending order.
     */
    DUEDATE_MAINTASK_DESC(DUEDATE_MAINTASK_ASC.getComparator().reversed());

    /**
     * The comparator used to sort MainTask entities according to the specified criterion.
     */
    private final Comparator<? super MainTask> comparator;

    /**
     * Constructs a SortCriteriaEnum with the specified comparator.
     *
     * @param comparator the comparator to use for sorting MainTask entities
     */
    SortCriteriaEnum(Comparator<? super MainTask> comparator) {
        this.comparator = comparator;
    }
}
