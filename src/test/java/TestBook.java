//import amazin.bookstore.User;
//import org.junit.jupiter.api.Test;
//import amazin.bookstore.Book;
//import org.junit.jupiter.api.BeforeEach;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Tests the Book Class
// */
//
//class TestBook {
//
//    private Book book;
//
//    /**
//     * Initializing a new book before each test
//     * to ensure methods work
//     */
//    @BeforeEach
//    public void initialize() {
//        book = new Book("12345", "Red Dragon", "Jacob B", "Pub Corp", "fantasy" );
//    }
//
//    /**
//     * Tests get ISBN method of book
//     */
//    @Test
//    void getIsbn() {
//        assertEquals("12345", book.getIsbn());
//    }
//
//    /**
//     * Tests set ISBN method of book
//     */
//    @Test
//    void setIsbn() {
//        book.setIsbn("22222");
//        assertEquals("22222", book.getIsbn());
//    }
//
//    /**
//     * Tests get title method of book
//     */
//    @Test
//    void getTitle() {
//        assertEquals("Red Dragon", book.getTitle());
//    }
//
//    /**
//     * Tests set title method of book
//     */
//    @Test
//    void setTitle() {
//        book.setTitle("Cloudy");
//        assertEquals("Cloudy", book.getTitle());
//    }
//
//    /**
//     * Tests get author method of book
//     */
//    @Test
//    void getAuthor() {
//        assertEquals("Jacob B", book.getAuthor());
//    }
//
//    /**
//     * Tests set author method of book
//     */
//    @Test
//    void setAuthor() {
//        book.setAuthor("Bob");
//        assertEquals("Bob", book.getAuthor());
//    }
//
//    /**
//     * Tests get publisher method of book
//     */
//    @Test
//    void getPublisher() {
//        assertEquals("Pub Corp", book.getPublisher());
//    }
//
//    /**
//     * Tests set publisher method of book
//     */
//    @Test
//    void setPublisher() {
//        book.setPublisher("Publishing");
//        assertEquals("Publishing", book.getPublisher());
//    }
//
//    /**
//     * Tests get description method of book
//     */
//    @Test
//    void getDescription() {
//        assertEquals("fantasy", book.getDescription());
//    }
//    /**
//     * Tests set description method of book
//     */
//    @Test
//    void setDescription() {
//        book.setDescription("Horror");
//        assertEquals("Horror", book.getDescription());
//
//    }
//}