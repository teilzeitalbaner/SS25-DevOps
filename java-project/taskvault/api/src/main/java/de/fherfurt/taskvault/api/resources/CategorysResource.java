package de.fherfurt.taskvault.api.resources;

import de.fherfurt.taskvault.api.exceptions.MappingException;
import de.fherfurt.taskvault.api.mapping.Mapper;
import de.fherfurt.taskvault.api.models.CategoryDto;
import de.fherfurt.taskvault.api.models.NewCategoryDto;
import de.fherfurt.taskvault.api.services.CategoryService;
import de.fherfurt.taskvault.api.services.ICategoryService;
import de.fherfurt.taskvault.models.Category;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

/**
 * REST resource for managing categories. Provides endpoints to create, read, update, and delete categories.
 */
@Path("api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategorysResource {
    private ICategoryService categoryService;

    /**
     * Constructor initializing the CategoryService.
     */
    public CategorysResource(){
        this.categoryService = new CategoryService();
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of CategoryDto representing all categories.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDto> getCategorys() {
        return this.categoryService
                .getAllCategories()
                .stream()
                .map(Mapper::categoryToDto)
                .toList();
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId the ID of the category to retrieve.
     * @return a Response containing the CategoryDto if found, or a 404 status if not found.
     */
    @GET
    @Path("{categoryId:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(
            @PathParam("categoryId") int categoryId
    ) {
        Optional<Category> category = this.categoryService.getCategoryById(categoryId);

        if(category.isPresent()) {
            return Response
                    .ok(Mapper.categoryToDto(category.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    /**
     * Creates a new category.
     *
     * @param categoryToCreate the data for the new category.
     * @return a Response containing the created CategoryDto, or a 400 status if creation fails.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory (NewCategoryDto categoryToCreate) {
        Category newCategory;

        try {
            newCategory = Mapper.categoryDtoToCategory(categoryToCreate);
        } catch (MappingException me) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(me.getMessage())
                    .build();
        }

        boolean success = this.categoryService.addCategory(newCategory);

        if(success) {
            return Response
                    .ok(Mapper.categoryToDto(newCategory))
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Updates an existing category by its ID.
     *
     * @param categoryToUpdate the updated data for the category.
     * @param categoryId the ID of the category to update.
     * @return a Response containing the updated CategoryDto, or a 400 status if the update fails.
     */
    @PUT
    @Path("{categoryId:\\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(
            NewCategoryDto categoryToUpdate,
            @PathParam("categoryId") int categoryId
    ) {
        Category categoryUpdates;

        try {
            categoryUpdates = Mapper.categoryDtoToCategory(categoryToUpdate);
        } catch (MappingException me) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(me.getMessage())
                    .build();
        }

        categoryUpdates.setId(categoryId);

        boolean success = this.categoryService.updateCategory(categoryUpdates);

        if(success) {
            return Response
                    .ok(Mapper.categoryToDto(categoryUpdates))
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    /**
     * Deletes a specific category by its ID.
     *
     * @param categoryId the ID of the category to delete.
     * @return a Response with a 200 status if successful, or a 404 status if the category is not found.
     */
    @DELETE
    @Path("{categoryId:\\d+}")
    public Response deleteCategory(
            @PathParam("categoryId") int categoryId
    ) {
        boolean success = this.categoryService.deleteCategory(categoryId);

        if(success) {
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
