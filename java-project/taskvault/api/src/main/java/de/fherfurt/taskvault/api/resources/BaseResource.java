package de.fherfurt.taskvault.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Base resource class that serves as the entry point for various API endpoints.
 */
@Path("/api")
public class BaseResource {
    /**
     * Endpoint to provide basic information about the resource.
     *
     * @return a string message "Hello from Base Resource".
     */
    @GET
    public String info() {
        return "Hello from Base Resource";
    }

    /**
     * Provides access to the MainTasksResource.
     *
     * @return a new instance of MainTasksResource.
     */
    @Path("/mainTasks")
    public MainTasksResource getMainTasksResource() {
        return new MainTasksResource();
    }

    /**
     * Provides access to the CategorysResource.
     *
     * @return a new instance of CategorysResource.
     */
    @Path("/category")
    public CategorysResource getCategoryResource(){
        return new CategorysResource();
    }

    /**
     * Health check endpoint to verify that the service is up and running.
     *
     * @return an HTTP 200 OK response if the service is healthy.
     */
    @GET
    @Path("/health")
    public Response getHealth() {
        return Response
                .ok()
                .build();
    }
}
