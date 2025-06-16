package de.fherfurt.taskvault.models;

import de.fherfurt.taskvault.data.core.AbstractDatabaseEntity;
import lombok.*;

import javax.persistence.Entity;

/**
 * Represents a category entity in the task management system.
 * Each Category has a unique name and can be used to group tasks under a specific label.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "category")
public class Category extends AbstractDatabaseEntity {
    private String categoryName;
}
