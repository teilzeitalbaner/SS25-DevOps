package de.fherfurt.taskvault.api.mapping;

import de.fherfurt.taskvault.api.exceptions.MappingException;
import de.fherfurt.taskvault.api.models.*;
import de.fherfurt.taskvault.core.Priority;
import de.fherfurt.taskvault.models.Category;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Mapper class for converting between domain models and Data Transfer Objects (DTOs).
 */
public class Mapper {
    private static final DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Converts a MainTask entity to its corresponding DTO.
     *
     * @param mainTask The MainTask entity to convert.
     * @return A MainTaskDto containing the data from the provided MainTask entity.
     */
    public static MainTaskDto mainTaskToDto(MainTask mainTask) {
        return new MainTaskDto(
                mainTask.getId(),
                mainTask.getTaskName(),
                mainTask.getTaskDescription(),
                mainTask.getCreationDate().format(defaultDateTimeFormatter),
                mainTask.getDueDate().format(defaultDateTimeFormatter),
                mainTask.getPriority().name(),
                mainTask.getSubTasks(),
                mainTask.isDailyTask(),
                mainTask.getCategory().getCategoryName(),
                mainTask.isCompleted()
        );
    }

    /**
     * Converts a NewMainTaskDto to a MainTask entity.
     *
     * @param newMainTaskDto The DTO containing the new task data.
     * @return A MainTask entity initialized with the data from the DTO.
     * @throws MappingException If there is an error during the mapping process, such as a date or priority parsing error.
     */
    public static MainTask newMainTaskDtoToMainTask(NewMainTaskDto newMainTaskDto) throws MappingException {
        LocalDate parseCreationDate;
        try {
            parseCreationDate = LocalDate.parse(newMainTaskDto.getCreationDate(), defaultDateTimeFormatter);
        } catch (DateTimeParseException dtpe) {
            throw new MappingException("CreationDate could not be parsed", dtpe);
        }

        LocalDate parseDueDate;
        try {
            parseDueDate = LocalDate.parse(newMainTaskDto.getDueDate(), defaultDateTimeFormatter);
        } catch (DateTimeParseException dtpe) {
            throw new MappingException("DueDate could not be parsed", dtpe);
        }

        Priority parsedPriority;
        try {
            parsedPriority = Priority.valueOf(newMainTaskDto.getPriority());
        } catch (IllegalArgumentException iae) {
            throw new MappingException("Priority could not be parsed", iae);
        }

        List<Task> subtasks = newMainTaskDto.getSubTasks();

        MainTask mainTask = new MainTask(
                newMainTaskDto.getTaskDescription(),
                parseCreationDate,
                parseDueDate,
                parsedPriority,
                subtasks,
                newMainTaskDto.isDailyTask(),
                null,
                newMainTaskDto.getTaskName(),
                newMainTaskDto.isCompleted()
        );

        Category category = new Category(newMainTaskDto.getCategory());
        mainTask.setCategory(category);

        return mainTask;
    }

    /**
     * Converts a Category entity to its corresponding DTO.
     *
     * @param category The Category entity to convert.
     * @return A CategoryDto containing the data from the provided Category entity.
     */
    public static CategoryDto categoryToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getCategoryName()
        );
    }

    /**
     * Converts a NewCategoryDto to a Category entity.
     *
     * @param categoryToUpdate The DTO containing the new category data.
     * @return A Category entity initialized with the data from the DTO.
     * @throws MappingException If there is an error during the mapping process.
     */
    public static Category categoryDtoToCategory(NewCategoryDto categoryToUpdate) throws MappingException {
        return new Category(categoryToUpdate.getCategoryName());
    }
}
