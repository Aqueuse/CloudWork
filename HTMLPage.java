package automationPoleEmploi;

import java.util.Map.Entry;


/**
 * A simple module to encapsulate using HTML tags.
 *
 * Note, makes use of CSS styling in an effort ease changing the style later.
 *
 * @author Robert C. Duvall
 */
public class HTMLPage {
    /**
     * @return tags to be included at top of HTML page
     */
    public static String startPage (int numSizes, int startSize) {
        StringBuilder style = new StringBuilder();        
        for (int k = 0; k < numSizes; k++) {
            style.append("  .size-" + (int) MakeCloudWord.FreqArray[k] + " { font-size: " + ((int) MakeCloudWord.FreqArray[k]*10) + "px; }");
        }
        return "<html><head><meta charset=\"ISO-8859-9\"><style>" + style + "</style></head>"
        		+ "<body><div style=\"width: 50%; height: 50%; margin: auto\">"
        		+ "<p>\n";
    }

    /**
     * @return tags to be included at bottom of HTML page
     */
    public static String endPage () {
        return "</div></p></body></html>";
    }

    /**
     * @return tags to display a single styled word
     */
    public static String formatWord (String word, long cssSize) {
        return "  <span class=\"size-" + cssSize + "\">" + word + "</span>\n";
    }

    /**
     * @return tags to display a single styled word
     */
    public static String formatWord (Entry<String, Long> wordCount) {
        return formatWord(wordCount.getKey(), wordCount.getValue());
    }
}