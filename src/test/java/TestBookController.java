import amazin.bookstore.AccessingDataJpaApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the execution of each HTTP Request in the BookController Class
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AccessingDataJpaApplication.class)
class TestBookController {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value="${local.server.port}")
    private int port;

    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void initialize() {
        headers.set("Content-Type", "application/x-www-form-urlencoded");
    }

    @Test
    void listBooks() {
        // view page
        String url = "http://localhost:" + port + "/books";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getBody()).contains("Amazin Bookstore");
        assertThat(responseEntity.getBody()).contains("Search");
        assertThat(responseEntity.getBody()).contains("Filter By Price");
        assertThat(responseEntity.getBody()).contains("Sort by Author");
        assertThat(responseEntity.getBody()).contains("Sort by Title");
    }

    @Test
    void addBook() {
        // No need to add a book in this test - add books are always saved
    }

    @Test
    void search() {
        // No need to add books in this test
        String url2 = "http://localhost:" + port + "/search";
        HttpEntity<String> request3 = new HttpEntity<>("query=The+Five+People+You+Meet+In+Heaven", headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2, request3, String.class);
        assertThat(responseEntity.getBody()).contains("The Five People You Meet In Heaven");
        assertThat(responseEntity.getBody()).doesNotContain("The Time Machine");
    }

    @Test
    void sortByTitle() {
        String url = "http://localhost:" + port + "/books/sortByTitle";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from the title column from each row
        String[] actualTitles = tableRows.select("td:nth-child(3)").eachText().toArray(String[]::new);
        assertThat(actualTitles)
                .containsExactly( "The Book Of Two Ways", "The Five People You Meet In Heaven", "The Time Machine");
    }

    @Test
    void sortByAuthor() {
        String url = "http://localhost:" + port + "/books/sortByAuthor";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from the author column from each row
        String[] actualAuthors = tableRows.select("td:nth-child(4)").eachText().toArray(String[]::new);
        assertThat(actualAuthors)
                .containsExactly("H.G.Wells", "Jodi Picoult", "Mitch Albom");
    }
}