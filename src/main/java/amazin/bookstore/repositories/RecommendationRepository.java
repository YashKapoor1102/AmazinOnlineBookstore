package amazin.bookstore.repositories;

import amazin.bookstore.Book;
import amazin.bookstore.Recommendation;
import amazin.bookstore.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * CRUD repository for Recommendation entities.
 * @author Henry Lin
 */

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
    Recommendation findById(long id);

    List<Recommendation> findByUser(User user);

    List<Recommendation> findByBook(Book book);

    List<Recommendation> findByUserOrderByWeightDesc(User user);

    Recommendation findByUserAndBook(User user, Book book);
}
