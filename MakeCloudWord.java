package automationPoleEmploi;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

public class MakeCloudWord {
	static int maxFreq=0;
	static int minFreq=1;
    static Object[] FreqArray;

	public static void main(String[] args) throws Exception {
		MakeCloudWord.makeCloudFromJSON(getJSONfromDistantAPI.grabJSON("boulanger"));
	}

	public static void makeCloudFromJSON(JsonNode jsonNode) throws Exception {
		// transform the JSON to a CSV with essential words
		extractJSON.countThemAll(extractJSON.extractWords(jsonNode));

		// load the CSV and proceed to words count
		String[][] motscles = readCSV.loadFile("motscles.csv");
		WordsFreqCount(motscles);
		
		// create the HTML cloud
		FileWriter HTMLwriter = new FileWriter("cloud.html");

		if (motscles[0].length>0) {
			HTMLwriter.append(HTMLPage.startPage(FreqArray.length, minFreq));

			for (int s=0; s<motscles[0].length; s++) {
				HTMLwriter.append((
						HTMLPage.formatWord(motscles[0][s], 
								Integer.valueOf(motscles[1][s]))));
			}
			HTMLwriter.append(HTMLPage.endPage());
			HTMLwriter.close();					
		}
		
		else {
			HTMLwriter.append("<p>no cloud for this job :'(</p>");
		}
	}

	private static Object[] WordsFreqCount(String[][] ArrayWords) {
		Set<Integer> numFreqs = new HashSet<Integer>();
		int currentFreq1;
		int currentFreq2;

		maxFreq=0;
		minFreq=1;

		// determine the max, min, and number of frequencies of words
		for (int i=0; i<ArrayWords[0].length; i++) {
			currentFreq1=Integer.valueOf(ArrayWords[1][i]);
			numFreqs.add(currentFreq1);

			for (int l=0; l<ArrayWords[0].length; l++) {
				currentFreq2=Integer.valueOf(ArrayWords[1][l]);
				if (currentFreq2>currentFreq1 && currentFreq2>maxFreq) {
					maxFreq=currentFreq2;
				}
				if (currentFreq2<currentFreq1 && currentFreq2<minFreq) {
					minFreq=currentFreq2;
				}
			}
		}

		FreqArray = numFreqs.toArray();
		return FreqArray;
	}
}