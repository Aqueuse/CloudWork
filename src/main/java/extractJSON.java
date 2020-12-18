import java.io.FileWriter;
import javax.json.*;

public class extractJSON {
    public static void extractWords(JsonObject jsonObject) throws Exception {
        JsonArray brutResult;
        String finalResult = "";
        FileWriter wordsWrite = new FileWriter("ressources/words.txt", false);

        brutResult = jsonObject.getJsonArray("resultats");

        for (int r = 0; r < brutResult.size(); r++) {
            finalResult = finalResult.concat(brutResult.get(r).asJsonObject().getString("description") + " ");
        }

        wordsWrite.append(finalResult);
        System.out.println(finalResult);

        wordsWrite.close();
        System.out.println("words extracted in resources/words.txt");
    }
}
