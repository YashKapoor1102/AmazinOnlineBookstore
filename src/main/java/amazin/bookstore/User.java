/**
 * @author Yash Kapoor (Student ID: 101163338)
 * @version Milestone 1
 */

package amazin.bookstore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User entity that contains a username and password.
 * There are two types of users: Bookstore owner or bookstore user.
 * There is only one bookstore owner, but many users can register on the registration page.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    private String username;
    private String password;
    private boolean isOwner;

    /**
     * Constructor for User
     * @param username  a String, the username of the user
     * @param password  a String, the password for the user
     * @param isOwner   a boolean, true if the user is a bookstore owner, false otherwise
     */
    public User(String username, String password, boolean isOwner) {
        this.username = username;
        this.password = password;
        this.isOwner = isOwner;
    }

    /**
     * The default constructor for user
     */
    public User() {
        this.username = "";
        this.password = "";
        this.isOwner = false;
    }

    /**
     * Get the ID of the User object - used as an identifier
     * @return  a Long, the id of the User object
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set the ID of the User object
     * @param id    a Long, the id of the User object
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Determines whether the user is an owner or a regular user
     * @return  a boolean, true if the user is an owner, false otherwise
     */
    public boolean isOwner() {
        return this.isOwner;
    }

    /**
     * Set the role of the user
     * @param isOwner   a boolean, true if the user is owner, false otherwise
     */
    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    /**
     * Get the role of the user (either Bookstore Owner or Bookstore User)
     * @return  a String, the role of the user
     */
    public String getRole() {
        return isOwner() ? "Bookstore Owner" : "Bookstore User";
    }

    /**
     * Get the username of the user
     * @return  a String, the username of the user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the username of the user
     * @param username     a String, the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password of the user
     * @return  a String, the password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the password of the user
     * @param password      a String, the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the shopping cart of the user
     * @return  a ShoppingCart object, the shopping cart of the user
     */
    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    /**
     * Set the shopping cart of the user
     * @param shoppingCart  a ShoppingCart object, the shopping cart of the user
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Converts the user details into string format
     * @return  a String representation of the user details, including
     *          User ID, username, and password
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
