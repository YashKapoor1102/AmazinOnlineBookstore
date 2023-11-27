package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import amazin.bookstore.Inventory;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST Controller for Inventory entity
 * @author Yaw Asamoah
 */
@RestController
public class InventoryController {


    /**
     * Repository for Inventory entity
     */
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Repository for Book entity
     */
    @Autowired
    private BookRepository bookRepository;


    /**
     * Creates a new Inventory
     * @return      Inventory, the new inventory added
     */
    @PostMapping("/inventory")
    public Inventory createInventory (){
        Inventory inventoryManager = new Inventory();
        return inventoryRepository.save(inventoryManager);
    }

    /**
     * Gets the stock for a book using the book id and the inventory id
     *
     * @param bookId        int, the id of the book specified
     * @param inventoryId   int, the id of the inventory that the book is in
     * @return              int, the current stock for the specified book
     */
    @GetMapping("/inventory/{inventoryId}/book/{bookId}")
    int getBookStockByBookIdInventoryId(@PathVariable int bookId, @PathVariable int inventoryId) {

        Book book = bookRepository.findById(bookId);
        int stock = -1;

        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        stock = inventoryManager.getBookStock(book);

        return stock;
    }

    /**
     * Gets the stock for a book only using the book id
     *
     * @param bookId        int, the id of the book specified
     * @return              int, the current stock for the specified book
     */
    @GetMapping("/inventory/book/{bookId}")
    int getBookStockByBookId(@PathVariable int bookId) {

        Book book = bookRepository.findById(bookId);
        int stock = -1;

        for (Inventory inventoryManager: getAllInventories())
        {
            if(inventoryManager.containsBook(book))
            {
                stock = inventoryManager.getBookStock(book);
                break;
            }
        }

        return stock;
    }

    /**
     * Gets all's the inventory entities
     *
     * @return      List<Inventory>, list of all the inventory entities in the database
     */
    @GetMapping(value = "/inventory")
    public List<Inventory> getAllInventories() {
        return (List<Inventory>) inventoryRepository.findAll();
    }

    /**
     * Get a singular inventory using it's id
     *
     * @param inventoryId       int, the inventory id
     * @return                  Inventory, the inventory associated with the id
     */
    @GetMapping(value = "/inventory/{inventoryId}")
    public Inventory getInventoryById(@PathVariable int inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }


    /**
     * Puts the specified stock for a particular book using book id and the inventory id
     *
     * @param inventoryId       int, the id of the inventory that the book is in
     * @param bookId            int, the id of the book specified
     * @param stock             int, the new stock a book should have
     * @return                  Inventory, the inventory associated with the inventory id
     */
    @PutMapping("/inventory/{inventoryId}/book/{bookId}/{stock}")
    public Inventory putBookStockByBookIdInventoryId (@PathVariable int inventoryId, @PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        inventoryManager.setBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }


    /**
     * Puts the specified stock for a particular book using book id only. Will add to an existing inventory
     * if the book is already there, or to the default inventory if not.
     *
     * @param bookId         int, the id of the book specified
     * @param stock          int, the new stock a book should have
     * @return              Inventory, the current inventory after adding the new book stock
     */
    @PutMapping("/inventory/book/{bookId}/{stock}")
    public Inventory putBookStockByBookId (@PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = findInventoryFromBook(book);

        if (inventoryManager == null)
        {
            inventoryManager = inventoryRepository.findById(getDefaultInventoryId());
        }

        inventoryManager.setBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }

    /**
     * Deletes a singular inventory from database
     *
     * @param inventoryId        int, the id of the inventory that the book is in
     */
    @DeleteMapping("/inventory/{inventoryId}")
    void deleteInventory(@PathVariable int inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    /**
     * Remove a singular book and stock from inventory using book id and inventory id
     *
     * @param inventoryId       int, the id of the inventory that the book is in
     * @param bookId            int, the id of the book specified
     * @return                  Inventory, the current inventory after removing the book
     */
    @PutMapping("/inventory/{inventoryId}/removeBook/{bookId}")
    public Inventory removeBookStockByBookIdInventoryId (@PathVariable int inventoryId, @PathVariable int bookId)
    {
        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        inventoryManager.removeBook(book);
        return inventoryRepository.save(inventoryManager);
    }

    /**
     * Remove a singular book and stock from inventory using book id
     *
     * @param bookId        int, the id of the book specified
     * @return              Inventory, the current inventory after removing the book
     */
    @PutMapping("/inventory/removeBook/{bookId}")
    public Inventory removeBookStockByBookId (@PathVariable int bookId)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = findInventoryFromBook(book);

        if (inventoryManager == null)
        {
            inventoryManager = inventoryRepository.findById(getDefaultInventoryId());
        }

        inventoryManager.removeBook(book);
        return inventoryRepository.save(inventoryManager);
    }

    /**
     * Gets the id of the default inventory, which is the first inventory entity in the database
     *
     * @return      int, the id of the first inventory entity in the database
     */
    private int getDefaultInventoryId()
    {
        int id = 1;

        List<Inventory> allInventories = getAllInventories();
        id = allInventories.get(0).getId();

        return id;
    }


    /**
     * Puts the increased stock for a particular book using book id only
     *
     * @param bookId         int, the id of the book specified
     * @param stock          int, the stock that will be increased from the current book stock
     * @return              Inventory, the current inventory after increasing the book stock
     */
    @PutMapping("/inventory/book/{bookId}/increase/{stock}")
    public Inventory increaseBookStockByBookId (@PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = findInventoryFromBook(book);

        if (inventoryManager == null)
        {
            return null;
        }

        inventoryManager.increaseBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }


    /**
     * Puts the decreased stock for a particular book using book id only
     *
     * @param bookId         int, the id of the book specified
     * @param stock          int, the stock that will be removed from the current book stock
     * @return              Inventory, the current inventory after decreasing the book stock
     */
    @PutMapping("/inventory/book/{bookId}/decrease/{stock}")
    public Inventory decreaseBookStockByBookId (@PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = findInventoryFromBook(book);

        if (inventoryManager == null)
        {
            return null;
        }

        inventoryManager.decreaseBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }

    private Inventory findInventoryFromBook(Book book)
    {
        Inventory inventoryManager = null;

        for (Inventory inventoryManager2: getAllInventories())
        {
            if(inventoryManager2.containsBook(book))
            {
                return inventoryManager2;
            }
        }

        return inventoryManager;
    }
}
