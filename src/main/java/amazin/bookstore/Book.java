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

    public Book() {}

    public Book(String isbn, String title, String author, String publisher, String description) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void main(String[] args) {
        //Tester file- can delete
    }
}