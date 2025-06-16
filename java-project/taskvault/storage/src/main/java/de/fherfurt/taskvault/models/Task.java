package de.fherfurt.taskvault.models;

import de.fherfurt.taskvault.data.core.AbstractDatabaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Represents a task within a main task in the task management system.
 * Each Task has a name, a completion status, and is associated with a main task.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "task")
public class Task extends AbstractDatabaseEntity {
    private String taskName;
    private boolean isCompleted;
    @ManyToOne
    private MainTask mainTask;
}
