import amazin.bookstore.AccessingDataJpaApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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

    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    public void initialize() {

        headers.set("Content-Type", "application/x-www-form-urlencoded");
    }


    /**
     * Tests listBooks method in Book Controller just to
     * check if table is there - then view is rendered
     */
    @Test
    void listBooks() {

        //view page
        String url = "http://localhost:8080/books";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getBody()).contains("Amazin Bookstore");
        assertThat(responseEntity.getBody()).contains("Search/Filter");
        assertThat(responseEntity.getBody()).contains("Sort by Author");
        assertThat(responseEntity.getBody()).contains("Sort by Title");

    }

    /**
     * Tests addBook method by adding book and checking if it appears in table
     * by using listBooks method
     */
    @Test
    void addBook() {
        //Add book
        String url = "http://localhost:8080/addBook";
        HttpEntity<String> request = new HttpEntity<>("isbn=11111111&title=Cloudy+Sky&author=Bob+West&publisher=tester+Co&description=fantasy", headers);
        restTemplate.postForEntity(url, request, String.class);

        //Check if it's been added
        String url2 = "http://localhost:8080/books";
        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(url2, String.class);
        assertThat(responseEntity2.getBody()).contains("Cloudy Sky");
    }

    /**
     * tests search method by
     * Checks if relevant books appear based on search query
     */
    @Test
    void search() {
        //Adding books
        String url = "http://localhost:8080/addBook";
        HttpEntity<String> request = new HttpEntity<>("isbn=12345&title=Red+Dragon&author=Jacob+B&publisher=Pub+Corp&description=fantasy", headers);
        restTemplate.postForEntity(url, request, String.class);
        HttpEntity<String> request2 = new HttpEntity<>("isbn=222222&title=Little+Mermaid&author=Sabrina+B&publisher=Trust&description=disney", headers);
        restTemplate.postForEntity(url, request2, String.class);

        //Making sure only relevant result appears
        String url2 = "http://localhost:8080/search";
        HttpEntity<String> request3 = new HttpEntity<>("query=Red+Dragon", headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2,request3, String.class);
        assertThat(responseEntity.getBody()).contains("Red Dragon");
        assertThat(responseEntity.getBody()).doesNotContain("Little Mermaid");
    }


    /**
     * tests sortByTitle method by
     * checking if books are sorted alphabetically based on title
     */
    @Test
    void sortByTitle() {
        String url = "http://localhost:8080/books/sortByTitle";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from author column from each row
        String[] actualAuthors = tableRows.select("td:nth-child(2)").eachText().toArray(String[]::new);
        assertThat(actualAuthors)
                .containsExactly("Cloudy Sky", "Little Mermaid", "Red Dragon");
    }

    /**
     * tests sortByAuthor method by
     * checking if books are sorted alphabetically based on author
     */
    @Test
    void sortByAuthor() {
        String url = "http://localhost:8080/books/sortByAuthor";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        Document document = Jsoup.parse(responseEntity.getBody());
        Elements tableRows = document.select("tbody tr");

        // Extracting text from author column from each row
        String[] actualAuthors = tableRows.select("td:nth-child(3)").eachText().toArray(String[]::new);
        assertThat(actualAuthors)
                .containsExactly("Bob West", "Jacob B", "Sabrina B");
    }

}