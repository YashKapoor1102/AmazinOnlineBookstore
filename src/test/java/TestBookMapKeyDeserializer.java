import amazin.bookstore.Book;
import amazin.bookstore.BookMapKeyDeserializer;
import amazin.bookstore.Inventory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * Tests the BookMapKeyDeserializer
 * @author Yaw Asamoah
 */
public class TestBookMapKeyDeserializer {

    /**
     * Test if BookMapKeyDeserializer works when deserializing a book only
     *
     * @throws IOException      IOException, exception when parsing json
     */
    @Test
    public void testDeserializeBookOnly() throws IOException {
        //Test with a non-null book
        Book testBook = new Book("20231117",
                "TestTitle",
                "TestAuthor",
                "TestPublisher",
                "TestDescription",
                10.00
        );

        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"isbn\":\"20231117\",\"title\":\"TestTitle\",\"author\":\"TestAuthor\",\"publisher\":\"TestPublisher\",\"description\":\"TestDescription\",\"price\":10.0,\"id\":null}";
        JsonParser parser = mapper.getFactory().createParser(json);
        DeserializationContext ctxt = mapper.getDeserializationContext();
        parser.nextToken();
        parser.nextToken();
        parser.nextToken();
        Book book = new BookMapKeyDeserializer().deserializeKey(json, ctxt);
        Assertions.assertEquals(testBook, book);


        //Test with a null book
        json = "{\"isbn\":null,\"title\":null,\"author\":null,\"publisher\":null,\"description\":null,\"price\":null,\"id\":null}";
        parser = mapper.getFactory().createParser(json);
        ctxt = mapper.getDeserializationContext();
        parser.nextToken();
        parser.nextToken();
        parser.nextToken();
        Book book3 = new BookMapKeyDeserializer().deserializeKey(json, ctxt);
        Assertions.assertNull(book3.getId());
        Assertions.assertNull(book3.getAuthor());
        Assertions.assertNull(book3.getDescription());
        Assertions.assertNull(book3.getTitle());
        Assertions.assertNull(book3.getIsbn());
        Assertions.assertNull(book3.getPublisher());

    }



    /**
     * Test if BookMapKeyDeserializer works when deserializing an inventory object
     */
   @Test
    public void testDeserializeInventory()
    {
        Inventory inventory = new Inventory();
        Book testBook = new Book("20231117",
                "TestTitle",
                "TestAuthor",
                "TestPublisher",
                "TestDescription",
                10.00
        );
        inventory.setBookStock(testBook, 10);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inventory);
            jsonResult = jsonResult.replaceAll("\\s+","");

            Inventory testInventory = mapper.readValue(jsonResult, Inventory.class);
            Assertions.assertEquals(inventory.getId(), testInventory.getId());
            Assertions.assertEquals(inventory.getCatalog().size(), testInventory.getCatalog().size());
            Assertions.assertEquals(inventory.getBookStock(testBook), testInventory.getBookStock(testBook));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
