package de.fherfurt.taskvault.models;

import de.fherfurt.taskvault.core.Priority;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a main task in the task management system.
 * A MainTask extends {@link Task} and includes additional attributes such as description,
 * creation and due dates, priority, and categorization.
 * It also supports managing a list of sub-tasks and associating itself with a category.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "mainTask")
public class MainTask extends Task {

    private String taskDescription;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private Priority priority;
    private boolean isDailyTask;

    @OneToMany
    private List<Task> subTasks;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    public MainTask(String taskDescription, LocalDate creationDate,
                    LocalDate dueDate, Priority priority,
                    List<Task> subTasks, boolean isDailyTask,
                    Category category, String taskName,
                    boolean isCompleted) {
        super(taskName, isCompleted, null);
        this.taskDescription = taskDescription;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.subTasks = subTasks;
        this.isDailyTask = isDailyTask;
        this.category = category;
    }

    /**
     * Create and add a new Task in ArrayList subTasks
     *
     * @param taskName, the name of the subTask
     */
    public void addSubTask(String taskName) {
        this.subTasks.add(new Task(taskName, false, this));
    }

    /**
     * Searches a subTask in local subTasks by its taskName and returns it if found.
     * Returns null if not found.
     *
     * @param taskName of searched subTask
     * @return found subTask, return null if not found
     */
    public Task getTaskByName(String taskName) {
        for (Task subTask : this.subTasks) {
            if (subTask.getTaskName().equals(taskName)) {
                return subTask;
            }
        }
        return null;
    }

    /**
     * Delete a subTask by its taskName using the Method getTaskByName
     *
     * @param taskName, the name of the subTask to be deleted
     */
    public void deleteSubTask(String taskName) {
        if (!subTasks.isEmpty()) {
            this.subTasks.remove(getTaskByName(taskName));
        }
    }

}
