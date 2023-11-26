package amazin.bookstore.repositories;

import amazin.bookstore.Book;
import amazin.bookstore.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Shopping Cart
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
