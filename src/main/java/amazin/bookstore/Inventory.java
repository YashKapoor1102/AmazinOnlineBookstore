package amazin.bookstore;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private int id;


    @ElementCollection
    @CollectionTable(name = "INVENTORY_BOOKS", joinColumns = @JoinColumn(name = "ID") )
    @Column(name="STOCK")
    @MapKeyJoinColumn(name = "BOOK_ID")
    private Map<Book, Integer> catalog;

    public Inventory()
    {
        catalog = new HashMap<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Map<Book, Integer> getCatalog() {
        return catalog;
    }


    public void setCatalog(Map<Book, Integer> catalog) {
        this.catalog = catalog;
    }

    public int getBookStock(Book book) {
        if (catalog.containsKey(book))
        {
            return catalog.get(book);
        }
        else
        {
            return -1; //or o
        }

    }

    public void setBookStock(Book book, int stock) {
        catalog.put(book, stock);
    }

    public boolean containsBook(Book book)
    {
        return catalog.containsKey(book);
    }

    public void removeBook(Book book)
    {
        catalog.remove(book);
    }
}
