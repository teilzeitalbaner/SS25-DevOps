package de.fherfurt.taskvault.data.repositories;

import de.fherfurt.taskvault.data.daos.JpaCategoryDao;
import de.fherfurt.taskvault.models.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {

    private static EntityManagerFactory entityManagerFactory;
    private JpaCategoryDao categoryDao;
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll(){
        entityManagerFactory = Persistence.createEntityManagerFactory("taskvault-unit-test");
    }

    @BeforeEach
    void setUp() {
        EntityManager em = entityManagerFactory.createEntityManager();
        categoryDao = new JpaCategoryDao(em);
        categoryRepository = new CategoryRepository(categoryDao);

    // Clean DB before each test
    List<Category> categories = (List<Category>) categoryRepository.getAllCategory();
    categoryDao.delete(categories);
    }

    @AfterEach
    void tearDown() {
        // Clean DB
        List<Category> categories = (List<Category>) categoryRepository.getAllCategory();
        categoryDao.delete(categories);

        // Clean up objects, for garbage collection
        categoryRepository = null;
        categoryDao = null;
    }

    @Test
    void testGetAllCategory(){
        //Arrange
        Category category1 = new Category("Uni");
        Category category2 = new Category("Essen");

        categoryRepository.createCategory(category1);
        categoryRepository.createCategory(category2);

        //Act
        List<Category> categories = (List<Category>) categoryRepository.getAllCategory();

        //Assert
        assertEquals(2, categories.size());
        assertTrue(categories.contains(category1));
        assertTrue(categories.contains(category2));
    }

    @Test
    void testGetCategory(){
        //Arrange
        Category category = new Category("Hochschule");
        categoryDao.create(category);

        int categoryId = category.getId();

        //Act
        Category retrievedCategory = categoryRepository.getCategory(categoryId);

        //Assert
        assertNotNull(retrievedCategory);
        assertEquals(categoryId, retrievedCategory.getId());
        assertEquals("Hochschule", retrievedCategory.getCategoryName());
    }

    @Test
    void testCreateCategory(){
        //Arrange
        Category category = new Category("Fitness");

        //Act
        boolean isCreated = categoryRepository.createCategory(category);
        List<Category> categories = (List<Category>) categoryRepository.getAllCategory();

        //Assert
        assertTrue(isCreated);
        assertTrue(categories.contains(category));
        assertEquals(1, categories.size());
    }

    @Test
    void testUpdateCategory(){
        //Arrange
        Category category = new Category("Fussball");
        categoryRepository.createCategory(category);
        category.setCategoryName("Basketball");
        int categoryID = category.getId();

        //Act
        boolean isUpdated = categoryRepository.updateCategory(category);
        Category retrievedCategory = categoryRepository.getCategory(category.getId());

        //Assert
        assertTrue(isUpdated);
        assertEquals("Basketball", retrievedCategory.getCategoryName());
        assertNotEquals("Fussball", retrievedCategory.getCategoryName());
        assertEquals(categoryID, retrievedCategory.getId());
    }

    @Test
    void testDeleteCategory(){
        //Arrange
        Category category = new Category("Schach");
        categoryRepository.createCategory(category);

        //Act
        int categoryID = category.getId();
        int categorySizeAfterDeletion = categoryRepository.getAllCategory().size();
        boolean isDeleted = categoryRepository.deleteCategory(categoryID);
        Category retrievedCategory = categoryRepository.getCategory(categoryID);

        //Assert
        assertTrue(isDeleted);
        assertNull(retrievedCategory);
        assertNotEquals(categorySizeAfterDeletion, categoryRepository.getAllCategory().size());
        assertEquals(categorySizeAfterDeletion-1, categoryRepository.getAllCategory().size());
    }
}