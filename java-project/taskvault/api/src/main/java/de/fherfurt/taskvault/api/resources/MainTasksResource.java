package de.fherfurt.taskvault.api.resources;

import de.fherfurt.taskvault.api.exceptions.MappingException;
import de.fherfurt.taskvault.api.models.NewMainTaskDto;
import de.fherfurt.taskvault.api.services.IMainTaskService;
import de.fherfurt.taskvault.api.services.MainTaskService;
import de.fherfurt.taskvault.api.models.MainTaskDto;
import de.fherfurt.taskvault.api.mapping.Mapper;
import de.fherfurt.taskvault.models.MainTask;
import de.fherfurt.taskvault.models.utils.SortCriteriaEnum;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST resource for managing mainTasks. Provides endpoints to create, read, update, delete, and sort main tasks.
 */
@Path("api/maintasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MainTasksResource {
    private final IMainTaskService mainTaskService;

    /**
     * Constructor initializing the MainTaskService.
     */
    public MainTasksResource() {
        this.mainTaskService = new MainTaskService();
    }

    /**
     * Retrieves all mainTasks.
     *
     * @return a list of MainTaskDto representing all main tasks.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MainTaskDto> getMainTasks() {
        return this.mainTaskService
                .getAllMainTasks()
                .stream()
                .map(Mapper::mainTaskToDto)
                .toList();
    }

    /**
     * Retrieves a specific mainTask by its ID.
     *
     * @param mainTaskId the ID of the mainTask to retrieve.
     * @return a Response containing the MainTaskDto if found, or a 404 status if not found.
     */
    @GET
    @Path("{mainTaskId:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainTask(
            @PathParam("mainTaskId") int mainTaskId
    ) {
        Optional<MainTask> mainTask = this.mainTaskService.getMainTaskById(mainTaskId);

        if(mainTask.isPresent()) {
            return Response
                    .ok(Mapper.mainTaskToDto(mainTask.get()))
                    .build();
        } else {
            return  Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    /**
     * Retrieves mainTasks sorted by a specific criteria.
     *
     * @param sortCriteria the criteria to sort the mainTasks by.
     * @return a Response containing a list of sorted MainTaskDto, or a 400 status if the criteria is invalid.
     */
    @GET
    @Path("/sorted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMainTaskSorted(@QueryParam("sortCriteria") String sortCriteria) {
        if (sortCriteria != null) {
            try {
                SortCriteriaEnum parsedCriteria = SortCriteriaEnum.valueOf(sortCriteria);

                System.out.println(parsedCriteria);

                List<MainTask> mainTasks = this.mainTaskService.getMainTasksSortedBy(parsedCriteria);

                if (mainTasks != null && !mainTasks.isEmpty()) {
                    List<MainTaskDto> mainTaskDtos = mainTasks.stream()
                            .map(Mapper::mainTaskToDto)
                            .collect(Collectors.toList());
                    return Response.ok(mainTaskDtos).build();
                } else {
                    return Response.ok(new ArrayList<>()).build();
                }
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid sort criteria").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Sort criteria is required").build();
        }
    }

    /**
     * Creates a new mainTask.
     *
     * @param mainTaskToCreate the data for the new mainTask.
     * @return a Response containing the created MainTaskDto, or a 400 status if creation fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMainTask(NewMainTaskDto mainTaskToCreate) {
        MainTask newMainTask = null;

        try {
            newMainTask = Mapper.newMainTaskDtoToMainTask(mainTaskToCreate);
        } catch (MappingException me) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(me.getMessage())
                    .build();
        }

        boolean success = this.mainTaskService.addMainTask( newMainTask );

        if(success) {
            return Response
                    .ok(Mapper.mainTaskToDto(newMainTask))
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Updates an existing mainTask by its ID.
     *
     * @param mainTaskToUpdate the updated data for the mainTask.
     * @param mainTaskId the ID of the main task to update.
     * @return a Response containing the updated MainTaskDto, or a 400 status if the update fails.
     */
    @PUT
    @Path("{mainTaskId:\\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMainTask(
            NewMainTaskDto mainTaskToUpdate,
            @PathParam("mainTaskId") int mainTaskId
    ) {
        MainTask mainTaskUpdates = null;

        try {
            mainTaskUpdates = Mapper.newMainTaskDtoToMainTask(mainTaskToUpdate);
        } catch (MappingException me) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(me.getMessage())
                    .build();
        }

        mainTaskUpdates.setId(mainTaskId);

        boolean success = this.mainTaskService.updateMainTask(mainTaskUpdates);

        if (success) {
            return Response
                    .ok(Mapper.mainTaskToDto(mainTaskUpdates))
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Deletes a specific mainTask by its ID.
     *
     * @param mainTaskId the ID of the mainTask to delete.
     * @return a Response with a 200 status if successful, or a 404 status if the mainTask is not found.
     */
    @DELETE
    @Path("{mainTaskId:\\d+}")
    public  Response deleteMainTask(
            @PathParam("mainTaskId") int mainTaskId
    ) {
        boolean success = this.mainTaskService.deleteMainTask(mainTaskId);

        System.out.println(success);

        if (success) {
            return Response
                    .ok()
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}
