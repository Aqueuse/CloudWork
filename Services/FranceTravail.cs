namespace CloudWork.Services;

using System.Net.Http.Json;
using System.Net.Http.Headers;

/// <summary>
/// Class to call France Travail API and access the offers based on a keyword
/// </summary>
public class FranceTravail {
    private readonly HttpClient _httpClient = new();

    public static List<string> GetWords() {
        List<string> descriptions = new List<string>();
        
        // try to get data
        
        // if code response is 400 or 500
        for (int i = 0; i < 5; i++) {
            // refresh token
            // retry to get data with new token
            // if success, return
        }
        
        // if still failed after 5 attempts
            // send user to gif page and return
        return new List<string>();
    }
    
    // get data
    public async Task<List<string>> GetDescriptionsFromKeyword(string keyword) {
        List<string> descriptions = new List<string>();

        var request = new HttpRequestMessage {
            Method = HttpMethod.Get,
            RequestUri = new Uri("https://api.francetravail.io/partenaire/offresdemploi/v2/offres/search"),
            Headers =
            {
                { "Authorization", "Bearer jtlqNnfDNHjFYXGtteySufAVrhU" },
                { "Accept", "application/json" },
            },
        };
        using var response = await _httpClient.SendAsync(request);
        response.EnsureSuccessStatusCode();
        var body = await response.Content.ReadAsStringAsync();
        
        // will allow us to know if we need to regenerate access token
        Console.WriteLine(response.StatusCode);

        return descriptions;
    }
    
    // get/refresh access token
    private async Task<string> GetAccessToken() {
        var client = new HttpClient();
        var request = new HttpRequestMessage(HttpMethod.Post, "https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=%2Fpartenaire");
        request.Headers.Add("Accept", "application/json;charset=UTF-8");
        var collection = new List<KeyValuePair<string, string>>();
        collection.Add(new("grant_type", "client_credentials"));
        collection.Add(new("client_id", "PAR_cloudwork_1e00df847d310028f64ea6257f9c9290fbad9ea928da4bf0b346800a7a659733"));
        collection.Add(new("client_secret", "c6d9880358e27bc2a3267ae1466f8764a03eae8b51d19b373020956b5cb46c41"));
        collection.Add(new("scope", "api_offresdemploiv2 o2dsoffre"));
        var content = new FormUrlEncodedContent(collection);
        request.Content = content;
        var response = await client.SendAsync(request);
        response.EnsureSuccessStatusCode();
        
        var responseContent = response.Content.ReadAsStringAsync();

        return "plop";
    }
}