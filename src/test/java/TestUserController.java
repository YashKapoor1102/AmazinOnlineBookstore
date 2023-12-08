import amazin.bookstore.AccessingDataJpaApplication;
import amazin.bookstore.User;
import amazin.bookstore.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the execution of each HTTP Request in the UserController Class
 * and whether it is being made successfully
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccessingDataJpaApplication.class)
public class TestUserController {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void resetDatabaseState() {
        userRepository.deleteByUsername("testUser");
    }

    /**
     * Tests the showRegistrationForm method
     * and ensures the registration page is being rendered correctly
     */
    @Test
    public void testShowRegistrationForm() {
        String url = "http://localhost:" + port + "/user/register";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody()).contains("User Registration Form");
        assertThat(responseEntity.getBody()).contains("username");
        assertThat(responseEntity.getBody()).contains("password");
    }

    /**
     * Tests the success of the registerUser method, ensuring the user can register successfully
     */
    @Test
    public void testRegisterUserSuccess() {
        String url = "http://localhost:" + port + "/user/register";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> request = new HttpEntity<>("username=testUser&password=password", headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(Objects.requireNonNull(responseEntity.getHeaders().getLocation()).toString()).contains("/user/login");

    }

    /**
     * Tests the failure of the registerUser method,
     * ensuring an error is thrown when the username already exists
     */
    @Test
    public void testRegisterUserFailure() {
        String url = "http://localhost:" + port + "/user/register";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> request = new HttpEntity<>("username=testUser&password=password", headers);

        // registering the user for the first time
        restTemplate.postForEntity(url, request, String.class);

        // Trying to register the user again - should cause a registration error
        ResponseEntity<String> secondResponse = restTemplate.postForEntity(url, request, String.class);
        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(secondResponse.getBody()).contains("Username already exists. Please choose another one.");
    }

    /**
     * Tests the showLoginForm method
     * and ensures the login form is being rendered correctly
     */
    @Test
    public void testShowLoginForm() {
        String url = "http://localhost:" + port + "/user/login";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(responseEntity.getBody()).contains("Welcome to Amazin Bookstore!");
        assertThat(responseEntity.getBody()).contains("username");
        assertThat(responseEntity.getBody()).contains("password");
    }

    /**
     * Tests the success of the LoginUser method, ensuring the user can register successfully
     */
    @Test
    public void testLoginUserSuccess() {
        // Register the user first
        String urlRegister = "http://localhost:" + port + "/user/register";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> request = new HttpEntity<>("username=testUserLogin&password=password", headers);
        restTemplate.postForEntity(urlRegister, request, String.class);

        // Once the user is registered, logging in with those credentials should be successful
        String urlLogin = "http://localhost:" + port + "/user/login";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(urlLogin, request, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(Objects.requireNonNull(responseEntity.getHeaders().getLocation()).toString()).contains("/books");
    }

    /**
     * Tests the failure of the LoginUser method,
     * ensuring an error is thrown if the username or password entered are invalid
     */
    @Test
    public void testLoginUserFailure() {
        // Logging in with credentials that are not registered yet should cause an error
        String url = "http://localhost:" + port + "/user/login";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> request = new HttpEntity<>("username=testInvalidUser&password=password", headers);

        restTemplate.postForEntity(url, request, String.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("Invalid username or password. Try again!");
    }

    /**
     * Tests the listUsers method and whether the list is being rendered with the correct
     * user information.
     */
    @Test
    public void testListUsers() {
        User user = new User("Test User 1", "testPassword", true);
        User user2 = new User("Test User 2", "testPassword", false);
        User user3 = new User("Test User 3", "testPassword", false);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        String url = "http://localhost:" + port + "/user/list";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // ensuring that all the registered users are on the list
        assertThat(responseEntity.getBody()).contains("List of Registered Users");
        assertThat(responseEntity.getBody()).contains("Test User 1");
        assertThat(responseEntity.getBody()).contains("Test User 2");
        assertThat(responseEntity.getBody()).contains("Test User 3");
        assertThat(responseEntity.getBody()).contains("Bookstore Owner");
        assertThat(responseEntity.getBody()).contains("Bookstore User");

        // ensuring users that are not registered do not appear on the list
        assertThat(responseEntity.getBody()).doesNotContain("Test User Invalid");

        //delete  test users from test
        userRepository.delete(user);
        userRepository.delete(user2);
        userRepository.delete(user3);
    }
}
