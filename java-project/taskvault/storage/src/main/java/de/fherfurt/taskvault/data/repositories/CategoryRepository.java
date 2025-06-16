package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.data.daos.ICategoryDao;
import de.fherfurt.taskvault.models.Category;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * The CategoryRepository class provides an implementation of the ICategoryRepository interface.
 * It interacts with the data access layer (DAO) to perform CRUD operations on Category entities.
 * This class acts as a bridge between the business logic and the data access layer.
 */
@AllArgsConstructor
public class CategoryRepository implements ICategoryRepository {

    /** The DAO instance used to perform database operations on Category entities. */
    private final ICategoryDao categoryDao;


    /** {@inheritDoc} */
    @Override
    public List<Category> getAllCategory() {
        return (List<Category>)categoryDao.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public Category getCategory(int categoryId) {
        return categoryDao.findById(categoryId);
    }

    /** {@inheritDoc} */
    @Override
    public boolean createCategory(Category category) {
        return categoryDao.create(category);
    }

    /** {@inheritDoc} */
    @Override
    public boolean updateCategory(Category category) {
        Category updatedCategory = categoryDao.update(category);
        return updatedCategory.equals(category);
    }

    /** {@inheritDoc} */
    @Override
    public boolean deleteCategory(int categoryId) {
        return categoryDao.delete(categoryId);
    }
}
