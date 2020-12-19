public class wordcloud {
    public static void main(String[] Args) throws Exception {
        getJSONfromDistantAPI.grabJSON("boulanger");
        //extractJSON.extractWords(loadJSON.loadFromFile("ressources/data.json"));
        // TODO : save the words in mongoDB
        //        runRscript.exec("ressources/cloud.r");
    }
}


