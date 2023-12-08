package amazin.bookstore.repositories;

import amazin.bookstore.Book;
import amazin.bookstore.Inventory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for Inventory
 * @author Yaw Asamoah
 */
public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    /**
     * Gets singular inventory entity by it's id
     *
     * @param id        int, the id of the wanted inventory
     * @return          Inventory, the inventory with the specified id
     */
    Inventory findById(int id);

    @Query("SELECT i FROM Inventory i JOIN i.catalog c WHERE KEY(c).id = :bookId")
    List<Inventory> findInventoriesWithBook(@Param("bookId") Long bookId);


}
