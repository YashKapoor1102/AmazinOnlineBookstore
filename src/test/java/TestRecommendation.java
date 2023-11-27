import amazin.bookstore.Book;
import amazin.bookstore.Recommendation;
import amazin.bookstore.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecommendation {
    private Recommendation rec;

    @Test
    public void testConstructor() {
        User user = new User();
        Book book = new Book();
        int weight = 1;
        rec = new Recommendation(user, book, weight);

        assertEquals(book, rec.getBook());
        assertEquals(user, rec.getUser());
        assertEquals(weight, rec.getWeight());
    }
}
