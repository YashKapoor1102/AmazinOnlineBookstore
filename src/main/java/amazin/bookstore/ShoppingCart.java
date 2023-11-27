package amazin.bookstore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "Cart_Book",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    /**
     * Constructor for ShoppingCart.
     * Initializes a new ArrayList to manage a list of books
     * in the Shopping Cart.
     */
    public ShoppingCart() {
        books = new ArrayList<>();
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
    public List<Book> getBooks() {
        return this.books;
    }

    /**
     * Set the list of books that are in the shopping cart
     * @param books     a List object, a list of books in the shopping cart
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Add a book to the shopping cart
     * @param book  a Book object, the book to be added to the shopping cart
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Remove a book from the shopping cart
     * @param book  a Book object, the book to be removed from the shopping cart
     */
    public void removeBook(Book book) {
        this.books.remove(book);
    }


}
