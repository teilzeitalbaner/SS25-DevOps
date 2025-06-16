package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.models.MainTask;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class JpaMainTaskDao extends JpaGenericDao<MainTask> implements IMainTaskDao {

    public JpaMainTaskDao(EntityManager em) {
        super(MainTask.class, em);
    }

    @Override
    public MainTask findByName(String taskname) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e WHERE e.taskname = ?1"
        );
        query.setParameter(1, taskname);

        List<MainTask> resultList = (List<MainTask>) query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public MainTask findByIsCompleted(boolean isCompleted) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e WHERE e.isCompleted = ?1"
        );
        query.setParameter(1, isCompleted);

        List<MainTask> resultList = (List<MainTask>) query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<MainTask> getCriteriaQueryAsc(String criteria){
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();

        CriteriaQuery<MainTask> criteriaQuery = criteriaBuilder.createQuery( MainTask.class );
        Root<MainTask> from = criteriaQuery.from( MainTask.class );
        criteriaQuery.select(from);
        criteriaQuery.orderBy(criteriaBuilder.asc(from.get(criteria)));

        return this.getEntityManager().createQuery( criteriaQuery ).getResultList();
    }

    public List<MainTask> getCriteriaQueryDesc(String criteria){
        CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();

        CriteriaQuery<MainTask> criteriaQuery = criteriaBuilder.createQuery( MainTask.class );
        Root<MainTask> from = criteriaQuery.from( MainTask.class );
        criteriaQuery.select(from);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get(criteria)));

        return this.getEntityManager().createQuery( criteriaQuery ).getResultList();
    }

    @Override
    public List<MainTask> findSortedByPriorityAsc() {
        return getCriteriaQueryAsc("priority");
    }

    @Override
    public List<MainTask> findSortedPriorityDesc() {
        return getCriteriaQueryDesc("priority");
    }

    @Override
    public List<MainTask> findSortedByCreationDateAsc() {
        return getCriteriaQueryAsc("creationDate");
    }

    @Override
    public List<MainTask> findSortedByCreationDateDesc() {
        return getCriteriaQueryDesc("creationDate");
    }

    @Override
    public List<MainTask> findSortedByDueDateAsc() {
        return getCriteriaQueryAsc("dueDate");
    }

    @Override
    public List<MainTask> findSortedByDueDateDesc() {
        return getCriteriaQueryDesc("dueDate");
    }
}
