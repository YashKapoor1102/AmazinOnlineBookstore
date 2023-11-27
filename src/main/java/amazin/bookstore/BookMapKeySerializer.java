package amazin.bookstore;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Custom Serializer for Book objects, when used as a key in maps
 * @author Yaw Asamoah
 */
public class BookMapKeySerializer extends JsonSerializer<Book> {

    /**
     * Serialize Book object, when book is a key in a map
     *
     * @param value         Book, the book that will be serialzied
     * @param gen           JsonGenerator, Generator used to output resulting Json content
     * @param serializers   Provider that can be used to get serializers for serializing Objects value contains, if any
     * @throws IOException  IOException, exception when writing json
     * @throws JsonProcessingException JsonProcessingException, exception when writing json
     */
    @Override
    public void serialize(Book value,
                          JsonGenerator gen,
                          SerializerProvider serializers)
            throws IOException, JsonProcessingException
    {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, value);
        gen.writeFieldName(writer.toString());

    }


}