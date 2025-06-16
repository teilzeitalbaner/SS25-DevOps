package de.fherfurt.taskvault.api.models;

import de.fherfurt.taskvault.models.MainTask;
import lombok.*;

/**
 * Contains information about a category's ID and name.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int id;
    private String categoryName;
}
