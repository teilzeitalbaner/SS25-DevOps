package de.fherfurt.taskvault.data.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * AbstractDatabaseEntity is a base class for all database entities, providing
 * common fields and behaviors such as ID, creation date, and modification date.
 * This class is intended to be extended by other entity classes in the application.
 *
 * This class uses JPA annotations to map the entity to a database table with an
 * inheritance strategy of TABLE_PER_CLASS. It also automatically handles the creation
 * and update timestamps through JPA lifecycle callbacks.
 *
 * Key Features:
 * - id: The primary key of the entity, generated using the TABLE strategy.
 * - created: The timestamp indicating when the entity was created.
 * - modified: The timestamp indicating when the entity was last modified.
 *
 *  Lifecycle Callback Methods:
 * - onCreate: Automatically sets the creation and modification timestamps when the entity is first persisted.
 * - onUpdate: Automatically updates the modification timestamp when the entity is updated.
 *
 * @version 1.0
 * @since 2024-08-21
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance( strategy =  InheritanceType.TABLE_PER_CLASS )
public abstract class AbstractDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    /**
     * Callback method that is automatically called before the entity is persisted to set
     * the creation and last modified timestamps.
     */
    @PrePersist
    public void onCreate() {
        this.created = new Date();
        this.modified = new Date();
    }

    /**
     * Callback method that is automatically called before the entity is updated to update
     * the last modified timestamp.
     */
    @PreUpdate
    public void onUpdate() {
        this.modified = new Date();
    }
}
