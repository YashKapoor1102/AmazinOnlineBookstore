package amazin.bookstore;

import jakarta.persistence.*;

/**
 * Recommendation entities contain a book which is
 * recommended to a user based on their purchase history.
 * @author Henry Lin
 */

@Entity
@Table(name="recommendations")
public class Recommendation {

    public enum Weighting {
        SAME_AUTHOR (50),
        OTHERS_PURCHASED (100),
        SAME_GENRE (70);

        private final int value;

        Weighting (int value) { this.value = value; }

        public int value() { return value; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    /** Integer weight determines how relevant the recommendation is (higher is more relevant) */
    private int weight;

    /** Default constructor */
    public Recommendation() {}

    /**
     * Create a Recommendation entity for the given user.
     * @param user The user who the recommendations belong to
     * @param book The book being recommended
     * @param weight The recommendation's weight
     */
    public Recommendation(User user, Book book, int weight) {
        this.user = user;
        this.book = book;
        this.weight = weight;
    }

    /**
     * Return the user who the recommendation is for
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
     * Return the recommended book
     * @return The recommended book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Set the recommended book
     * @param book The book to recommend
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Return the recommendation's weight
     * @return The recommendation's weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the recommendation's weight
     * @param weight The given weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Get the recommendation's id
     * @return The id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the recommendation's id
     * @param id The id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
