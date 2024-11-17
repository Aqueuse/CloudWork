using CloudWork.Services;
using KnowledgePicker.WordCloud;
using KnowledgePicker.WordCloud.Coloring;
using KnowledgePicker.WordCloud.Drawing;
using KnowledgePicker.WordCloud.Layouts;
using KnowledgePicker.WordCloud.Primitives;
using KnowledgePicker.WordCloud.Sizers;
using SkiaSharp;

namespace CloudWork.DataParsing;

public class WordsToCloud(Source_Offres sourceOffres) {
    private List<string> words = new ();
    private List<string> descriptions = new ();
    readonly FranceTravail franceTravail = new ();

    public async Task<bool> GenerateCloudFromKeyword(string keyword) {
        if (sourceOffres == Source_Offres.FRANCE_TRAVAIL) {
            descriptions = await franceTravail.GetWordsFromKeywork(keyword);

            if (descriptions.Count == 0) {
                return false;
            }
            
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
        // get the words frequency on a dictionnary (skip useless french words, etc, see blacklist)
        
        // effectively draw the cloud
        var frequencies = new Dictionary<string, int>();
        
        // ...collect word frequencies somehow...
        IEnumerable<WordCloudEntry> wordEntries = frequencies.Select(p => new WordCloudEntry(p.Key, p.Value));
        
        var wordCloud = new WordCloudInput(wordEntries) {
            Width = 1024,
            Height = 256,
            MinFontSize = 8,
            MaxFontSize = 32
        };
        
        var sizer = new LogSizer(wordCloud);
        using var engine = new SkGraphicEngine(sizer, wordCloud);
        var layout = new SpiralLayout(wordCloud);
        var colorizer = new RandomColorizer();
        var wcg = new WordCloudGenerator<SKBitmap>(wordCloud, engine, layout, colorizer);
        
        using var final = new SKBitmap(wordCloud.Width, wordCloud.Height);
        using var canvas = new SKCanvas(final);

        // Draw on white background.
        canvas.Clear(SKColors.White);
        using var bitmap = wcg.Draw();
        canvas.DrawBitmap(bitmap, 0, 0);

        // Save to PNG
        using var data = final.Encode(SKEncodedImageFormat.Png, 100);
        await using var writer = File.Create("cloud.png");
        data.SaveTo(writer);
    }
}