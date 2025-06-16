package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class JpaCategoryDao extends JpaGenericDao<Category> implements ICategoryDao{

    public JpaCategoryDao(EntityManager em) {
        super(Category.class, em);
    }

    @Override
    public Category findByName(String categoryname) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e WHERE e.username = ?1"
        );
        query.setParameter(1, categoryname);

        List<Category> resultList = (List<Category>) query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    /**
     *
     * @param categoryName
     */
    @Override
    public void addCategory(String categoryName) {
        create(new Category(categoryName));
    }

}
