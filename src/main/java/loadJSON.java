import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.StringReader;

public class loadJSON {
    private static JsonObject JSONnode;

    public static JsonObject loadFromFile(String path) {
        try {
            JsonReader reader = Json.createReader(new FileReader(path));
            JSONnode = (JsonObject) reader.read();
        }
        catch (Exception e) {
            System.out.println(e + "in class loadJSON in method loadFromFile");
        }
        return JSONnode;
    }

    public static JsonObject loadFromString(String source) {
        try {
            JsonReader reader = Json.createReader(new StringReader(source));
            JSONnode = (JsonObject) reader.read();
        }
        catch (Exception e) {
            System.out.println(e + "/ in class loadJSON in method loadFromString");
        }

        return JSONnode;
    }
}
