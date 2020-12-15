import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

public class extractJSON {
     public static void extractWords(JsonNode jsonNode) throws Exception, IOException {
        String brutResult = " ";
        FileWriter wordsWrite = new FileWriter(new File("ressources/words.txt"), false);

        for (int r = 0; r < jsonNode.get("resultats").size(); r++) {
            brutResult = brutResult.concat(jsonNode.get("resultats").get(r).get("description") + " ");
        }

        String tmpList[] = brutResult.split(" ");

        for (int s = 0; s < brutResult.split(" ").length; s++) {
            wordsWrite.append(tmpList[s] + "\n");
        }
        wordsWrite.close();
         System.out.println("words extracted in resources/words.txt");
    }
}