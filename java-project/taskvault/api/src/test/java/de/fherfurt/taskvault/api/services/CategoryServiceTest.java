package de.fherfurt.taskvault.api.services;

import de.fherfurt.taskvault.data.core.DataController;
import de.fherfurt.taskvault.data.repositories.CategoryRepository;
import de.fherfurt.taskvault.data.repositories.ICategoryRepository;
import de.fherfurt.taskvault.models.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private ICategoryService categoryService;
    private ICategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        //Mock
        categoryRepository = mock(ICategoryRepository.class);
        DataController dataController = mock(DataController.class);

        when(dataController.getCategoryRepository()).thenReturn(categoryRepository);

        categoryService = new CategoryService(){
            private DataController getDataController(){
                return dataController;
            }
        };
    }

    @AfterEach
    void tearDown() {
        categoryService = null;
        categoryRepository = null;
    }

    @Test
    void testGetAllCategories() {
        //Arrange
        Category category1 = new Category("Einkaeufe");
        Category category2 = new Category("Getraenkeliste");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(categoryRepository.getAllCategory()).thenReturn(categories);

        //Act
        List<Category> result = categoryRepository.getAllCategory();

        //Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
    }

    @Test
    void testGetCategoryById(){
        //Arrange
        String categoryName = "Getraenkeliste";
        Category category = new Category(categoryName);
        Category category2 = new Category("Snackliste");
        categoryService.addCategory(category);
        categoryService.addCategory(category2);

        //Act
        int expectedIdForCategory1 = 1;
        int expectedIdForCategory2 = 2;

        int categoryIdByName = this.categoryService.getAllCategories()
                .stream()
                .filter(category1 -> category.getCategoryName().equals(categoryName))
                .map(Category::getId)
                .findFirst().orElse(-1);

        int categoryIdById = categoryService.getCategoryById(expectedIdForCategory2).get().getId();

        //Assert
        assertEquals(expectedIdForCategory1, categoryIdByName);
        assertEquals(expectedIdForCategory2, categoryIdById);
    }

    @Test
    void testAddCategory(){
        //Arrange
        Category category1 = new Category("Getraenkeliste");
        Category category2 = new Category("Einkaeufe");
        List<Category> categories = new ArrayList<>();

        when(categoryRepository.getAllCategory()).thenReturn(categories);

        //Act
        int categorySizeBefore = categoryRepository.getAllCategory().size();
        categoryService.addCategory(category1);
        categoryService.addCategory(category2);

        //Assert
        assertEquals(categorySizeBefore+2, categoryService.getAllCategories().size());
        assertNotEquals(categorySizeBefore, categoryService.getAllCategories().size());
    }

    @Test
    void testUpdateCategory(){
        //Arrange
        Category category1 = new Category("Getraenkeliste");
        List<Category> categories = new ArrayList<>();

        when(categoryRepository.getAllCategory()).thenReturn(categories);

        //Act
        String category1Before = category1.getCategoryName();
        String newCategory1Name = "Bierliste";
        category1.setCategoryName(newCategory1Name);
        categoryService.addCategory(category1);
        boolean updatedResult = categoryService.updateCategory(category1);

        //Assert
        assertTrue(updatedResult);
        assertNotEquals(category1Before, category1.getCategoryName());
        assertEquals(newCategory1Name, category1.getCategoryName());

    }

    @Test
    void testDeleteCategory(){
        //Arrange
        Category category1 = new Category("Getraenkeliste");
        List<Category> categories = new ArrayList<>();

        when(categoryRepository.getAllCategory()).thenReturn(categories);
        categoryService.addCategory(category1);

        //Act
        int categorySizeBefore = categoryService.getAllCategories().size();
        boolean isDeleted = categoryService.deleteCategory(category1.getId());
        int categorySizeAfter = categoryService.getAllCategories().size();

        //Assert
        assertTrue(isDeleted);
        assertNotEquals(categorySizeBefore, categoryService.getAllCategories().size());
        assertEquals(categorySizeAfter, categoryService.getAllCategories().size());
        assertFalse(categoryService.getAllCategories().contains(category1));
    }
}