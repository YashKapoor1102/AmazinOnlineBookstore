package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import amazin.bookstore.Inventory;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class InventoryController {


    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BookRepository bookRepository;


    @PostMapping("/inventory")
    public Inventory createInventory (){
        Inventory inventoryManager = new Inventory();
        return inventoryRepository.save(inventoryManager);
    }




    @GetMapping("/inventory/{inventoryId}/book/{bookId}")
    int getBookStockByBookIdInventoryId(@PathVariable int bookId, @PathVariable int inventoryId) {

        Book book = bookRepository.findById(bookId);
        int stock = -1;

        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        stock = inventoryManager.getBookStock(book);

        return stock;
    }

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



    @GetMapping(value = "/inventory")
    public List<Inventory> getAllInventories() {
        return (List<Inventory>) inventoryRepository.findAll();
    }

    @GetMapping(value = "/inventory/{inventoryId}")
    public Inventory getInventoryById(@PathVariable int inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }


    @PutMapping("/inventory/{inventoryId}/book/{bookId}/{stock}")
    public Inventory putBookStockByBookIdInventoryId (@PathVariable int inventoryId, @PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        inventoryManager.setBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }


    @PutMapping("/inventory/book/{bookId}/{stock}")
    public Inventory putBookStockByBookIdInventoryId (@PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = null;

        for (Inventory inventoryManager2: getAllInventories())
        {
            if(inventoryManager2.containsBook(book))
            {
                inventoryManager = inventoryManager2;
                break;
            }
        }

        if (inventoryManager == null)
        {
            inventoryManager = inventoryRepository.findById(getDefaultInventoryId());
        }

        inventoryManager.setBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }

    @DeleteMapping("/inventory/{inventoryId}")
    void deleteInventory(@PathVariable int inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    @PutMapping("/inventory/{inventoryId}/removeBook/{bookId}/{stock}")
    public Inventory removeBookStockByBookIdInventoryId (@PathVariable int inventoryId, @PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = inventoryRepository.findById(inventoryId);
        inventoryManager.removeBook(book);
        return inventoryRepository.save(inventoryManager);
    }


    @PutMapping("/inventory/removeBook/{bookId}/{stock}")
    public Inventory removeBookStockByBookIdInventoryId (@PathVariable int bookId, @PathVariable int stock)
    {

        Book book = bookRepository.findById(bookId);
        Inventory inventoryManager = null;



        for (Inventory inventoryManager2: getAllInventories())
        {
            if(inventoryManager2.containsBook(book))
            {
                inventoryManager = inventoryManager2;
                break;
            }
        }

        if (inventoryManager == null)
        {
            inventoryManager = inventoryRepository.findById(getDefaultInventoryId());
        }

        inventoryManager.removeBook(book);
        return inventoryRepository.save(inventoryManager);
    }

    public int getDefaultInventoryId()
    {
        int id = 1;

        List<Inventory> allInventories = getAllInventories();
        id = allInventories.get(0).getId();

        return id;
    }

    /**

     @PostMapping("/inventory/{id}/{stock}")
     public InventoryManager createInventory2 (@PathVariable int id, @PathVariable int stock){
     Book book = bookRepository.findById(id);
     InventoryManager inventoryManager;


     if (inventoryManagerRepository.findById(InventoryManagerId) !=null)
     {
     inventoryManager = inventoryManagerRepository.findById(InventoryManagerId);
     }
     else {
     inventoryManager = new InventoryManager();
     }

     inventoryManager.setProductStock(book, stock);

     return inventoryManagerRepository.save(inventoryManager);
     }


    @PutMapping("/inventory/{id}/{stock}")
    public Inventory putBookStockByBookIdInventoryId (@PathVariable int id, @PathVariable int stock, @PathVariable Optional<Integer> optionalInventoryId){

        Book book = bookRepository.findById(id);
        Inventory inventoryManager = null;
        int defaultInventoryId = 1;

        if (optionalInventoryId.isPresent()) {
            int inventoryId = optionalInventoryId.get();
            inventoryManager = inventoryRepository.findById(inventoryId);
        }
        else
        {
            for (Inventory inventoryManager2: getAllInventoryManagers())
            {
                if(inventoryManager2.containsBook(book))
                {
                    inventoryManager = inventoryManager2;
                    break;
                }
            }

            if (inventoryManager == null)
            {
                inventoryManager = inventoryRepository.findById(defaultInventoryId);
            }
        }

        inventoryManager.setBookStock(book, stock);
        return inventoryRepository.save(inventoryManager);
    }


     @GetMapping("/Inventory/{bookId}")
     int getInventoryStockByBookId(@PathVariable int bookId, @PathVariable Optional<Integer> optionalInventoryId) {

     Book book = bookRepository.findById(bookId);
     int stock = -1;

     if (optionalInventoryId.isPresent()) {
     int inventoryId = optionalInventoryId.get();
     Inventory inventoryManager = inventoryManagerRepository.findById(inventoryId);
     stock = inventoryManager.getBookStock(book);
     }
     else
     {
     for (Inventory inventoryManager: getAllInventoryManagers())
     {
     if(inventoryManager.containsBook(book))
     {
     stock = inventoryManager.getBookStock(book);
     break;
     }
     }
     }

     return stock;
     }
     **/

    
}
