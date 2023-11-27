import amazin.bookstore.AccessingDataJpaApplication;
import amazin.bookstore.Book;
import amazin.bookstore.Inventory;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.InventoryRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

/**
 * Tests the InventoryController
 * @author Yaw Asamoah
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccessingDataJpaApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInventoryController {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Book that will be used for testing the inventory
     */
    private Book book;

    /**
     * Before all the tests run, creates a new book to be used for testing
     */
    @BeforeAll
    public void setupTests()
    {
        Book testBook = new Book("20231117",
                "TestInventoryTitle",
                "TestInventoryAuthor",
                "TestInventoryPublisher",
                "TestInventoryDescription",
                13.00
                );
        this.book = bookRepository.save(testBook);

    }

    /**
     * After all tests have ran, deletes the test book
     */
    @AfterAll
    public void cleanUp()
    {
        bookRepository.deleteById(book.getId());
    }

    /**
     * Removes the specified inventory from the database. Will be used after test is done.
     * @param inventoryId       int, the id of the inventory to be removed
     */
    private void removeInventoryAfterTest(int inventoryId)
    {
        String url = "http://localhost:" + port + "/inventory/"+inventoryId;
        this.restTemplate.delete(url);
    }

    /**
     * Creates a new empty inventory to be used for tests
     *
     * @return      Inventory, the empty inventory made
     */
    private Inventory createNewEmptyInventory()
    {
        String url = "http://localhost:" + port + "/inventory";
        Inventory testInventory = this.restTemplate.postForObject(url, new Inventory(),Inventory.class);
        return testInventory;
    }

    /**
     * Creates a new inventory with the already defined book and stock to be used for tests
     *
     * @param stock         int, the stock of the defined book to be added to the inventory
     * @return              Inventory, the new inventory made
     */
    private Inventory createNewInventoryWithBook(int stock)
    {
        Inventory initalInventory = createNewEmptyInventory();
        String putBookUrl = "http://localhost:" + port +"/inventory/{inventoryId}/book/{bookId}/{stock}";
        initalInventory.setBookStock(book, stock);
        this.restTemplate.put(putBookUrl, initalInventory, initalInventory.getId(), book.getId(), stock);
        return initalInventory;

    }

    /**
     * Tests the createInventory method
     */
    @Test
    public void testCreateInventory()
    {
        String testUrl = "http://localhost:" + port + "/inventory";
        Inventory initalInventory = new Inventory();
        Inventory testInventory = this.restTemplate.postForObject(testUrl, initalInventory,Inventory.class);

        Assertions.assertEquals(0,testInventory.getCatalog().size());
        Assertions.assertTrue(testInventory.getId() > 0);

        removeInventoryAfterTest(testInventory.getId());
    }

    /**
     * Tests the getBookStockByBookIdInventoryId method
     */
    @Test
    public void testGetBookStockByBookIdInventoryId()
    {
        //Test for book that exist in Inventory
        int expectedStock = 5;
        long existingBookId = book.getId();
        Inventory initalInventory = createNewInventoryWithBook(expectedStock);

        String testUrl = "http://localhost:" + port + "/inventory/{inventoryId}/book/{bookId}";
        int actualStock = this.restTemplate.getForObject(testUrl, Integer.class, initalInventory.getId(), existingBookId);
        Assertions.assertEquals(expectedStock, actualStock);

        //Test for book that doesn't exist in Inventory
        int nonExistingBookId = 0;
        actualStock = this.restTemplate.getForObject(testUrl, Integer.class, initalInventory.getId(), nonExistingBookId);
        Assertions.assertEquals(-1, actualStock);

        removeInventoryAfterTest(initalInventory.getId());
    }

    /**
     * Tests the getBookStockByBookId method
     */
    @Test
    public void testGetBookStockByBookId()
    {

        //Test for book that exist in Inventory
        int expectedStock = 10;
        long existingBookId = book.getId();
        Inventory initalInventory = createNewInventoryWithBook(expectedStock);

        String testUrl = "http://localhost:" + port + "/inventory/book/{bookId}";
        int actualStock = this.restTemplate.getForObject(testUrl, Integer.class, existingBookId);
        Assertions.assertEquals(expectedStock, actualStock);

        //Test for book that doesn't exist in Inventory
        int nonExistingBookId = 0;
        actualStock = this.restTemplate.getForObject(testUrl, Integer.class, nonExistingBookId);
        Assertions.assertEquals(-1, actualStock);

        removeInventoryAfterTest(initalInventory.getId());
    }

    /**
     * Tests the getAllInventories method
     */
    @Test
    public void testGetAllInventories()
    {
        String url = "http://localhost:" + port + "/inventory";
        int expectedStock = 15;
        Inventory initialInventory1 = createNewEmptyInventory();
        Inventory initialInventory2 = createNewInventoryWithBook(expectedStock);

        ResponseEntity<Inventory[]> response =restTemplate.getForEntity(url, Inventory[].class);
        Inventory[] allInventories = response.getBody();

        Assertions.assertEquals(2, allInventories.length);
        Assertions.assertEquals(0, allInventories[0].getCatalog().size());
        Assertions.assertEquals(1, allInventories[1].getCatalog().size());

        removeInventoryAfterTest(initialInventory1.getId());
        removeInventoryAfterTest(initialInventory2.getId());
    }

    /**
     * Tests the getInventoryById method
     */
    @Test
    public void testGetInventoryById()
    {
        String url = "http://localhost:" + port + "/inventory/{inventoryId}";
        int expectedStock = 5;
        Inventory initalInventory = createNewInventoryWithBook(expectedStock);
        Inventory testInventory = this.restTemplate.getForObject(url, Inventory.class, initalInventory.getId());

        Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
        Assertions.assertEquals(1, testInventory.getCatalog().size());
        Assertions.assertEquals(expectedStock, testInventory.getBookStock(book));

        removeInventoryAfterTest(initalInventory.getId());
    }

    /**
     * Tests the putBookStockByBookIdInventoryId method
     */
    @Test
    public void testPutBookStockByBookIdInventoryId()
    {
        String url = "http://localhost:" + port +"/inventory/{inventoryId}/book/{bookId}/{stock}";
        Inventory initalInventory = createNewEmptyInventory();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        //Loop to first test putting a new Book into an Inventory,
        //then 2nd loop to test updating the stock of an existing book in an inventory
        for (int i = 0; i < 2; i++)
        {
            int expectedStock = 10 + (10*i);
            initalInventory.setBookStock(book, expectedStock);
            ResponseEntity<Inventory> response = this.restTemplate.exchange(url, HttpMethod.PUT, request,
                    Inventory.class, initalInventory.getId(), book.getId(), expectedStock);
            Inventory testInventory = response.getBody();

            Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
            Assertions.assertEquals(1, testInventory.getCatalog().size());
            Assertions.assertEquals(expectedStock, testInventory.getBookStock(book));

        }

        removeInventoryAfterTest(initalInventory.getId());

    }

    /**
     * Tests the putBookStockByBookId method
     */
    @Test
    public void testPutBookStockByBookId()
    {
        String url = "http://localhost:" + port +"/inventory/book/{bookId}/{stock}";
        Inventory initalInventory = createNewEmptyInventory();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        //Loop to first test putting a new Book into an Inventory,
        //then 2nd loop to test updating the stock of an existing book in an inventory
        for (int i = 0; i < 2; i++)
        {
            int expectedStock = 10 + (10*i);
            initalInventory.setBookStock(book, expectedStock);
            ResponseEntity<Inventory> response = this.restTemplate.exchange(url, HttpMethod.PUT, request,
                    Inventory.class, book.getId(), expectedStock);
            Inventory testInventory = response.getBody();

            Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
            Assertions.assertEquals(1, testInventory.getCatalog().size());
            Assertions.assertEquals(expectedStock, testInventory.getBookStock(book));

        }

        removeInventoryAfterTest(initalInventory.getId());

    }

    /**
     * Tests the deleteInventory method
     */
    @Test
    public void testDeleteInventory()
    {

        Inventory initialInventory1 = createNewEmptyInventory();
        Inventory initialInventory2 = createNewInventoryWithBook(40);

        removeInventoryAfterTest(initialInventory2.getId());

        String getAllurl = "http://localhost:" + port + "/inventory";
        ResponseEntity<Inventory[]> response =restTemplate.getForEntity(getAllurl, Inventory[].class);
        Inventory[] allInventories = response.getBody();

        Assertions.assertEquals(1, allInventories.length);
        Assertions.assertEquals(initialInventory1.getId(), allInventories[0].getId());
        Assertions.assertEquals(0, allInventories[0].getCatalog().size());

        removeInventoryAfterTest(initialInventory1.getId());

    }

    /**
     * Tests the removeBookStockByBookIdInventoryId method
     */
    @Test
    public void testRemoveBookStockByBookIdInventoryId()
    {
        String testUrl = "/inventory/{inventoryId}/removeBook/{bookId}";
        Inventory initalInventory = createNewInventoryWithBook(20);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Inventory> response = this.restTemplate.exchange(testUrl, HttpMethod.PUT, request,
                Inventory.class, initalInventory.getId(), book.getId());
        Inventory testInventory = response.getBody();

        Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
        Assertions.assertEquals(0, testInventory.getCatalog().size());
        Assertions.assertEquals(-1, testInventory.getBookStock(book));

        removeInventoryAfterTest(initalInventory.getId());

    }

    /**
     * Tests the removeBookStockByBookId method
     */
    @Test
    public void testRemoveBookStockByBookId()
    {
        String testUrl = "/inventory/removeBook/{bookId}";
        Inventory initalInventory = createNewInventoryWithBook(20);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<Inventory> response = this.restTemplate.exchange(testUrl, HttpMethod.PUT, request,
                Inventory.class, book.getId());
        Inventory testInventory = response.getBody();

        Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
        Assertions.assertEquals(0, testInventory.getCatalog().size());
        Assertions.assertEquals(-1, testInventory.getBookStock(book));

        removeInventoryAfterTest(initalInventory.getId());

    }


    /**
     * Test the increaseBookStockByBookId method
     */
    @Test
    public void testIncreaseBookStockByBookId()
    {
        String url = "http://localhost:" + port +"/inventory/book/{bookId}/increase/{stock}";
        Inventory initalInventory = createNewInventoryWithBook(20);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        int expectedStock = 10 + 20;
        initalInventory.increaseBookStock(book, 10);

        ResponseEntity<Inventory> response = this.restTemplate.exchange(url, HttpMethod.PUT, request,
                Inventory.class, book.getId(), 10);
        Inventory testInventory = response.getBody();

        Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
        Assertions.assertEquals(1, testInventory.getCatalog().size());
        Assertions.assertEquals(expectedStock, testInventory.getBookStock(book));

        removeInventoryAfterTest(initalInventory.getId());

    }


    /**
     * Test the DecreaseBookStockByBookId method
     */
    @Test
    public void testDecreaseBookStockByBookId()
    {
        String url = "http://localhost:" + port +"/inventory/book/{bookId}/decrease/{stock}";
        Inventory initalInventory = createNewInventoryWithBook(20);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        int expectedStock = 20 - 10;
        initalInventory.decreaseBookStock(book, 10);

        ResponseEntity<Inventory> response = this.restTemplate.exchange(url, HttpMethod.PUT, request,
                Inventory.class, book.getId(), 10);
        Inventory testInventory = response.getBody();

        Assertions.assertEquals(initalInventory.getId(), testInventory.getId());
        Assertions.assertEquals(1, testInventory.getCatalog().size());
        Assertions.assertEquals(expectedStock, testInventory.getBookStock(book));

        removeInventoryAfterTest(initalInventory.getId());

    }

}
