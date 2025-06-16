package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.core.Priority;
import de.fherfurt.taskvault.data.daos.JpaGenericDao;
import de.fherfurt.taskvault.data.daos.JpaTaskDao;
import de.fherfurt.taskvault.models.Category;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private static EntityManagerFactory entityManagerFactory;
    private JpaTaskDao taskDao;
    private TaskRepository taskRepository;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("taskvault-unit-test");
    }

    @BeforeEach
    void setUp() {
        EntityManager em = entityManagerFactory.createEntityManager();
        taskDao = new JpaTaskDao(em);

        taskRepository = new TaskRepository(taskDao);
    }

    @AfterEach
    void tearDown() {
        //Clean DB
        List<Task> allTasks = (List<Task>)taskDao.findAll();
        taskDao.delete(allTasks);

        // Clean up obj. for Gargbage Collection
        taskRepository = null;
        taskDao = null;
    }

    @Test
    void testCreateTask(){
        //Arrange
        Category category1 = new Category("Rezepte");
        List<Task> subTasks1 = new ArrayList<>();
        MainTask task1 = new MainTask("Irgendeine Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks1, false, category1,
                "Taskname 1", false);


        Category category2 = new Category("Kaffeesorten");
        List<Task> subTasks2 = new ArrayList<>();
        MainTask task2 = new MainTask("Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks2, false, category2,
                "Taskname 2", false);

        taskRepository.createTask(1, task1);
        taskRepository.createTask(2, task2);

        //Act
        List<Task> allTasks = taskRepository.getAllTasks();

        //Assert
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    void testGetAllTasks(){
        //Arrange
        Category category1 = new Category("Rezepte");
        List<Task> subTasks1 = new ArrayList<>();
        MainTask task1 = new MainTask("Irgendeine Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks1, false, category1,
                "Taskname 1", false);


        Category category2 = new Category("Kaffeesorten");
        List<Task> subTasks2 = new ArrayList<>();
        MainTask task2 = new MainTask("Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks2, false, category2,
                "Taskname 2", false);

        List<Task> subTasks3 = new ArrayList<>();
        MainTask task3 = new MainTask("Wieder Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks2, false, category1,
                "Taskname 3", false);

        taskRepository.createTask(1, task1);
        taskRepository.createTask(2, task2);
        taskRepository.createTask(3, task3);

        //Act
        List<Task> alLTasks = taskRepository.getAllTasks();

        //Assert
        assertEquals(3, alLTasks.size());
        assertTrue(alLTasks.contains(task1));
        assertTrue(alLTasks.contains(task2));
        assertTrue(alLTasks.contains(task3));
    }

    @Test
    void testGetTask(){
        //Arrange
        Category category = new Category("Kaffeesorten");
        List<Task> subTasks = new ArrayList<>();
        MainTask task1 = new MainTask("Eine Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 1", false);
        MainTask task2 = new MainTask("Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 2", false);

        taskRepository.createTask(1, task1);
        taskRepository.createTask(2, task2);

        //Act
        Task selectedTask = taskRepository.getTask(1, task1.getId());

        //Assert
        assertEquals(task1.getId(), selectedTask.getId());
        assertNotEquals(task2.getId(), selectedTask.getId());
    }

    @Test
    void testUpdateTask(){
        //Arrange
        Category category = new Category("Kaffeesorten");
        List<Task> subTasks = new ArrayList<>();
        MainTask task1 = new MainTask("Eine Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 1", false);
        MainTask task2 = new MainTask("Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 2", false);

        taskRepository.createTask(1, task1);
        taskRepository.createTask(2, task2);

        //Act
        String task1DescBefore = task1.getTaskDescription();
        Priority priorityBefore = task1.getPriority();
        task1.setTaskDescription("neue Beschreibung");
        task1.setPriority(Priority.HIGH_IMPORTANCE);
        boolean updatedResult = taskRepository.updateTask(1, task1);

        //Assert
        assertTrue(updatedResult);
        assertNotEquals(task1DescBefore, task1.getTaskDescription());
        assertNotEquals(priorityBefore, task1.getPriority());
    }

    @Test
    void testDeleteTask(){
        //Arrange
        Category category = new Category("Kaffeesorten");
        List<Task> subTasks = new ArrayList<>();
        MainTask task1 = new MainTask("Eine Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 1", false);
        MainTask task2 = new MainTask("Andere Beschreibung",
                LocalDate.now(), LocalDate.now().plusDays(10),
                Priority.LOW_IMPORTANCE, subTasks, false, category,
                "Taskname 2", false);

        taskRepository.createTask(1, task1);
        taskRepository.createTask(2, task2);

        //Act
        int taskSizeBefore = taskRepository.getAllTasks().size();
        boolean deletedResult = taskRepository.deleteTask(1, task1.getId());
        Task deleteTask = taskRepository.getTask(1, task1.getId());

        //Assert
        assertTrue(deletedResult);
        assertNull(deleteTask);
        assertNotEquals(taskSizeBefore, taskRepository.getAllTasks().size());
    }
}