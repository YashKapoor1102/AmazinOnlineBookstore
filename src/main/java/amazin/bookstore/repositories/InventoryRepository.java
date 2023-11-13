package amazin.bookstore.repositories;

import amazin.bookstore.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {

    Inventory findById(int id);
}
