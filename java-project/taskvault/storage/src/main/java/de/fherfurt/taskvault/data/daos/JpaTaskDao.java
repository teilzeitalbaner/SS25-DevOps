package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.Task;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class JpaTaskDao extends JpaGenericDao<Task> implements ITaskDao{

    public JpaTaskDao(EntityManager em) {
        super(Task.class, em);
    }

    @Override
    public Task findByName(String name) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e WHERE e.username = ?1"
        );
        query.setParameter(1, name);

        List<Task> resultList = (List<Task>) query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public Task findIsCompleted(boolean isCompleted) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e WHERE e.isCompleted = ?1"
        );
        query.setParameter(1, isCompleted);

        List<Task> resultList = (List<Task>) query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
