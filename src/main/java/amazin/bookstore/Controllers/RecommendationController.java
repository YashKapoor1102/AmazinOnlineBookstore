package amazin.bookstore.Controllers;

import amazin.bookstore.repositories.RecommendationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationController {

    @Autowired
    private final RecommendationRepository repository;

    public RecommendationController(RecommendationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recommendations")
    public String showRecommendations(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/user/login";
        }

        return "recommendations";
    }
}
