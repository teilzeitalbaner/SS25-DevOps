package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.models.Category;

import java.util.List;

/**
 * ICategoryRepository provides an interface for managing Category entities in the data layer.
 * It defines the basic CRUD operations (Create, Read, Update, Delete) that can be performed
 * on Category entities.
 */
public interface ICategoryRepository {

    /**
     * Retrieves all Category entities from the repository.
     *
     * @return a list of all Category entities
     */
    List<Category> getAllCategory();

    /**
     * Retrieves a Category entity by its ID.
     *
     * @param categoryId the ID of the Category to retrieve
     * @return the Category entity with the specified ID, or null if not found
     */
    Category getCategory ( int categoryId );

    /**
     * Creates a new Category entity in the repository.
     *
     * @param category the Category entity to create
     * @return true if the Category was successfully created, false otherwise
     */
    boolean createCategory( Category category );

    /**
     * Updates an existing Category entity in the repository.
     *
     * @param category the Category entity with updated information
     * @return true if the Category was successfully updated, false otherwise
     */
    boolean updateCategory( Category category);

    /**
     * Deletes a Category entity from the repository by its ID.
     *
     * @param categoryId the ID of the Category to delete
     * @return true if the Category was successfully deleted, false otherwise
     */
    boolean deleteCategory( int categoryId );
}
