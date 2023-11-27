//import amazin.bookstore.AccessingDataJpaApplication;
//import amazin.bookstore.Book;
//import amazin.bookstore.ShoppingCart;
//import amazin.bookstore.User;
//import amazin.bookstore.repositories.BookRepository;
//import amazin.bookstore.repositories.UserRepository;
//import org.assertj.core.api.Assertions;
//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccessingDataJpaApplication.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class TestShoppingCartController {
//
//    @Value(value="${local.server.port}")
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    private User testUser;
//    private Book testAddBook;
//    private Book testRemoveBook;
//    private String sessionCookie;
//
//    /**
//     * Initializing a new user, books that the user can buy,
//     * and the shopping cart for that user.
//     *
//     * Also, registering that user that is initialized and
//     * ensuring the user is logged in.
//     */
//    @BeforeAll
//    public void initialize() {
//
//        // creating new users
//        // adding new books to the Book Repository
//        // those books are added to the shopping cart of the user
//        // since each user has one shopping cart
//        ShoppingCart cart = new ShoppingCart();
//        userRepository.deleteByUsername("testCartUser");
//        testUser = new User("testCartUser", "password", false);
//
//        testAddBook = new Book("123456789", "book1", "author", "publisher", "description");
//        bookRepository.save(testAddBook);
//
//        testRemoveBook = new Book("987654321", "book2", "author", "publisher", "description");
//        bookRepository.save(testRemoveBook);
//        cart.addBook(testRemoveBook);
//
//        testUser.setShoppingCart(cart);
//        cart.setUser(testUser);
//
//        userRepository.save(testUser);
//
//        // Registering the user
//        String url = "http://localhost:" + port + "/user/register";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//        HttpEntity<String> request = new HttpEntity<>("username=testCartUser&password=password", headers);
//        restTemplate.postForEntity(url, request, String.class);
//
//        // User log in
//        String url2 = "http://localhost:" + port + "/user/login";
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.set("Content-Type", "application/x-www-form-urlencoded");
//        HttpEntity<String> request2 = new HttpEntity<>("username=testCartUser&password=password", headers2);
//        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity(url2, request2, String.class);
//        sessionCookie = responseEntity2.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
//
//    }
//
//    /**
//     * Deleting the books from the Book Repository
//     * after all the tests run
//     * to ensure they do not appear on the Books Page.
//     */
//    @AfterAll
//    public void cleanUp() {
//        bookRepository.deleteById(testAddBook.getId());
//        bookRepository.deleteById(testRemoveBook.getId());
//    }
//
//
//    /**
//     * Tests the addBookToCart method to ensure books
//     * are being added to the user's shopping cart
//     * appropriately
//     */
//    @Test
//    public void testAddBookToCart() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//        headers.add("Cookie", sessionCookie);
//
//        HttpEntity<String> request = new HttpEntity<>("bookId=" + testAddBook.getId(), headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/add", request, String.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertThat(responseEntity.getHeaders().getLocation()).hasPath("/cart/view");
//
//        // Re-fetch testUser from the repository to get updated cart
//        testUser = userRepository.findById(testUser.getId()).orElse(null);
//        if(testUser != null) {
//            Assertions.assertThat(testUser.getShoppingCart().getBooks().contains(testAddBook)).isTrue();
//            testUser.getShoppingCart().removeBook(testAddBook);
//            userRepository.save(testUser);
//        }
//    }
//
//    /**
//     * Tests the removeBookFromCart method to ensure books
//     * are being removed from the user's shopping cart
//     * appropriately
//     */
//    @Test
//    public void testRemoveBookFromCart() {
//        Assertions.assertThat(testUser.getShoppingCart().getBooks().contains(testRemoveBook)).isTrue();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//        headers.add("Cookie", sessionCookie);
//
//        HttpEntity<String> request = new HttpEntity<>("bookId=" + testRemoveBook.getId(), headers);
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/cart/remove", request, String.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        assertThat(responseEntity.getHeaders().getLocation()).hasPath("/cart/view");
//
//        // Re-fetch testUser from the repository to get updated cart
//        testUser = userRepository.findById(testUser.getId()).orElse(null);
//        if(testUser != null) {
//            Assertions.assertThat(testUser.getShoppingCart().getBooks().contains(testRemoveBook)).isFalse();
//        }
//
//    }
//
//    /**
//     * Tests the viewCart method to ensure the shopping cart is
//     * being rendered with the correct information
//     */
//    @Test
//    public void testViewCart() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/x-www-form-urlencoded");
//        headers.add("Cookie", sessionCookie);
//
//        HttpEntity<String> request = new HttpEntity<>(headers);
//        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/cart/view",
//                HttpMethod.GET,
//                request,
//                String.class
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).contains("Your Shopping Cart");
//        assertThat(responseEntity.getBody()).contains("Book ID");
//        assertThat(responseEntity.getBody()).contains("Title");
//        assertThat(responseEntity.getBody()).contains("Author");
//
//    }
//}
