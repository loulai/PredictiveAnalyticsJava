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
	
	public Map<String, ArrayList<Double>> columnsMap;
	public ArrayList<String> uniqueTerms;
	public int nRow;
	public int nCol;
	public File[] mainCorpus;
	//ArrayList<String> initialTextList;
	
	public static int getTF(String word, ArrayList<String> doc) throws FileNotFoundException {
		int termFreq = 0;
		for(int i = 0; i < doc.size(); i++) {
			if(word.equals(doc.get(i))) {
				termFreq++;
			}
		}
		return termFreq;
	}
	
	public static double getIDF(String word, File[] corpus) throws FileNotFoundException {
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
	
	public static int getNumDocsContaingWord(String word, File[] corpus) throws FileNotFoundException {
		int docsContainingWord = 0;

		for(int i = 0; i < corpus.length; i++) { //search every doc for the target word
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
	
	public static double getTFIDF(String word, File file, File[] corpus) throws FileNotFoundException {
		ArrayList<String> targetFile = convertFileToArrayList(file);
		int tf = getTF(word, targetFile);
		double idf = getIDF(word, corpus);
		double tfidf = tf * idf;
		return tfidf;
	}	
	
	private static ArrayList<String> mergeDocs(File[] corpus) throws FileNotFoundException{
		//append each word in each doc to grand arrayList
		ArrayList<String> grandArrayList = new ArrayList<String>();
		for(int i = 0; i < corpus.length; i++) {
			ArrayList<String> doc = convertFileToArrayList(corpus[i]); 
			for (int k =0; k < doc.size();k++) {
				grandArrayList.add(doc.get(k));
			}
		}
		return grandArrayList;
	}
	
	public TFIDF(File[] corpus) throws FileNotFoundException {
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
	
	public TFIDF addTFIDF() throws FileNotFoundException {
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
	
	public static void main(String[] args) throws FileNotFoundException {
	
		File[] datasetNames = new File("./../DataSet").listFiles();
		//int numCorpra = datasetNames.length; //comment out during development because there are two extra files
		int numCorpra = 15;
		File[][] dataset = new File[15][];
		
		//read files
		 for (int i = 0; i < datasetNames.length; i++) {
		      if (datasetNames[i].isDirectory()) {
		    	int cIndex = Integer.valueOf(datasetNames[i].getName().replace("C", "")) - 1;
		        //System.out.println("Directory: " + (cIndex + 1));
		        
		        File[] articleNames = datasetNames[i].listFiles();
		        dataset[cIndex] = new File[articleNames.length]; // e.g. dataset[0] = new File[8]
		        
		        for(int k = 0; k < articleNames.length; k++) {
			    	//System.out.printf("\t%s\n", articleNames[k].getName());
			    	String articleName = articleNames[k].getName();
			    	dataset[cIndex][k] = new File("./../DataSet/C" + (cIndex+1) + "/" + articleName);
			      }
			    }
		      }
		
		//TFIDF everything *** WARNING: TAKES A LONG TIME ***
		for (int i = 0; i < 1; i++) {
			System.out.println("----------- Corpus " + (i+1) + " -----------");
			File[] oneCorpus = new File[3]; //replace num with:
			for(int k = 0; k < 3; k++) {    //dataset[i].length (two lines)
				oneCorpus[k] = dataset[i][k];
			}
			TFIDF myTFIDF = new TFIDF(oneCorpus);
			myTFIDF.addTFIDF();
			myTFIDF.printTFIDF();
		}
		
	}
	
	public static double getTFIDFVerbose(String word, File file, File[] corpus) throws FileNotFoundException { //same code but with print statements
		ArrayList<String> targetFile = convertFileToArrayList(file);
		int tf = getTF(word, targetFile);
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
}

