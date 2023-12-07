package amazin.bookstore.Controllers;

import amazin.bookstore.Book;
import amazin.bookstore.Recommendation;
import amazin.bookstore.Recommendation.Weighting;
import amazin.bookstore.User;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.RecommendationRepository;
import amazin.bookstore.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private final RecommendationRepository recommendationRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BookRepository bookRepository;

    public RecommendationController(RecommendationRepository recommendationRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public static double JaccardIndex(Set<Book> userBooks, Set<Book> otherUserBooks) {
        HashSet<Book> union = new HashSet<>(userBooks);
        union.addAll(otherUserBooks);
        HashSet<Book> intersection = new HashSet<>(userBooks);
        intersection.retainAll(otherUserBooks);

        return (double) intersection.size() / union.size();
    }

    @GetMapping("")
    public String showRecommendations(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/user/login";
        }
        User user = userRepository.findById((Long) session.getAttribute("userId")).orElseThrow();
        List<Recommendation> recommendations = recommendationRepository.findByUserOrderByWeightDesc(user);
        ArrayList<Book> recommendedBooks = new ArrayList<>();
        for (Recommendation r : recommendations) {
            recommendedBooks.add(r.getBook());
        }
        model.addAttribute("recommendations", recommendedBooks);
        return "recommendations";
    }

    @GetMapping("/refresh")
    public String refreshRecommendations(HttpSession session, Model model) {
        User user = userRepository.findById((Long) session.getAttribute("userId")).orElse(null);
        if (user == null) return "redirect:/user/login";

        List<Book> history = user.getPurchasedBooks();
        List<Book> catalogue = (List<Book>) bookRepository.findAll(Sort.unsorted());
        cleanOldRecommendations(user);

        for (Book b : catalogue) {
            if (!history.contains(b)) {
                for (Book b1 : history) {
                    boolean recommended = false;
                    if (b1.getAuthor().equals(b.getAuthor())) {
                        recommend(user, b, Weighting.SAME_AUTHOR.value());
                        recommended = true;
                    }
                    if (b1.getDescription().equals(b.getDescription())) {
                        recommend(user, b, Weighting.SAME_GENRE.value());
                        recommended = true;
                    }
                    if (recommended) {
                        break;
                    }
                }
            }
        }

        return "redirect:/recommendations";
    }

    public void recommend(User user, Book book, int weight) {
        Recommendation recommendation = recommendationRepository.findByUserAndBook(user, book);
        int totalWeight = weight;
        if (recommendation != null) {
            totalWeight += recommendation.getWeight();
            recommendation.setWeight(totalWeight);
        } else {
            recommendation = new Recommendation(user, book, totalWeight);
        }
        recommendationRepository.save(recommendation);
    }

    public void cleanOldRecommendations(User user) {
        List<Book> purchasedBooks = user.getPurchasedBooks();
        for (Recommendation r : recommendationRepository.findByUser(user)) {
            if (purchasedBooks.contains(r.getBook())) {
                recommendationRepository.delete(r);
            } else {
                r.setWeight(0);
                recommendationRepository.save(r);
            }
        }
    }
}
