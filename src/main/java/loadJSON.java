import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.IOException;

public class loadJSON {
    public static JsonNode loadFromFile(String path) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode JSONnode = null;
        
        try {
            File data = new File(path);
            factory = mapper.getFactory();
            JsonParser parser = factory.createParser(data);
            JSONnode = mapper.readTree(parser);

        } catch (IOException io) {
            System.out.println(io + "in class loadJSON in method loadFromFile");
        }

        return JSONnode;
    }

    public static JsonNode loadFromString(String source) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode JSONnode = null;

        try {
            JSONnode = mapper.readTree(source);

        } catch (JsonProcessingException Joups) {
            System.out.println(Joups + "/ in class loadJSON in method loadFromString");
        }

        return JSONnode;
    }
}
