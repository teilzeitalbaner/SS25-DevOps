package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.data.core.AbstractDatabaseEntity;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Getter
public class JpaGenericDao<T extends AbstractDatabaseEntity> implements IGenericDao<T> {
    private final Class<T> persistentClass;
    private EntityManager entityManager;

    public JpaGenericDao( Class<T> type, EntityManager em ) {
        this.persistentClass = type;
        this.entityManager = em;
    }

    public T findById(int id) {
        return getEntityManager().find(persistentClass, id);
    }
    public Collection<T> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM " + getPersistentClass().getCanonicalName() + " e"
        );
        return (Collection<T>) query.getResultList();
    }

    public boolean create(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist( entity );
        getEntityManager().getTransaction().commit();

        return true;
    }

    public boolean createAll(Collection<T> newEntities) {
        getEntityManager().getTransaction().begin();

        for( T entry : newEntities ) {
            getEntityManager().persist(entry);
        }

        getEntityManager().getTransaction().commit();

        return true;
    }

    public T update(T entity) {
        getEntityManager().getTransaction().begin();
        final T savedEntity = getEntityManager().merge( entity );
        getEntityManager().getTransaction().commit();

        return savedEntity;
    }


    public boolean delete(int id) {
        T entity = this.findById( id );
        return this.delete( entity );
    }

    public boolean delete(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove( entity );
        getEntityManager().getTransaction().commit();

        return true;
    }

    public boolean delete(List<T> entries) {
        getEntityManager().getTransaction().begin();

        for( T entry : entries ) {
            getEntityManager().remove(entry);
        }

        getEntityManager().getTransaction().commit();

        return true;
    }
}
