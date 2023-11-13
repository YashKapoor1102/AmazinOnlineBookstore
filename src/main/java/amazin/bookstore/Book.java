package amazin.bookstore;

import jakarta.persistence.*;

/***
 * Book Entity class
 * @author Dana El Sherif
 */
@Entity
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;
    @Id
    @GeneratedValue
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
     */
    public Book(String isbn, String title, String author, String publisher, String description) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
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

}