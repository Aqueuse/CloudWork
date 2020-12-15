import java.io.IOException;

public class wordcloud {
    public static void main (String[] Args) throws IOException, Exception {
        getJSONfromDistantAPI.grabJSON("cuisinier");

        if (getJSONfromDistantAPI.codeResponse <400) {
            extractJSON.extractWords(loadJSON.loadFromFile("ressources/data.json"));
            // TODO : save the words in mongoDB
        }

        runRscript.exec("ressources/cloud.r");
        System.out.println("R script executed");
    }
}