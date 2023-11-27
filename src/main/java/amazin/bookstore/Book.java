package amazin.bookstore;

import jakarta.persistence.*;

import java.util.Objects;

/***
 * Book Entity class
 * @author Dana El Sherif
 */
@Entity
@Table(name = "book")
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;

    private double price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * The default constructor for Book
     */
    public Book() {}

    /**
     *
     * @param isbn string isbn of book
     * @param title string title of book
     * @param author string author of book
     * @param publisher string publisher of book
     * @param description string description of book
     * @param price double price of book
     */
    public Book(String isbn, String title, String author, String publisher, String description, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }


    /**
     * Sets the price
     * @return price
     */
    public double getPrice() {
        return price;
    }


    /**
     * Gets the price
     * @param price double price of book
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the ISBN
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * sets the book isbn
     * @param isbn string isbn of book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * gets title of book
     * @return string title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title of book
     * @param title string of book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets author of book
     * @return string author of book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * sets author of book
     * @param author string author of book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * get publisher of book
     * @return string publisher of book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * sets publisher of book
     * @param publisher string publisher of book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * gets description of book
     * @return string description of book
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description of book
     * @param description string description of book
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Converts the book details into a String format
     * @return  a String representation of the Book object, including its
     *          ISBN, title, author, publisher, and description
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Compares this book to the specified object.
     * Result is true if the argument is not null and is
     * a Book object that has the same ISBN, title, author,
     * publisher, description, and ID as this object.
     *
     * @param o     the object that is used to compare this Book with.
     * @return      a boolean, true if the object represents a Book equivalent to this book,
     *              false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher) && Objects.equals(description, book.description) && Objects.equals(id, book.id);
    }

    /**
     * Returns a hash code value for this book.
     * Hash code is calculated using the book's ISBN, title, author,
     * publisher, description, and ID
     *
     * @return    an int, a hash code value for this book.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, publisher, description, id);
    }
}