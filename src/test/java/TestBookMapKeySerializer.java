import amazin.bookstore.Book;
import amazin.bookstore.BookMapKeySerializer;
import amazin.bookstore.Inventory;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Tests the BookMapKeySerializer
 * @author Yaw Asamoah
 */
public class TestBookMapKeySerializer {

    /**
     * Test if BookMapKeySerializer works when serializing a book only
     *
     * @throws IOException      IOException, exception when writing json
     */
    @Test
    public void testSerializeBookOnly() throws IOException {
        //Test with a non-null book
        Book testBook = new Book("20231117",
                "TestTitle",
                "TestAuthor",
                "TestPublisher",
                "TestDescription",
                10.00
        );

        Writer jsonWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        jsonGenerator.writeStartObject();
        SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();
        new BookMapKeySerializer().serialize(testBook, jsonGenerator, serializerProvider);
        jsonGenerator.flush();
        String expectedOutput = "{\"{\\\"isbn\\\":\\\"20231117\\\",\\\"title\\\":\\\"TestTitle\\\",\\\"author\\\":\\\"TestAuthor\\\",\\\"publisher\\\":\\\"TestPublisher\\\",\\\"description\\\":\\\"TestDescription\\\",\\\"price\\\":10.0,\\\"id\\\":null}\"";
        Assertions.assertEquals(jsonWriter.toString(), expectedOutput);


        //Test with a null book
        jsonWriter = new StringWriter();
        testBook = new Book();
        jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        jsonGenerator.writeStartObject();
        serializerProvider = new ObjectMapper().getSerializerProvider();
        new BookMapKeySerializer().serialize(testBook, jsonGenerator, serializerProvider);
        jsonGenerator.flush();
        expectedOutput = "{\"{\\\"isbn\\\":null,\\\"title\\\":null,\\\"author\\\":null,\\\"publisher\\\":null,\\\"description\\\":null,\\\"price\\\":0.0,\\\"id\\\":null}\"";
        Assertions.assertEquals(expectedOutput, jsonWriter.toString());

    }

    /**
     * Test if BookMapKeySerializer works when serializing an inventory object
     */
    @Test
    public void testSerializeInventory()
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
            String expectedOutput ="{\"id\":0,\"catalog\":{\"{\\\"isbn\\\":\\\"20231117\\\",\\\"title\\\":\\\"TestTitle\\\",\\\"author\\\":\\\"TestAuthor\\\",\\\"publisher\\\":\\\"TestPublisher\\\",\\\"description\\\":\\\"TestDescription\\\",\\\"price\\\":10.0,\\\"id\\\":null}\":10}}";
            Assertions.assertEquals(expectedOutput, jsonResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
