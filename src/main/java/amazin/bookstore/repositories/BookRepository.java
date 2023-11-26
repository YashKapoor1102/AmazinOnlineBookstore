package amazin.bookstore.repositories;

import amazin.bookstore.Book;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

/***
 * Repository for books
 * @author Dana El Sherif
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    //Used for search functionality

    List<Book> findByTitleContainingOrAuthorContainingOrPublisherContainingOrDescriptionContainingOrIsbnContaining(String isbn, String description, String author, String publisher, String title);


    //Below functions might be used by other classes. If not used in future implementations, they can be deleted.
    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);
    Book findById(long id);
    Book findByIsbn(String isbn);

    Book deleteById(long id);

    List<Book> findByPublisher(String publisher);

    Object findAll(Sort title);
}
