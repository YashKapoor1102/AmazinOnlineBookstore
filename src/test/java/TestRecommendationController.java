import amazin.bookstore.AccessingDataJpaApplication;
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
}
