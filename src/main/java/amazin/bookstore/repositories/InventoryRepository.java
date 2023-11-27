package amazin.bookstore.repositories;

import amazin.bookstore.Inventory;
import org.springframework.data.repository.CrudRepository;

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
}
