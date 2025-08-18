package de.fherfurt.taskvault.data.core;

import de.fherfurt.taskvault.data.daos.*;
import de.fherfurt.taskvault.data.repositories.*;
import lombok.Getter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

/**
 * The DataController class is a singleton responsible for managing the creation
 * and provision
 * of repositories and DAOs (Data Access Objects) in the application. It sets up
 * the
 * EntityManagerFactory, initializes repositories, and ensures that the same
 * instance of
 * the DataController is used throughout the application.
 */
public class DataController {
    private static final Logger LOGGER = Logger.getLogger(DataController.class.getSimpleName());
    private static final String PERSISTENCE_UNIT_NAME = resolvePersistenceUnit();

    private static String resolvePersistenceUnit() {
        String env = System.getenv("PERSISTENCE_UNIT");
        if (env != null && !env.isBlank()) {
            LOGGER.info("Using persistence unit (from ENV): " + env);
            return env;
        }
        String prop = System.getProperty("persistenceUnit");
        if (prop != null && !prop.isBlank()) {
            LOGGER.info("Using persistence unit (from System Property): " + prop);
            return prop;
        }
        LOGGER.info("Using persistence unit (default): taskvault-unit");
        return "taskvault-unit";
    }

    private final EntityManagerFactory entityManagerFactory;

    @Getter
    private final IMainTaskRepository mainTaskRepository;
    @Getter
    private final ITaskRepository taskRepository;
    @Getter
    private final ICategoryRepository categoryRepository;

    private static DataController INSTANCE;
    
    /**
     * Returns the singleton instance of the DataController.
     * If the instance does not exist, it is created.
     *
     * @return the singleton instance of DataController
     */
    public static DataController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataController();
        }
        return INSTANCE;
    }

    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes the EntityManagerFactory and creates the repositories.
     */

    private DataController() {
        LOGGER.info("Init Data Controller");

        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        LOGGER.info("Create RepositoryImpl");
        this.mainTaskRepository = new MainTaskRepository(this.getMainTaskDao(), null);
        this.taskRepository = new TaskRepository(this.getTaskDao());
        this.categoryRepository = new CategoryRepository(this.getCategoryDao());

        LOGGER.info("Create Test Data");
    }

    /**
     * Creates a new instance of JpaMainTaskDao using the EntityManagerFactory.
     *
     * @return a new JpaMainTaskDao instance
     */
    public IMainTaskDao getMainTaskDao() {
        return new JpaMainTaskDao(this.entityManagerFactory.createEntityManager());
    }

    /**
     * Creates a new instance of JpaTaskDao using the EntityManagerFactory.
     *
     * @return a new JpaTaskDao instance
     */
    public ITaskDao getTaskDao() {
        return new JpaTaskDao(this.entityManagerFactory.createEntityManager());
    }

    public ICategoryDao getCategoryDao() {
        return new JpaCategoryDao(this.entityManagerFactory.createEntityManager());
    }

}
