package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import amazin.bookstore.ShoppingCart;
import amazin.bookstore.User;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.ShoppingCartRepository;
import amazin.bookstore.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller Class for managing the shopping cart for each user.
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a book to the shopping cart using the
     * bookId as an identifier.
     *
     * @param bookId    a Long, the ID of the book to be added
     * @param session   a HttpSession object, keeps track of the user's session
     *                  (used to figure out which user is logged in)
     * @param model     a Model object, the model used to pass data to the view
     * @return      the name of the view template called "viewCart" that
     *              renders the content for the Shopping Cart page.
     *              If the user is not logged in, then they are redirected to the Login page.
     */
    @PostMapping("/add")
    public String addBookToCart(@RequestParam Long bookId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userRepository.findById(userId).orElse(null);

        ShoppingCart cart;
        if(user != null) {
            cart = user.getShoppingCart();
            user.setShoppingCart(cart);

            Book book = bookRepository.findById(bookId).orElse(null);

            if(book == null) {
                model.addAttribute("bookNotFound", "The Book ID provided is incorrect. Try again!");
                return "viewCart";
            }
            if(cart != null) {
                cart.addBook(book);
                userRepository.save(user); // Saving user will cascade and save the cart as well
            }

            return "redirect:/cart/view";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Removes a book from the shopping cart using the
     * bookId as an identifier.
     *
     * @param bookId    a Long, the ID of the book to be removed
     * @param session   a HttpSession object, keeps track of the user's session
     *                  (used to figure out which user is logged in)
     * @param model     a Model object, the model used to pass data to the view
     * @return          If the userId is not found, then the individual is redirected to the Login page.
     *                  Otherwise, the name of the view template called "viewCart" that renders
     *                  the content for the Shopping Cart page is returned.
     */
    @PostMapping("/remove")
    public String removeBookFromCart(@RequestParam Long bookId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId"); // Retrieve user ID from session
        if (userId == null) {
            return "redirect:/login"; // Redirect to log in if user is not logged in
        }

        User user = userRepository.findById(userId).orElse(null);
        ShoppingCart cart = user != null ? user.getShoppingCart() : null;

        Book book = bookRepository.findById(bookId).orElse(null);

        if(book == null) {
            model.addAttribute("bookNotFound", "The Book ID provided is incorrect. Try again!");
            return "viewCart";
        }
        if (cart != null && cart.getBooks().contains(book)) {
            cart.removeBook(book);
            userRepository.save(user);
        }

        return "redirect:/cart/view";
    }


    /**
     * Displays the Shopping Cart and its contents
     * @param session   a HttpSession object, keeps track of the user's session
     *                  (used to figure out which user is logged in)
     * @param model     a Model object, the model used to pass data to the view
     * @return          If the userId is not found, then the individual is redirected to the Login page.
     *                  Otherwise, the name of the view template called "viewCart" that renders
     *                  the content for the Shopping Cart page is returned.
     */
    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId"); // Retrieve user ID from session
        if (userId == null) {
                return "redirect:/login";   // Redirect to log in if user is not logged in
        }

        User user = userRepository.findById(userId).orElse(null);
        ShoppingCart cart = user != null ? user.getShoppingCart() : null;

        model.addAttribute("cart", cart);

        return "viewCart";
    }

}
