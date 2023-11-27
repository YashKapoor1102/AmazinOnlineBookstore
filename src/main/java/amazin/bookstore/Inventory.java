package amazin.bookstore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the inventory of the book store. Makes use of a custom key deserializer and key serializer
 * for the catalog map.
 * @author Yaw Asamoah
 */
@Entity
@Table(name = "inventory")
public class Inventory {

    /**
     * The id of the Inventory, generated from the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * The catalog of books in the inventory. A map that represents the stock per book object
     */
    @ElementCollection
    @CollectionTable(name = "inventory_books", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "stock")
    @MapKeyJoinColumn(name = "book_id")
    @JsonSerialize(keyUsing = BookMapKeySerializer.class)
    @JsonDeserialize(keyUsing = BookMapKeyDeserializer.class)
    private Map<Book, Integer> catalog;


    /**
     * Default constructor
     */
    public Inventory()
    {
        catalog = new HashMap<>();
    }

    /**
     * Gets the inventory id
     * @return      int, the inventory id
     */
    public int getId() {
        return id;
    }

    /**
     * Set's the inventory id
     * @param id    int, the inventory id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the catalog of books and stock per book
     *
     * @return      Map<Book, Integer>, the catalog of books and stock per in the inventory
     */
    public Map<Book, Integer> getCatalog() {
        return catalog;
    }

    /**
     * Set the catalog of books and stock per book
     *
     * @param catalog   Map<Book, Integer>, the catalog of books and stock per in the inventory
     */
    public void setCatalog(Map<Book, Integer> catalog) {
        this.catalog = catalog;
    }

    /**
     * Gets the stock for a particular book
     *
     * @param book      Book, the book that the stock is for
     * @return          int, the stock for the book
     */
    public int getBookStock(Book book) {
        if (catalog.containsKey(book)) {
            return catalog.get(book);
        } else {
            return -1; //or o //or only in controller??
        }

    }

    /**
     * Sets the stock for a particular book
     *
     * @param book      Book, the book that the stock is for
     * @param stock     int, the stock for the book
     */
    public void setBookStock(Book book, int stock) {
        catalog.put(book, stock);
    }

    /**
     * Checks if the inventory contains a particular book
     *
     * @param book      Book, the book that is being looked for
 * @return              Boolean, true if the book is inventory, false if the book is not in inventory
     */
    public boolean containsBook(Book book) {
        return catalog.containsKey(book);
    }

    /**
     * Removes a book from the inventory
     *
     * @param book      Book, the book that is going to be removed
     */
    public void removeBook(Book book) {
        catalog.remove(book);
    }

    /**
     * Increases the stock for a particular book, by the specified amount
     *
     * @param book      Book, the book that the stock is for
     * @param stock     int, the new stock to be added
     */
    public void increaseBookStock(Book book, int stock) {
        int originalStock = getBookStock(book);
        catalog.put(book, originalStock + stock);
    }

    /**
     * Decreases the stock for a particular book, by the specified amount
     *
     * @param book      Book, the book that the stock is for
     * @param stock     int, the new stock to be removed
     */
    public void decreaseBookStock(Book book, int stock) {
        int originalStock = getBookStock(book);
        catalog.put(book, originalStock - stock);
    }
}
