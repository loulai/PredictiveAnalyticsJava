import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TFIDF extends Preprocessing {

	public static void main(String[] args) {
		File targetFile = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article01.txt");
		File[] corpus = new File[8];
		corpus[0] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article01.txt");
		corpus[1] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article02.txt");
		corpus[2] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article03.txt");
		corpus[3] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article04.txt");
		corpus[4] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article05.txt");
		corpus[5] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article06.txt");
		corpus[6] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article07.txt");
		corpus[7] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article08.txt");

	//	double tfidf = getTFIDFVerbose("aviation", targetFile, corpus);
		ArrayList<String> grandFile = mergeDocs(corpus);
		System.out.print(grandFile);
		
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
		int docsContainingWord = getNumDocsContaingWord(word, corpus);
		double idf = 0;
		if(docsContainingWord == 0) {
			System.out.printf("OOV: No documents contain word: %s\n", word);
			idf=-1;
		} else {
			idf = Math.log(corpus.length / docsContainingWord);
		}
		return idf;
	}
	
	public static int getNumDocsContaingWord(String word, File[] corpus) {
		int totalDocs = corpus.length;
		int docsContainingWord = 0;

		for(int i = 0; i < totalDocs; i++) { //search every doc for the target word
			ArrayList<String> currentDoc = convertFileToArrayList(corpus[i]);
			for(int k=0; k < currentDoc.size(); k++) { 
				if(word.equals(currentDoc.get(k))) {
					docsContainingWord++;
					break; //found, stop searching
				}
			}
		}
		return docsContainingWord;
	}
	
	public static double getTFIDF(String word, File targetFile, File[] corpus) {
		int tf = getTF(word,targetFile);
		double idf = getIDF(word, corpus);
		double tfidf = tf * idf;
		return tfidf;
	}
	
	public static double getTFIDFVerbose(String word, File targetFile, File[] corpus) { //same code, except with print statements
		int tf = getTF(word,targetFile);
		double idf = getIDF(word, corpus);
		double tfidf = tf * idf;
		int numDocsContainingWord = getNumDocsContaingWord(word, corpus);
		System.out.printf("Target Word: %s\n", word);
		System.out.printf(">>> TF: %d\n", tf);
		System.out.printf("corpusSize: %d\n", corpus.length);
		System.out.printf("numDocsContainingWord: %d\n", numDocsContainingWord);
		System.out.printf(">>> IDF: %f\n", idf);
		System.out.printf(">>> TFIDF: %f\n", tfidf);
		return tfidf;
	}
	
	private static ArrayList<String> mergeDocs(File[] corpus){
		ArrayList<String> grandArrayList = new ArrayList<String>();

		//append each word in each doc to Grand arrayList
		for(int i = 0; i < corpus.length; i++) {
			ArrayList<String> doc = convertFileToArrayList(corpus[i]); 
			for (int k =0; k < doc.size();k++) {
				grandArrayList.add(doc.get(k));
			}
		}
		return grandArrayList;
	}
}
