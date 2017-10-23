import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TFIDF extends Preprocessing {

	public static void main(String[] args) {
		File targetFile = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/testtext.txt");
		File[] corpus = new File[3];
		corpus[0] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article01.txt");
		corpus[1] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article02.txt");
		corpus[2] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article03.txt");
		
		ArrayList<String> targetArrayList = convertFileToArrayList(targetFile);
		//targetArrayList = toUnique(targetArrayList);
		System.out.println(targetArrayList);
		System.out.println(getTF("code", targetFile));
		
		System.out.println(getIDF("aviation", corpus));
		
	}
	
	public static int getTF(String word, File file) {
		int termFreq = 0;
		ArrayList<String> doc = convertFileToArrayList(file);
		for(int i=0; i < doc.size(); i++) {
			if(word.equals(doc.get(i))) {
				termFreq++;
			}
		}
		return termFreq;
	}
	
	public static double getIDF(String word, File[] corpus) {
		int totalDocs = corpus.length;
		int docsContainingWord = 0;
		double idf = 0;
			for(int i = 0; i<totalDocs; i++) {
				ArrayList<String> currentDoc = convertFileToArrayList(corpus[i]);
				for(int k=0; k<currentDoc.size(); k++) {
					if(word.equals(currentDoc.get(i))) {
						docsContainingWord++;
						break; //stop searching
					}
				}
			}
		//end of search
		if(docsContainingWord == 0) {
			System.out.printf("No documents contain word: %s", word);
		} else {
			idf = Math.log(totalDocs / docsContainingWord);
		}
		return idf;
	}

}
