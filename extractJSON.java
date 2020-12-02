package automationPoleEmploi;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import com.fasterxml.jackson.databind.JsonNode;

public class extractJSON {
	public static String[] extractWords(JsonNode jsonNode) throws Exception {
		ArrayList<String> motsCles = new ArrayList<String>();

		String[][] interResults = readCSV.loadFile("blacklist.csv");

		String interResult = "";
		String finalResult;

		// concat the result in a brut String
		String brutResult = "";
		for (int r=0; r<jsonNode.get("resultats").size(); r++) {
			brutResult = brutResult.concat(jsonNode.get("resultats").get(r).get("description").toPrettyString()+" ");
		}
		
		// supprimer les retours à la ligne
		interResult = brutResult.replaceAll(",", " ");
		
		for (int m=0; m<interResults[0].length; m++) {
			if (interResults[1][m].contains("null")) {
				interResult = interResult.replaceAll(interResults[0][m], "");
			}
			if (interResults[1][m].contains("space")) {
				interResult = interResult.replaceAll(interResults[0][m], " ");
			}
			else {
				interResult = interResult.replaceAll(interResults[0][m], interResults[1][m]);
			}
		}
		
		String tmpList[] = interResult.split(" ");

		for (int s=0; s<tmpList.length;s++) {
			finalResult = clarifyDrop(tmpList[s]);		
			if (finalResult.length()>0) {
				motsCles.add(finalResult);
			}
		}

		String[] Words = new String[motsCles.size()]; 
		Words = motsCles.toArray(Words);

		return (String[]) Words;
	}

	// matches le mot avec une blacklist de mots inutiles pour le nuage de mots
	public static String clarifyDrop(String word) throws IOException {
		String Drop=word;

		String matches = "A|de|la|dans|sur|plus|avec|%|au|AU|une|un|le|et|est|des|selon|"
				+ "vous|les|en|Au|sein|en|:|(&agrave;)|tout|HT|d\'une|d\'un|deux"
				+ "env|bien|&amp;gt;|Dernier|(d\'affaires)|env|etc";

		if (word.length()<=2) Drop="";

		// explore the characters for lookinf for lonely upperCase
		// characters if so, it's sticked word, then skip it
		for (int u=1; u<word.length(); u++) {
			char ch = word.charAt(u);
			if (Character.isUpperCase(ch)) Drop="";
		}

		// explore the characters for looking for upperCase word
		if (word.length() >2 && Character.isUpperCase(word.charAt(0)))
			Drop=word.toLowerCase();

		for (int b=0; b<word.length(); b++) {
			if (word.matches(matches)) Drop="";
		}

		return Drop;
	}

	public static void countThemAll(String[] words) throws IOException {
		BufferedWriter writeCSV = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(
								new File("motscles.csv"), true), StandardCharsets.UTF_8));
		Set<String> occurences = new HashSet<String>();
		int[] countByWord;

		String[] wordsBrut = words;
		int incr=0;

		// compter les occurrences de chaque terme
		// dans une collection afin d'éliminer les
		// doublons
		for (int s=0; s<wordsBrut.length; s++) {
			occurences.add(wordsBrut[s]);
		}

		Object[] NoDuplicate = occurences.toArray();
		countByWord = new int[NoDuplicate.length];

		// compter les doublons et les stocker dans un Array
		// voisin avec le même indexage
		for (int t1=0; t1<NoDuplicate.length; t1++) {
			for (int t2=0; t2<wordsBrut.length; t2++) {
				if (NoDuplicate[t1].equals(wordsBrut[t2])) {
					incr=incr+1;
				}
			}
			countByWord[t1]=incr;
			writeCSV.append(NoDuplicate[t1]+","+countByWord[t1]+"\n");
			System.out.println(NoDuplicate[t1]+","+countByWord[t1]+"\n");
			incr=0;
		}
		writeCSV.close();
	}
}