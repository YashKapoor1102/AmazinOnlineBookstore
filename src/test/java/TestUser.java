import amazin.bookstore.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

/**
 * Tests the User Class
 */
public class TestUser {
    private User user;

    /**
     * Initializing a new user before each test
     * to ensure the user information is correct
     */
    @BeforeEach
    public void initialize() {
        user = new User();
    }

    /**
     * Tests the ID accessors (getId and SetId)
     */
    @Test
    public void testIdAccessors() {
        user.setId(1L);
        Assertions.assertEquals(1L, user.getId());
    }

    /**
     * Tests the Owner Accessors (isOwner and setOwner)
     */
    @Test
    public void testOwnerAccessors() {
        Assertions.assertFalse(user.isOwner());
        user.setOwner(true);
        Assertions.assertTrue(user.isOwner());
    }

    /**
     * Tests the getRole method
     */
    @Test
    public void testGetRole() {
        User owner = new User("owner", "password", true);
        Assertions.assertEquals("Bookstore Owner", owner.getRole());

        User regularUser = new User("user", "password", false);
        Assertions.assertEquals("Bookstore User", regularUser.getRole());
    }

    /**
     * Tests the username accessors (getUsername and setUsername)
     */
    @Test
    public void testUsernameAccessors() {
        user.setUsername("testUser");
        Assertions.assertEquals("testUser", user.getUsername());
    }

    /**
     * Tests the password accessors (getPassword and setPassword)
     */
    @Test
    public void testPasswordAccessors() {
        user.setPassword("testPassword");
        Assertions.assertEquals("testPassword", user.getPassword());
    }
}
