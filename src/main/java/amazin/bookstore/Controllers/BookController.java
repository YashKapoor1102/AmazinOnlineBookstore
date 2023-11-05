package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import amazin.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/***
 * Controller of Book
 * @author Dana El Sherif
 */
@Controller
public class BookController {

    @Autowired
    public final BookRepository bookRepository;

    BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /***
     * Lists all books in book repository (books must be added first, or it will be empty)
     * @param model Model object
     * @return view template
     */
    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll();
        model.addAttribute("books", books);
        return "book";
    }

    /***
     * Finds book based on search query
     * @param query String title, author, publisher, description, or isbn of book
     * @param model Model Object
     * @return view template
     */
    @PostMapping("/search")
    public String search(@RequestParam String query, Model model) {
        List<Book> searchResults = bookRepository.findByTitleContainingOrAuthorContainingOrPublisherContainingOrDescriptionContainingOrIsbnContaining(query, query, query, query, query);
        model.addAttribute("books", searchResults);
        return "book";
    }

    @GetMapping("/books/sortByTitle")
    public String sortByTitle(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("books", books);
        return "book";
    }

    @GetMapping("/books/sortByAuthor")
    public String sortByAuthor(Model model) {
        List<Book> books = (List<Book>) bookRepository.findAll(Sort.by(Sort.Direction.ASC, "author"));
        model.addAttribute("books", books);
        return "book";
    }


    /***
     * Adds books to book repository.
     * @param isbn String isbn of book
     * @param title String title of book
     * @param author String author of book
     * @param publisher String publisher of book
     * @param description String description of book
     * @return view template
     */
    @PostMapping("/addBook")
    public String addBook(@RequestParam String isbn, @RequestParam String title,
                          @RequestParam String author, @RequestParam String publisher,
                          @RequestParam String description) {
        Book newBook = new Book(isbn, title, author, publisher, description);
        bookRepository.save(newBook);
        System.out.println("New book added: " + newBook.getTitle());
        return "redirect:/books";
    }




}