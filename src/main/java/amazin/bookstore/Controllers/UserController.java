/**
 * @author Yash Kapoor (Student ID: 101163338)
 * @version Milestone 1
 */

package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import jakarta.servlet.http.HttpSession;
import amazin.bookstore.ShoppingCart;
import amazin.bookstore.User;
import amazin.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Controller Class for managing users
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Allows the user to register by entering a username and a password
     * @param model     the model object, the model used to pass data to the view
     * @return  the name of the view template called "registerUser" that renders the content for the register page
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registerUser";
    }

    /**
     * The action that occurs after the user clicks the "Submit" button on the registration page
     * @param user      a User object, the username and password inputted by the user
     * @param model     the model object, the model used to pass data to the view
     * @return  the view template of the registration page if there is an error, the login page otherwise
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null) {
            // Username already exists
            model.addAttribute("registrationError", "Username already exists. Please choose another one.");
            return "registerUser";
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        userRepository.save(user);
        return "redirect:/user/login";
    }

    /**
     * Allows the user to log in with their credentials (i.e., username and password)
     * @return  the view template of the login page
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "loginUser";
    }


    /**
     * The action that occurs after the user clicks on the "Submit" button on the login page
     * @param username      a String, the username entered by the user
     * @param password      a String, the password entered by the user
     * @param model     the model object, the model used to the pass data to the view
     * @return  the view template of the books page if there are no errors, the login page otherwise
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);

//        if(user.isOwner()) {
//             return ""    // Redirect to appropriate page - Commented out until the view template is created
//        }
        if (user != null && password.equals(user.getPassword())) {
            session.setAttribute("userId", user.getId());

            return "redirect:/books";
        } else {
            model.addAttribute("loginError", "Invalid username or password. Try again!");
            return "loginUser";
        }
    }

    /**
     * Displays a list of users that are registered including the bookstore owner
     * @param model     the model object, the model used to pass data to the view
     * @return  the appropriate view template that displays the list of users
     */
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        return "listUsers";
    }


    @GetMapping("/purchased-books")
    public String listPurchasedBooks(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/user/login";
        }
        List<Book> purchasedBooks = user.getPurchasedBooks();
        model.addAttribute("purchasedBooks", purchasedBooks);
        return "listPurchasedBooks";
    }


}





