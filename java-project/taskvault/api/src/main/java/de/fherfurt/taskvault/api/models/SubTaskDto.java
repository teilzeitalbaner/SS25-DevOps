package de.fherfurt.taskvault.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contains information about a subtask's name and completion status.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskDto {
    private String taskName;
    private boolean isCompleted;
}
