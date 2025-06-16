package de.fherfurt.taskvault.api.models;

import de.fherfurt.taskvault.core.Priority;
import de.fherfurt.taskvault.models.Category;
import de.fherfurt.taskvault.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data needed to create a new mainTask.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewMainTaskDto {
    private String taskName;
    private String taskDescription;
    private String creationDate;
    private String dueDate;
    private String priority;
    private List<Task> subTasks;
    private boolean isDailyTask;
    private String category;
    private boolean isCompleted;
}
