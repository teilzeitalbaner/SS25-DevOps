package de.fherfurt.taskvault.data.daos;

import de.fherfurt.taskvault.data.core.AbstractDatabaseEntity;
import de.fherfurt.taskvault.models.MainTask;

import java.util.Collection;
import java.util.List;

public interface IGenericDao<T extends AbstractDatabaseEntity> {
    T findById( int id );
    Collection<T> findAll();

    boolean create( T entity );
    boolean createAll( Collection<T> newEntities );

    T update( T entity );

    boolean delete( int id );
    boolean delete( T entity );
    boolean delete( List<T> entries );
}
