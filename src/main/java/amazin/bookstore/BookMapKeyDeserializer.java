package amazin.bookstore;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Custom Deserializer for Book objects, when used as a key in maps
 * @author Yaw Asamoah
 */
public class BookMapKeyDeserializer extends KeyDeserializer {

    /**
     * Custom Deserializer for Book objects, when used as a key in maps
     *
     * @param key       String, value of serialized book object in string properties
     * @param ctxt      DeserializationContext, Context for the process of deserialization a single root-level value. Used to allow passing in configuration settings and reusable temporary objects
     * @return          Book, book object created from deserialization
     * @throws IOException              IOException, exception when writing json
     * @throws JsonProcessingException  JsonProcessingException, exception when writing json
     */
    @Override
    public Book deserializeKey(
            String key,
            DeserializationContext ctxt) throws IOException,
            JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Book book =  mapper.readValue(key, Book.class);
        return book;
    }
}