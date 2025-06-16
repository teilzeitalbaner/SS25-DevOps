package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.models.Category;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the service for managing categories.
 * @author Luise Brumme
 */
public interface ICategoryService {
    /**
     * Retrieves all categories.
     *
     * @return a list of all Category objects.
     */
    List<Category> getAllCategories();

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId the ID of the category to be retrieved.
     * @return a Category or empty if not found.
     */
    Optional<Category> getCategoryById(int categoryId);

    /**
     * Adds a new category.
     *
     * @param category the Category object to add
     * @return true if the category was successfully added, false otherwise.
     */
    boolean addCategory(Category category);

    /**
     * Updates existing category
     * @param category with updated information
     * @return true if the category was successfully added, false otherwise.
     */
    boolean updateCategory(Category category);

    /**
     * Deletes Category by its ID
     * @param categoryId of category to be deleted
     * @return true if the category was successfully added, false otherwise.
     */
    boolean deleteCategory(int categoryId);
}
