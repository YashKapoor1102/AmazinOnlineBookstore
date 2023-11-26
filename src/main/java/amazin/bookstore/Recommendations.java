package amazin.bookstore;

import jakarta.persistence.*;

import java.util.ArrayList;

/**
 * Recommendations entity contains a list of books which
 * are recommended to a user based on their purchase history, etc.
 * @author Henry Lin
 */

@Entity
@Table(name="recommendations")
public class Recommendations {
    @Id
    @OneToOne
    private User user;

    @ManyToMany
    private ArrayList<Book> books;

    /** Default constructor */
    public Recommendations() {}

    /**
     * Create a Recommendations entity for the given user.
     * @param user The user who the recommendations belong to
     */
    public Recommendations(User user) {
        this.user = user;
    }

    /**
     * Return the user who the recommendations are for
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Return the recommended books
     * @return An ArrayList of books
     */
    public ArrayList<Book> getBooks() {
        return books;
    }
}
