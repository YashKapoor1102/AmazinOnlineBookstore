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

    /**
     * Tests listBooks method in Book Controller just to
     * check if the table is there - then view is rendered
     */
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

    /**
     * Tests addBook method by adding a book and checking if it appears in the table
     * by using listBooks method
     */
    @Test
    void addBook() {
        // Add a book
        String url = "http://localhost:" + port + "/addBook";
        HttpEntity<String> request = new HttpEntity<>("isbn=11111111&title=Cloudy+Sky&author=Bob+West&publisher=tester+Co&description=fantasy&price=10", headers);
        restTemplate.postForEntity(url, request, String.class);

        // Check if it's been added
        String url2 = "http://localhost:" + port + "/books";
        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(url2, String.class);
        assertThat(responseEntity2.getBody()).contains("Cloudy Sky");
    }

    /**
     * Tests the search method by
     * checking if relevant books appear based on the search query
     */
    @Test
    void search() {
        // Adding books
        String url = "http://localhost:" + port + "/addBook";
        HttpEntity<String> request = new HttpEntity<>("isbn=12345&title=Red+Dragon&author=Jacob+B&publisher=Pub+Corp&description=fantasy&price=10", headers);
        restTemplate.postForEntity(url, request, String.class);
        HttpEntity<String> request2 = new HttpEntity<>("isbn=222222&title=Little+Mermaid&author=Sabrina+B&publisher=Trust&description=disney&price=10", headers);
        restTemplate.postForEntity(url, request2, String.class);

        // Making sure only relevant result appears
        String url2 = "http://localhost:" + port + "/search";
        HttpEntity<String> request3 = new HttpEntity<>("query=Red+Dragon", headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2, request3, String.class);
        assertThat(responseEntity.getBody()).contains("Red Dragon");
        assertThat(responseEntity.getBody()).doesNotContain("Little Mermaid");
    }

    /**
     * Tests sortByTitle method by
     * checking if books are sorted alphabetically based on title
     */
    @Test
    void sortByTitle() {
        String url = "http://localhost:" + port + "/books/sortByTitle";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from the title column from each row
        String[] actualTitles = tableRows.select("td:nth-child(3)").eachText().toArray(String[]::new);
        assertThat(actualTitles)
                .containsExactly("Cloudy Sky", "Little Mermaid", "Red Dragon", "Sapiens: A Brief History of Humankind", "The Book Of Two Ways", "The Five People You Meet In Heaven", "The Time Machine");
    }

    /**
     * Tests sortByAuthor method by
     * checking if books are sorted alphabetically based on author
     */
    @Test
    void sortByAuthor() {
        String url = "http://localhost:" + port + "/books/sortByAuthor";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from the author column from each row
        String[] actualAuthors = tableRows.select("td:nth-child(4)").eachText().toArray(String[]::new);
        assertThat(actualAuthors)
                .containsExactly("Bob West", "H.G.Wells", "Jacob B", "Jodi Picoult", "Mitch Albom", "Sabrina B", "Yuval Noah Harari");
    }
}
