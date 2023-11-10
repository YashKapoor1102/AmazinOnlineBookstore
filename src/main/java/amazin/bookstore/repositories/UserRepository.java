/**
 * @author Yash Kapoor (Student ID: 101163338)
 * @version Milestone 1
 */
package amazin.bookstore.repositories;

import amazin.bookstore.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for users
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Retrieves a user entity by its username
     * @param username      a String, the username of the user to be retrieved
     * @return  the user with the specified username or null if the username does not exist
     */
    User findByUsername(String username);
}
