package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.Category;

public interface ICategoryDao extends IGenericDao<Category> {
    Category findByName(String categoryName);
    void addCategory(String categoryName);
}
