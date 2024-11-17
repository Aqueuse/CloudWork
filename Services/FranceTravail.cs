using System.Net;

namespace CloudWork.Services;

using System.Net.Http.Json;
using System.Net.Http.Headers;

/// <summary>
/// Class to call France Travail API and access the offers based on a keyword
/// </summary>
public class FranceTravail {
    private readonly HttpClient _httpClient = new();

    private readonly List<string> accessData;

    public FranceTravail() {
        accessData = ReadAccessData();
    }
    
    private List<string> ReadAccessData() {
        return File.ReadLines("/ressources/access_FranceTravail.txt").ToList();
    }

    private void WriteAccessData(List<string> lines) {
        File.WriteAllLines("/ressources/access_FranceTravail.txt", lines);
    }
    
    public async Task<List<string>> GetWordsFromKeywork(string keyword) {
        List<string> descriptions = new List<string>();
        
        // verify validity of the access token
        for (int i = 0; i < 5; i++) {
            HttpStatusCode statusCode = await TestAccess(accessData[3]);

            if (statusCode == HttpStatusCode.BadRequest | statusCode == HttpStatusCode.InternalServerError) {
                // refresh token
                accessData[3] = await RefreshAccessToken();
            }

            // if token is still okay, try to get data
            if (statusCode == HttpStatusCode.OK) {
                descriptions = await GetDescriptionsFromKeyword(keyword, accessData[3]);
            }
        }
        
        return descriptions;
    }
    
    // get data
    private async Task<List<string>> GetDescriptionsFromKeyword(string keyword, string token) {
        List<string> descriptions = new List<string>();

        var request = new HttpRequestMessage {
            Method = HttpMethod.Get,
            RequestUri = new Uri("https://api.francetravail.io/partenaire/offresdemploi/v2/offres/search?motsCles="+keyword),
            Headers =
            {
                { "Authorization", "Bearer "+token },
                { "Accept", "application/json" }
            }
        };
        using var response = await _httpClient.SendAsync(request);
        response.EnsureSuccessStatusCode();
        
        var body = await response.Content.ReadAsStringAsync();
        
        // how to access descriptions on body ? Serialization ?
        return descriptions;
    }

    private async Task<HttpStatusCode> TestAccess(string token) {
        var request = new HttpRequestMessage {
            Method = HttpMethod.Get,
            RequestUri = new Uri("https://api.francetravail.io/partenaire/offresdemploi/v2/offres/search"),
            Headers =
            {
                { "Authorization", "Bearer "+token },
                { "Accept", "application/json" }
            }
        };
        using var response = await _httpClient.SendAsync(request);
        return response.StatusCode;
    }
    
    // get/refresh access token
    private async Task<string> RefreshAccessToken() {
        var client = new HttpClient();
        var request = new HttpRequestMessage(HttpMethod.Post, "https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=%2Fpartenaire");
        request.Headers.Add("Accept", "application/json;charset=UTF-8");
        var collection = new List<KeyValuePair<string, string>>();
        collection.Add(new("grant_type", "client_credentials"));
        collection.Add(new("client_id", accessData[0]));
        collection.Add(new("client_secret", accessData[1]));
        collection.Add(new("scope", "api_offresdemploiv2 o2dsoffre"));
        var content = new FormUrlEncodedContent(collection);
        request.Content = content;
        var response = await client.SendAsync(request);
        response.EnsureSuccessStatusCode();
        
        var responseContent = response.Content.ReadAsStringAsync();
        
        // TODO : retrieve access token
        
        WriteAccessData(new List<string>(){accessData[0], accessData[1], responseContent.Result} );

        return "plop";
    }
}