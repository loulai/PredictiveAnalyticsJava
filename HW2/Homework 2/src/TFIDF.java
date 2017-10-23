import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;

public class TFIDF extends Preprocessing {
	
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
	
	public TFIDF(File[] corpus) {
		ArrayList<String> grandArrayList = mergeDocs(corpus);
		ArrayList<String> terms = toUnique(grandArrayList);
		Map<String, ArrayList<Double>> columnsMap = new HashMap(); // each doc is a column
		
		for(int i=0;i < corpus.length; i++) { //create doc1:[0, 0, 0, 0] for each doc
			ArrayList<Double> tfidf = new ArrayList<>(Collections.nCopies(terms.size(), 0.0)); //initialize all zeros
			columnsMap.put(String.valueOf(i), tfidf);
		}
		
		//setting values
		uniqueTerms = terms;
		this.columnsMap = columnsMap;
		nRow = terms.size();
		nCol = corpus.length;
		mainCorpus = corpus;
	}
	
	public Map<String, ArrayList<Double>> columnsMap;
	public ArrayList<String> uniqueTerms;
	public int nRow;
	public int nCol;
	public File[] mainCorpus;
	//ArrayList<String> initialTextList;
	
	public void printTFIDF() {
		//column header
		System.out.printf("%-14s ", ""); //padding for row
		for(String key:columnsMap.keySet()) {
			System.out.printf("%-14s ", key);
		}
		System.out.println();
		
		//rows
		for(int i = 0; i < nRow; i++) {
			System.out.printf("%-14s ", uniqueTerms.get(i)); //current row (e.g. "the")
			for(String key:columnsMap.keySet()) { //iterates over all columns for that row
				System.out.printf("%-14f ", columnsMap.get(key).get(i)); 
			}
			System.out.println();
		}
	}
	
	public TFIDF addTFIDF() {
	
		for(int i = 0; i< nCol; i++) {
			for(int k=0; k< nRow; k++) {
				String currentWord = uniqueTerms.get(k);
				double tfidf = getTFIDF(currentWord, mainCorpus[i], mainCorpus);
				
				//update
				columnsMap.get(String.valueOf(i)).set(k,  tfidf);
			}
		}
		return this;
	}
	
	public static void main(String[] args) {
		File[] corpus = new File[8];
		corpus[0] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article01.txt");
		corpus[1] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article02.txt");
		corpus[2] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article03.txt");
		corpus[3] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article04.txt");
		corpus[4] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article05.txt");
		corpus[5] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article06.txt");
		corpus[6] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article07.txt");
		corpus[7] = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article08.txt");

		TFIDF testTFIDF = new TFIDF(corpus);
		testTFIDF.addTFIDF();
		testTFIDF.printTFIDF();
		
	}
}
