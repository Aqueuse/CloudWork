using CloudWork.Services;

namespace CloudWork.DataParsing;

public class WordsToCloud {
    private Source_Offres sourceOffres;
    
    private List<string> words = new ();
    private List<string> descriptions = new ();

    public WordsToCloud(Source_Offres sourceOffres) {
        this.sourceOffres = sourceOffres;
    }

    public async Task<bool> GenerateCloud() {
        if (sourceOffres == Source_Offres.FRANCE_TRAVAIL) {
            descriptions = FranceTravail.GetWords();
            
            words = CurateWordsListFromDescriptions(descriptions);
        
            await DrawCloud(words);

            return true;
        }

        return false;
    }

    public List<string> CurateWordsListFromDescriptions(List<string> descriptions) {
        return new List<string>();
    }

    public async Task DrawCloud(List<string> words) {
        // effectively draw the cloud
    }
}