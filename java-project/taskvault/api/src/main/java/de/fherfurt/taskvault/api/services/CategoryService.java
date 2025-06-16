package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.data.core.DataController;
import de.fherfurt.taskvault.data.repositories.ICategoryRepository;
import de.fherfurt.taskvault.models.Category;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing categories.
 * Implements the ICategoryService interface and provides methods for
 * CRUD operations on Category objects using an ICategoryRepository.
 *
 * @see ICategoryService
 * @see ICategoryRepository
 * @see Category
 */
public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;

    /**
     * Constructs a new CategoryService and initializes the
     * ICategoryRepository using the DataController.
     */
    public CategoryService() {
        this.categoryRepository = DataController
                .getInstance()
                .getCategoryRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        Category category = categoryRepository.getCategory(categoryId);

        return Optional.ofNullable(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addCategory(Category category) {
        return categoryRepository.createCategory(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCategory(int categoryId) {
        return categoryRepository.deleteCategory(categoryId);
    }
}
