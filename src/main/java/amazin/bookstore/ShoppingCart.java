package amazin.bookstore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Shopping Cart Class that allows each user to
 * add books to their shopping cart and remove books from it.
 * Each user has one shopping cart.
 */
@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @CollectionTable(name = "Cart_Book", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "book_id")
    @Column(name = "quantity")
    private Map<Book, Integer> books;

    /**
     * Constructor for ShoppingCart.
     * Initializes a new ArrayList to manage a list of books
     * in the Shopping Cart.
     */
    public ShoppingCart() {
        books = new HashMap<>();
    }

    /**
     * Get the ID of the ShoppingCart object - used as an identifier
     * @return  a Long, the id of the ShoppingCart object
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set the ID of the ShoppingCart object
     * @param id    a Long, the id of the ShoppingCart object
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user associated with the Shopping Cart
     * @return  a User object, the user that owns this specific ShoppingCart
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Set the user of the Shopping Cart.
     * @param user  a User object, the user that this ShoppingCart belongs to
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get a list of books that are in the shopping cart
     * @return  a List object, a list of books in the shopping cart
     */
// Modify the getBooks method in ShoppingCart class
    public Map<Book, Integer> getBooks() {
        return new HashMap<>(books);
    }
    /**
     * Set the list of books that are in the shopping cart
     * @param books     a List object, a list of books in the shopping cart
     */
    public void setBooks(Map<Book, Integer> books) {
        this.books = new HashMap<>(books);
    }

    /**
     * Add a book to the shopping cart
     * @param book  a Book object, the book to be added to the shopping cart
     */
    public void addBook(Book book, int quantity) {
        if (books.containsKey(book)) {
            books.put(book, books.get(book) + quantity);
        } else {
            books.put(book, quantity);
        }
    }

    /**
     * Remove a book from the shopping cart
     * @param book  a Book object, the book to be removed from the shopping cart
     */
    public void removeBook(Book book) {
        this.books.remove(book);
    }


}
