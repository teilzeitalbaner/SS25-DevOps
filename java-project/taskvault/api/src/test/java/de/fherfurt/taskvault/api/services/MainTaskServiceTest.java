package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.core.Priority;
import de.fherfurt.taskvault.data.core.DataController;
import de.fherfurt.taskvault.data.repositories.IMainTaskRepository;
import de.fherfurt.taskvault.models.Category;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTaskServiceTest {

    private IMainTaskService mainTaskService;
    private IMainTaskRepository mainTaskRepository;

    @BeforeEach
    void setUp() {
        //Mock
        mainTaskRepository = mock(IMainTaskRepository.class);
        DataController dataController = mock(DataController.class);

        when(dataController.getMainTaskRepository()).thenReturn(mainTaskRepository);

        mainTaskService = new MainTaskService() {
        };
    }

    @AfterEach
    void tearDown() {
        mainTaskRepository = null;
        mainTaskService = null;
    }

    @Test
    void testGetAllMainTasks() {
        //Arrange
        MainTask task1 = mock(MainTask.class);
        MainTask task2 = mock(MainTask.class);
        List<MainTask> mainTasks = new ArrayList<>();

        mainTasks.add(task1);
        mainTasks.add(task2);
        when(mainTaskRepository.getAllMainTasks()).thenReturn(mainTasks);

        //Act
        List<MainTask> result = mainTaskRepository.getAllMainTasks();

        //Assert
        assertNotNull(result);
        assertEquals(2, mainTaskRepository.getAllMainTasks().size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
    }

    @Test
    void testGetMainTaskById() {
        //Arrange
        List<MainTask> tasks = new ArrayList<>();
        MainTask task3 = mock(MainTask.class);
        MainTask task4 = mock(MainTask.class);
        int task3Id = 0;
        int task4Id = 1;

        //Act
        when(mainTaskRepository.getMainTask(0)).thenReturn(task3);
        when(mainTaskRepository.getMainTask(0).getId()).thenReturn(0);
        when(mainTaskRepository.getMainTask(1)).thenReturn(task4);
        when(mainTaskRepository.getMainTask(1).getId()).thenReturn(1);
        when(mainTaskRepository.getAllMainTasks()).thenReturn(tasks);

        //Assert
        assertEquals(task3Id, mainTaskRepository.getMainTask(0).getId());
        assertEquals(task4Id, mainTaskRepository.getMainTask(1).getId());
    }

    @Test
    void testAddMainTask() {
        //Arrange
        MainTask task1 = mock(MainTask.class);
        MainTask task2 = mock(MainTask.class);
        List<MainTask> mainTasks = new ArrayList<>();

        when(mainTaskRepository.createMainTask(task1)).thenReturn(mainTasks.add(task1));
        when(mainTaskRepository.createMainTask(task2)).thenReturn(mainTasks.add(task2));
        when(mainTaskRepository.getAllMainTasks()).thenReturn(mainTasks);

        //Act
        int mainTaskSizeBefore = 0;

        //Assert
        assertEquals(mainTaskSizeBefore + 2, mainTaskRepository.getAllMainTasks().size());
        assertNotEquals(mainTaskSizeBefore, mainTaskRepository.getAllMainTasks().size());
    }

    @Test
    void testUpdateMainTask() {
        //Arrange
        List<MainTask> tasks = new ArrayList<>();

        MainTask task2 = mock(MainTask.class);
        MainTask task3 = mock(MainTask.class);

        when(mainTaskRepository.createMainTask(task2)).thenReturn(tasks.add(task2));
        when(mainTaskRepository.createMainTask(task3)).thenReturn(tasks.add(task3));
        when(mainTaskRepository.getAllMainTasks()).thenReturn(tasks);
        when(mainTaskRepository.updateMainTask(task3)).thenReturn(true);

        //Act
        int taskSizeBefore = mainTaskRepository.getAllMainTasks().size();
        boolean isUpdated = mainTaskRepository.updateMainTask(task3);

        //Assert
        assertTrue(isUpdated);
        assertEquals(taskSizeBefore, mainTaskRepository.getAllMainTasks().size());
    }

    @Test
    void testDeleteMainTask() {
        //Arrange
        List<MainTask> mainTasks = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        Category category = new Category("Irgendeine Kategorie");
        MainTask task1 = new MainTask("Mein Kopf schmerzt", LocalDate.now(),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, tasks,
                false, category, "Task1", false);

        when(mainTaskRepository.createMainTask(task1)).thenReturn(mainTasks.add(task1));
        when(mainTaskRepository.getAllMainTasks()).thenReturn(mainTasks);

        //Act

        int taskSizeBefore = mainTaskRepository.getAllMainTasks().size();
        when(mainTaskRepository.deleteMainTask(task1.getId())).thenReturn(true);
        boolean isDeleted = mainTaskRepository.deleteMainTask(task1.getId());
        int taskSizeAfter = mainTaskRepository.getAllMainTasks().size();

        //Assert
        assertTrue(isDeleted);
        assertEquals(taskSizeAfter, mainTaskRepository.getAllMainTasks().size());
    }

    @Test
    void testGetMainTaskSortedByPriority() {
        //Arrange
        List<Task> tasks = new ArrayList<>();
        Category category = new Category("Irgendeine Kategorie");
        MainTask task1 = new MainTask("Mein Kopf schmerzt", LocalDate.now(),
                LocalDate.now().plusDays(10), Priority.HIGH_IMPORTANCE, tasks,
                false, category, "Task1", false);
        MainTask task2 = new MainTask("Mein Kopf schmerzt mehr", LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(20), Priority.LOW_IMPORTANCE, tasks,
                false, category, "Task2", false);
        MainTask task3 = new MainTask("Mein Kopf schmerzt viel mehr", LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(12), Priority.AVERAGE_IMPORTANCE, tasks,
                false, category, "Task2", false);

        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_ASC)).thenReturn(Arrays.asList(task2, task3, task1));
        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_DESC)).thenReturn(Arrays.asList(task1, task2, task3));

        //Act - 1st-Asc, 2nd-Desc
        List<MainTask> mainTasksSortedByPriorityAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_ASC);
        List<MainTask> mainTasksSortedByPriorityDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.PRIORITY_MAINTASK_DESC);

        //Assert - 1st Block-asc, 2nd Block-desc
        assertEquals(3, mainTasksSortedByPriorityAsc.size());

        assertEquals(Priority.LOW_IMPORTANCE, mainTasksSortedByPriorityAsc.get(0).getPriority());
        assertEquals(Priority.AVERAGE_IMPORTANCE, mainTasksSortedByPriorityAsc.get(1).getPriority());
        assertEquals(Priority.HIGH_IMPORTANCE, mainTasksSortedByPriorityAsc.get(2).getPriority());

        assertEquals(Priority.HIGH_IMPORTANCE, mainTasksSortedByPriorityDesc.get(0).getPriority());
        assertEquals(Priority.AVERAGE_IMPORTANCE, mainTasksSortedByPriorityDesc.get(2).getPriority());
        assertEquals(Priority.LOW_IMPORTANCE, mainTasksSortedByPriorityDesc.get(1).getPriority());
    }

    @Test
    void testGetMainTaskSortedByCreationDate() {
        //Arrange
        List<Task> tasks = new ArrayList<>();
        Category category = new Category("Irgendeine Kategorie");
        MainTask task1 = new MainTask("Mein Kopf schmerzt",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                Priority.HIGH_IMPORTANCE, tasks,
                false, category, "Task1", false);
        MainTask task2 = new MainTask("Mein Kopf schmerzt mehr",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(20),
                Priority.LOW_IMPORTANCE, tasks,
                false, category, "Task2", false);
        MainTask task3 = new MainTask("Mein Kopf schmerzt viel mehr",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(12),
                Priority.AVERAGE_IMPORTANCE, tasks,
                false, category, "Task2", false);

        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_ASC)).thenReturn(Arrays.asList(task2, task1, task3));
        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_DESC)).thenReturn(Arrays.asList(task3, task1, task2));

        //Act - 1st-Asc, 2nd-Desc
        List<MainTask> mainTasksSortedByCreationDateAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_ASC);
        List<MainTask> mainTasksSortedByCreationDateDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.CREATIONDATE_MAINTASK_DESC);

        //Assert - 1st Block-asc, 2nd Block-desc
        assertEquals(3, mainTasksSortedByCreationDateAsc.size());

        assertEquals(task2.getCreationDate(), mainTasksSortedByCreationDateAsc.get(0).getCreationDate());
        assertEquals(task1.getCreationDate(), mainTasksSortedByCreationDateAsc.get(1).getCreationDate());
        assertEquals(task3.getCreationDate(), mainTasksSortedByCreationDateAsc.get(2).getCreationDate());

        assertEquals(task2.getCreationDate(), mainTasksSortedByCreationDateDesc.get(2).getCreationDate());
        assertEquals(task1.getCreationDate(), mainTasksSortedByCreationDateDesc.get(1).getCreationDate());
        assertEquals(task3.getCreationDate(), mainTasksSortedByCreationDateDesc.get(0).getCreationDate());
    }

    @Test
    void testGetMainTaskSortedByDueDate() {
        //Arrange
        List<Task> tasks = new ArrayList<>();
        Category category = new Category("Irgendeine Kategorie");
        MainTask task1 = new MainTask("Mein Kopf schmerzt",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                Priority.HIGH_IMPORTANCE, tasks,
                false, category, "Task1", false);
        MainTask task2 = new MainTask("Mein Kopf schmerzt mehr",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(20),
                Priority.LOW_IMPORTANCE, tasks,
                false, category, "Task2", false);
        MainTask task3 = new MainTask("Mein Kopf schmerzt viel mehr",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(12),
                Priority.AVERAGE_IMPORTANCE, tasks,
                false, category, "Task2", false);

        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_ASC)).thenReturn(Arrays.asList(task1, task3, task2));
        when(mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_DESC)).thenReturn(Arrays.asList(task1, task3, task2));

        //Act - 1st-Asc, 2nd-Desc
        List<MainTask> mainTasksSortedByDueDateAsc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_ASC);
        List<MainTask> mainTasksSortedByDueDateDesc = mainTaskRepository.getMainTasksSortedBy(SortCriteriaEnum.DUEDATE_MAINTASK_DESC);


        //Assert - 1st Block-asc, 2nd Block-desc
        assertEquals(3, mainTasksSortedByDueDateAsc.size());

        assertEquals(task1.getCreationDate(), mainTasksSortedByDueDateAsc.get(0).getCreationDate());
        assertEquals(task3.getCreationDate(), mainTasksSortedByDueDateAsc.get(1).getCreationDate());
        assertEquals(task2.getCreationDate(), mainTasksSortedByDueDateAsc.get(2).getCreationDate());

        assertEquals(task2.getCreationDate(), mainTasksSortedByDueDateDesc.get(2).getCreationDate());
        assertEquals(task3.getCreationDate(), mainTasksSortedByDueDateDesc.get(1).getCreationDate());
        assertEquals(task1.getCreationDate(), mainTasksSortedByDueDateDesc.get(0).getCreationDate());
    }

    @Test
    void testGetMainTaskSortedByNothing() {
        //Arrange
        List<Task> tasks = new ArrayList<>();
        Category category = new Category("Irgendeine Kategorie");
        MainTask task1 = new MainTask("Mein Kopf schmerzt",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                Priority.HIGH_IMPORTANCE, tasks,
                false, category, "Task1", false);
        MainTask task2 = new MainTask("Mein Kopf schmerzt mehr",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(20),
                Priority.LOW_IMPORTANCE, tasks,
                false, category, "Task2", false);
        MainTask task3 = new MainTask("Mein Kopf schmerzt viel mehr",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(12),
                Priority.AVERAGE_IMPORTANCE, tasks,
                false, category, "Task2", false);

        when(mainTaskRepository.getMainTasksSortedBy(null)).thenReturn(Arrays.asList(task1, task2, task3));

        //Act
        List<MainTask> tasksSortedByNothing = mainTaskRepository.getMainTasksSortedBy(null);

        //Assert
        assertEquals(3, tasksSortedByNothing.size());
        assertTrue(tasksSortedByNothing.contains(task1));
        assertTrue(tasksSortedByNothing.contains(task2));
        assertTrue(tasksSortedByNothing.contains(task3));
    }
}