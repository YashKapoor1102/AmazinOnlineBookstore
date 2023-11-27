import amazin.bookstore.Book;
import amazin.bookstore.Inventory;
import amazin.bookstore.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests the Inventory Class
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInventory {
    private Inventory inventory;
    private Book book;

    /**
     * Initializing a new inventory before all test
     */
    @BeforeAll
    public void initialize() {
        inventory = new Inventory();
        book = new Book("20231117",
                "TestInventoryTitle",
                "TestInventoryAuthor",
                "TestInventoryPublisher",
                "TestInventoryDescription",
                15.00
        );
        inventory.setBookStock(book, 20);
    }

    /**
     * Tests the ID accessors (getId and SetId)
     */
    @Test
    public void testIdAccessors() {
        inventory.setId(10);
        Assertions.assertEquals(10, inventory.getId());
    }

    /**
     * Tests the catalog Accessors (setCatalog and getCatalog)
     */
    @Test
    public void testCatalogAccessors() {

        Map<Book, Integer> newCatalog = new HashMap<>();
        newCatalog.put(book, 20);

        inventory.setCatalog(newCatalog);
        Assertions.assertEquals(inventory.getCatalog(), newCatalog);

    }

    /**
     * Test the setBookStock method
     */
    @Test
    public void testSetBookStock() {
        inventory.setBookStock(book, 20);
        Assertions.assertEquals(inventory.getBookStock(book), 20);
    }

    /**
     * Test the getBookStock method
     */
    @Test
    public void testGetBookStock() {
        //Test getBookStock with existing book in inventory
        Assertions.assertEquals(inventory.getBookStock(book), 20);

        //Test getBookStock when book doesn't exist in inventory
        Book fakeBook = new Book("112623", "Doesnt Exist", "4806", "SYSC", "For testing", 15);
        Assertions.assertEquals(inventory.getBookStock(fakeBook), -1);

    }

    /**
     * Test the containsBook method
     */
    @Test
    public void testContainsBook() {
        inventory.setBookStock(book, 20);
        Assertions.assertTrue(inventory.containsBook(book));

        Book fakeBook = new Book("1126231", "Doesnt Exist", "4806", "SYSC", "For testing", 15);
        Assertions.assertFalse(inventory.containsBook(fakeBook));

    }

    /**
     * Test the removeBook method
     */
    @Test
    public void testRemoveBook() {
        Book book2 = new Book("1126232", "removeBook", "4806", "SYSC", "For testing", 15);
        inventory.setBookStock(book2, 100);
        Assertions.assertTrue(inventory.containsBook(book2));

        inventory.removeBook(book2);
        Assertions.assertFalse(inventory.containsBook(book2));

    }

    /**
     * Test the increaseBookStock method
     */
    @Test
    public void testIncreaseBookStock() {
        inventory.increaseBookStock(book, 10);
        Assertions.assertEquals(inventory.getBookStock(book), 30);

        inventory.setBookStock(book, 20);
    }

    /**
     * Test the increaseBookStock method
     */
    @Test
    public void testDecreaseBookStock() {
        inventory.decreaseBookStock(book, 13);
        Assertions.assertEquals(inventory.getBookStock(book), 7);

        inventory.setBookStock(book, 20);
    }

}
