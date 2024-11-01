using Microsoft.AspNetCore.Components;

namespace CloudWork.Layout;

public partial class CloudWork : ComponentBase {
    private bool collapseDescription = true;
    
    private string descriptionClass => collapseDescription ? "normalDescription" : "collapsedDescription";

//    private string ImageSrc => $"{imagePath}?{Guid.NewGuid()}";
    
    private void Search() {
        collapseDescription = true;
        
        ShowLoadingImage();

        DrawCloudFromAPI();
        
        ShowResults();
    }
    
    private List<string> wordsresult = [];

    private async void DrawCloudFromAPI() {
        wordsresult = await GetWordsFromApi();

        await WriteResultInFile();
        
        await DrawCloud();
    }

    private Task<List<string>> GetWordsFromApi() {
        wordsresult.Add("");
        
        return Task.FromResult(wordsresult);
    }

    private static Task WriteResultInFile() {
        // write the result on file

        return Task.CompletedTask;
    }

    private static Task DrawCloud() {
        // call the github action to execute R with the file Result
        
        return Task.CompletedTask;
    }
}