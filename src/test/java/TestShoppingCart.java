//import amazin.bookstore.Book;
//import amazin.bookstore.ShoppingCart;
//import amazin.bookstore.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Tests the ShoppingCart Class
// */
//public class TestShoppingCart {
//    private ShoppingCart shoppingCart;
//    private User user;
//    private Book book1, book2;
//
//    /**
//     * Initializing a new ShoppingCart, User, and Book
//     * before each test is executed to ensure values
//     * do not get mixed up
//     */
//    @BeforeEach
//    public void initialize() {
//        shoppingCart = new ShoppingCart();
//        user = new User("TestUser", "TestPassword", false);
//        book1 = new Book("123456789", "book1", "Author1", "Publisher1", "Description1", 5);
//        book2 = new Book("987654321", "book2", "Author2", "Publisher2", "Description2", 18);
//    }
//
//    /**
//     * Tests the ID accessors (getId and SetId)
//     */
//    @Test
//    public void testIdAccessors() {
//        shoppingCart.setId(1L);
//        Assertions.assertEquals(1L, shoppingCart.getId());
//    }
//
//    /**
//     * Tests the User accessors (getUser and setUser)
//     */
//    @Test
//    public void testUserAccessors() {
//        shoppingCart.setUser(user);
//        Assertions.assertEquals(user, shoppingCart.getUser());
//    }
//
//    /**
//     * Tests the Books accessors (getBooks and setBooks)
//     */
//    @Test
//    public void testBooksAccessors() {
//        List<Book> books = new ArrayList<>();
//        books.add(book1);
//        books.add(book2);
//        shoppingCart.setBooks(books);
//        Assertions.assertEquals(2, shoppingCart.getBooks().size());
//        Assertions.assertTrue(shoppingCart.getBooks().contains(book1));
//        Assertions.assertTrue(shoppingCart.getBooks().contains(book2));
//    }
//
//    /**
//     * Tests the addBook method to ensure a book is being added
//     * to the shopping cart correctly
//     */
//    @Test
//    public void testAddBook() {
//        shoppingCart.addBook(book1);
//        Assertions.assertEquals(1, shoppingCart.getBooks().size());
//        Assertions.assertTrue(shoppingCart.getBooks().contains(book1));
//    }
//
//    /**
//     * Tests the removeBook method to ensure a book is being removed
//     * from the shopping cart correctly
//     */
//    @Test
//    public void testRemoveBook() {
//        shoppingCart.addBook(book1);
//        shoppingCart.addBook(book2);
//        shoppingCart.removeBook(book1);
//        Assertions.assertEquals(1, shoppingCart.getBooks().size());
//        Assertions.assertFalse(shoppingCart.getBooks().contains(book1));
//        Assertions.assertTrue(shoppingCart.getBooks().contains(book2));
//    }
//}
