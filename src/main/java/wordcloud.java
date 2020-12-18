public class wordcloud {
    public static void main(String[] Args) throws Exception {
        extractJSON.extractWords(loadJSON.loadFromFile("ressources/data.json"));
    }
}

        //        getJSONfromDistantAPI.grabJSON("cuisinier");
        //        if (getJSONfromDistantAPI.codeResponse <400) {
        //            TODO : save the words in mongoDB
        //        }

        //        runRscript.exec("ressources/cloud.r");
        //        System.out.println("R script executed");