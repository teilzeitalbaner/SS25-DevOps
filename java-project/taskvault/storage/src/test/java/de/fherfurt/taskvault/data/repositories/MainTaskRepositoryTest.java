package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.core.Priority;
import de.fherfurt.taskvault.data.daos.JpaMainTaskDao;
import de.fherfurt.taskvault.data.daos.JpaTaskDao;
import de.fherfurt.taskvault.models.Category;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;
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

class MainTaskRepositoryTest {

    private static EntityManagerFactory entityManagerFactory;
    private JpaMainTaskDao mainTaskDao;
    private JpaTaskDao taskDao;
    private MainTaskRepository mainTaskRepository;

    @BeforeAll
    static void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("taskvault-unit-test");
    }


    @BeforeEach
    void setUp() {
        EntityManager em = entityManagerFactory.createEntityManager();
        mainTaskDao = new JpaMainTaskDao(em);

        mainTaskRepository = new MainTaskRepository(mainTaskDao, taskDao);
    }

    @AfterEach
    void tearDown() {
        // Clean DB
        List<MainTask> allMainTasks = (List<MainTask>) mainTaskDao.findAll();
        mainTaskDao.delete(allMainTasks);

        // Clean up objects, for Garbage Collection
        mainTaskRepository = null;
        mainTaskDao = null;
    }

    @Test
    void testGetAllMainTasks() {
        // Arrange
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

        mainTaskRepository.createMainTask(task1);
        mainTaskRepository.createMainTask(task2);

        //Act
        List<MainTask> allMainTasks = mainTaskRepository.getAllMainTasks();

        //Assert
        assertEquals(2, allMainTasks.size());
        assertTrue(allMainTasks.contains(task1));
        assertTrue(allMainTasks.contains(task2));
    }

    @Test
    void testGetMainTaskById(){
        // für diese Methode: public MainTask getMainTask(int mainTaskId)

        //Arrange
        Category category = new Category("Eissorten");
        List<Task> subTasks = new ArrayList<>();
        MainTask task = new MainTask("Eis Beschreibung", LocalDate.now(),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Eis-Task", false);
        mainTaskRepository.createMainTask(task);

        //Act
        MainTask retrievedMaintask = mainTaskRepository.getMainTask(task.getId());

        //Assert
        assertNotNull(retrievedMaintask);
        assertEquals(task.getTaskName(), retrievedMaintask.getTaskName());
    }

    @Test
    void testUpdateMainTask(){
        //Arrange
        Category category = new Category("Hausaufgaben");
        List<Task> subTasks = new ArrayList<>();
        MainTask task = new MainTask("Hausis", LocalDate.now(),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Matheaufgaben", false);
        mainTaskRepository.createMainTask(task);

        //Act
        task.setTaskName("Bioaufgaben");
        boolean updatedTaskName = mainTaskRepository.updateMainTask(task);
        MainTask updatedTaskId = mainTaskRepository.getMainTask(task.getId());

        //Assert
        assertTrue(updatedTaskName);
        assertEquals("Bioaufgaben", updatedTaskId.getTaskName());
    }

    @Test
    void testDeleteMainTask(){
        //Arrange
        Category category = new Category("Wassersorten");
        List<Task> subTasks = new ArrayList<>();
        MainTask task = new MainTask("Trinkwasser", LocalDate.now(),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Viel trinken", false);
        mainTaskRepository.createMainTask(task);

        //Act
        boolean deletedTaskId = mainTaskRepository.deleteMainTask(task.getId());
        MainTask deletedTask = mainTaskRepository.getMainTask(task.getId());

        //Assert
        assertTrue(deletedTaskId);
        assertNull(deletedTask);
    }

    @Test
    void getAllMainTasksSortedByPriority(){
        //Arrange
        Category category = new Category("Wassersorten");
        List<Task> subTasks = new ArrayList<>();

        MainTask task1 = new MainTask("Volvic", LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Wasser1", false);

        MainTask task2 = new MainTask("Vittel", LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(5), Priority.LOW_IMPORTANCE, subTasks,
                false, category, "Wasser2", false);

        MainTask task3 = new MainTask("G&G", LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(15), Priority.AVERAGE_IMPORTANCE, subTasks,
        false, category, "Wasser3", false);

        mainTaskRepository.createMainTask(task1);
        mainTaskRepository.createMainTask(task2);
        mainTaskRepository.createMainTask(task3);

        //Act
            //asc:
        List<MainTask> tasksSortedByPriorityAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_ASC);
            //desc:
        List<MainTask> tasksSortedByPriorityDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_DESC);

        //Assert - 1. Block asc, 2. Block desc
        assertEquals(Priority.LOW_IMPORTANCE, tasksSortedByPriorityAsc.get(0).getPriority());
        assertEquals(Priority.AVERAGE_IMPORTANCE, tasksSortedByPriorityAsc.get(1).getPriority());
        assertEquals(Priority.HIGH_IMPORTANCE, tasksSortedByPriorityAsc.get(2).getPriority());

        assertEquals(3, tasksSortedByPriorityDesc.size());
        assertEquals(Priority.HIGH_IMPORTANCE, tasksSortedByPriorityDesc.get(0).getPriority());
        assertEquals(Priority.AVERAGE_IMPORTANCE, tasksSortedByPriorityDesc.get(1).getPriority());
        assertEquals(Priority.LOW_IMPORTANCE, tasksSortedByPriorityDesc.get(2).getPriority());
    }

    @Test
    void testGetMainTasksSortedByCreationDate(){
        //Arrange
        Category category = new Category("Wassersorten");
        List<Task> subTasks = new ArrayList<>();

        MainTask task1 = new MainTask("Volvic", LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Wasser1", false);

        MainTask task2 = new MainTask("Vittel", LocalDate.now(),
                LocalDate.now().plusDays(5), Priority.LOW_IMPORTANCE, subTasks,
                false, category, "Wasser2", false);

        MainTask task3 = new MainTask("G&G", LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(15), Priority.AVERAGE_IMPORTANCE, subTasks,
                false, category, "Wasser3", false);

        mainTaskRepository.createMainTask(task1);
        mainTaskRepository.createMainTask(task2);
        mainTaskRepository.createMainTask(task3);

        //Act
        List<MainTask> tasksSortedByCreationDateAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_ASC);
        List<MainTask> tasksSortedByCreationDateDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_DESC);

        //Assert - 1. Block asc, 2. Block desc
        assertEquals(task1.getCreationDate(), tasksSortedByCreationDateAsc.get(0).getCreationDate());
        assertEquals(task3.getCreationDate(), tasksSortedByCreationDateAsc.get(1).getCreationDate());
        assertEquals(task2.getCreationDate(), tasksSortedByCreationDateAsc.get(2).getCreationDate());

        assertEquals(task2.getCreationDate(), tasksSortedByCreationDateDesc.get(0).getCreationDate());
        assertEquals(task3.getCreationDate(), tasksSortedByCreationDateDesc.get(1).getCreationDate());
        assertEquals(task1.getCreationDate(), tasksSortedByCreationDateDesc.get(2).getCreationDate());
    }


    @Test
    void testGetMainTasksSortedByDueDate(){
        //Arrange
        Category category = new Category("Wassersorten");
        List<Task> subTasks = new ArrayList<>();

        MainTask task1 = new MainTask("Volvic", LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Wasser1", false);

        MainTask task2 = new MainTask("Vittel", LocalDate.now(),
                LocalDate.now().plusDays(5), Priority.LOW_IMPORTANCE, subTasks,
                false, category, "Wasser2", false);

        MainTask task3 = new MainTask("G&G", LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(15), Priority.AVERAGE_IMPORTANCE, subTasks,
                false, category, "Wasser3", false);

        mainTaskRepository.createMainTask(task1);
        mainTaskRepository.createMainTask(task2);
        mainTaskRepository.createMainTask(task3);

        //Act
        List<MainTask> tasksSortedByDueDateAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_ASC);
        List<MainTask> tasksSortedByDueDateDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_DESC);

        //Assert
        assertEquals(3, tasksSortedByDueDateAsc.size());
        assertEquals(3, tasksSortedByDueDateDesc.size());
        assertEquals(task2.getDueDate(), tasksSortedByDueDateAsc.get(0).getDueDate());
        assertEquals(task3.getDueDate(), tasksSortedByDueDateDesc.get(0).getDueDate());
    }

    @Test
    void testGetMainTasksSortedByNothing(){
        //Arrange
        Category category = new Category("Wassersorten");
        List<Task> subTasks = new ArrayList<>();

        MainTask task1 = new MainTask("Volvic", LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, subTasks,
                false, category, "Wasser1", false);

        MainTask task2 = new MainTask("Vittel", LocalDate.now(),
                LocalDate.now().plusDays(5), Priority.LOW_IMPORTANCE, subTasks,
                false, category, "Wasser2", false);

        MainTask task3 = new MainTask("G&G", LocalDate.now().minusDays(2),
                LocalDate.now().plusDays(15), Priority.AVERAGE_IMPORTANCE, subTasks,
                false, category, "Wasser3", false);

        mainTaskRepository.createMainTask(task1);
        mainTaskRepository.createMainTask(task2);
        mainTaskRepository.createMainTask(task3);

        //Act
        List<MainTask> tasksSortedByNothing = mainTaskRepository.getMainTasksSortedBy(null);

        //Assert
        assertEquals(3, tasksSortedByNothing.size());
    }

}



