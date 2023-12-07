import amazin.bookstore.AccessingDataJpaApplication;
import amazin.bookstore.Book;
import amazin.bookstore.Controllers.RecommendationController;
import amazin.bookstore.repositories.BookRepository;
import amazin.bookstore.repositories.RecommendationRepository;
import amazin.bookstore.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccessingDataJpaApplication.class)
public class TestRecommendationController {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Value(value = "${local.server.port}")
    private int port;

    @Test
    public void redirectToLoginPage() {
        String url = "http://localhost:" + port + "/recommendations";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getBody()).contains("Login Form");
    }

    @Test
    public void displayRecommendationsPage() {
        // Register and login
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> request = new HttpEntity<>("username=test_user&password=pwd", headers);
        String registerUrl = "http://localhost:" + port + "/user/register";
        restTemplate.postForEntity(registerUrl, request, String.class);
        String loginUrl = "http://localhost:" + port + "/user/login";
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(loginUrl, request, String.class);
        String cookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.add("Cookie", cookie);
        String url = "http://localhost:" + port + "/recommendations";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<String>(getHeaders), String.class);
        assertThat(response.getBody()).contains("My Recommendations");
    }

    @Test
    public void testJaccardIndex() {
        Book book1 = new Book("123", "Book1", "Author1",
                "Publisher1", "Desc1", 1);
        Book book2 = new Book("456", "Book2", "Author2",
                "Publisher2", "Desc2", 1);
        Book book3 = new Book("789", "Book3", "Author3",
                "Publisher3", "Desc3", 1);
        HashSet<Book> set1 = new HashSet<>();
        HashSet<Book> set2 = new HashSet<>();
        assertThat(Double.isNaN(RecommendationController.JaccardIndex(set1, set2))).isEqualTo(true);

        set1.add(book1); set1.add(book2);
        set2.add(book1); set2.add(book3);
        assertThat(RecommendationController.JaccardIndex(set1, set2)).isEqualTo((double) 1 /3);
    }
}
