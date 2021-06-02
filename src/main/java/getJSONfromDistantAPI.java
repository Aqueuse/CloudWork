import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class getJSONfromDistantAPI {
    public static int codeResponse = 0;

    public static void grabJSON(String motcle) throws Exception {
        // try a GET with the saved token
        // if 401 unauthorized, ask a new token, save it and retry
        try {
            doGet(motcle);
        }
        catch (IOException ioException) {
            System.out.println("credentials are expired, request new credentials");
            RequestCredentials();
            doGet(motcle);
        }
    }

    private static void RequestCredentials() throws Exception {
        URL url = new URL("https://entreprise.pole-emploi.fr/connexion/oauth2/access_token?realm=%2Fpartenaire");
        HttpURLConnection connexion = (HttpURLConnection) url.openConnection();

        connexion.setRequestMethod("POST");
        connexion.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connexion.setRequestProperty("Accept", "application/json");
        connexion.setDoOutput(true);

        String credentials = "grant_type=client_credentials&" +
                "client_id=PAR_the_id" +
                "client_secret=the_secret" +
                "scope=api_offresdemploiv2 application_PAR_the_id o2dsoffre";

        OutputStream out = connexion.getOutputStream();
        byte[] byteCred = credentials.getBytes(StandardCharsets.UTF_8);
        out.write(byteCred);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connexion.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String responseLine = null;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        String token = loadJSON.loadFromString(response.toString()).getString("access_token");
        System.out.println("Credentials reloaded : "+token);

        // write the token in ressources/token.txt
        FileWriter tokenWrite = new FileWriter("ressources/token.txt", StandardCharsets.UTF_8, false);
        tokenWrite.append(token);
        tokenWrite.close();
    }

    private static void doGet(String motcle) throws IOException {
        BufferedReader tokenRead = new BufferedReader(new FileReader("ressources/token.txt", StandardCharsets.UTF_8));
        String token = tokenRead.readLine();

        URL url = new URL("https://api.emploi-store.fr/partenaire/offresdemploi/v2/offres/search?motsCles=" + motcle);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Authorization", "Bearer " + token);
        connection.setDoOutput(true);

        codeResponse = connection.getResponseCode();
        System.out.println("code response : " + codeResponse);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String responseLine = null;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        // write the JSON result to data.json
        FileWriter jsonOut = new FileWriter(new File("ressources/data.json"), StandardCharsets.UTF_8, false);
        jsonOut.append(response);
        jsonOut.close();
        System.out.println("JSON retrieved ! HTTP code : " + codeResponse);
    }
}
